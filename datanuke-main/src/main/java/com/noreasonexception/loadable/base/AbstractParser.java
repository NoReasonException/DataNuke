package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.gui.dialog.GeneralExceptionDialog;
import com.noreasonexception.datanuke.app.gui.dialog.SameValueSituationDialog;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import com.noreasonexception.loadable.base.etc.LoopOperationResult;
import com.noreasonexception.loadable.base.etc.LoopOperationStatus;
import javafx.application.Platform;

import java.util.regex.Matcher;

/***
 * This is a abstract parser class
 * used as main interface between parsers
 */
abstract public class AbstractParser implements Runnable {
    private AbstractValueFilter<Double> valueFilter;        //value filter is the object who manages the values into the external medium(file,network,etc)
    private ThreadRunnerTaskEventsDispacher dispacher;      //populate changes and events to subscribed listeners



    AbstractParser(ThreadRunnerTaskEventsDispacher dispacher,AbstractValueFilter<Double> valueFilter){
        this.valueFilter=valueFilter;
        this.dispacher=dispacher;

    }
    /***
     * A useful tool to trigger the .find() method n expected times in order to take the match of interest
     */
    public static class Utills{
        public static void triggerMacherMethodFindNTimes(Matcher m, int n){
            for (int i = 0; i < n; i++) {
                m.find();
            }
        }
    }
    protected AbstractValueFilter<Double> getValueFilter() {
        return this.valueFilter;
    }

    protected ThreadRunnerTaskEventsDispacher getDispacher() {
        return dispacher;
    }

    /***
     * Informs the value filter for new values , is called by .loop()
     * @param value
     * @return true if the submitted value is the new one .
     */
    protected boolean informValueFilter(Double value) {
        try{
            return getValueFilter().submitValue(getClass().getName(),value);

        }catch (CsvValueFilterException e){
            e.printStackTrace();
            return false;
        }
    }

    /***
     * In case of no changed value , this method need to be called!
     *
     * @return
     */
    protected boolean declareSameValueSituation(){
        try{
            return getValueFilter().sameValueSituation(getClass().getName());
        }catch (CsvValueFilterException e){
            e.printStackTrace();
            return false;
        }
    }

    /***
     * Entry point of Abstract Parser
     */
    @Override
    public void run() {
        System.out.println("run \t"+getClass().getName());
        LoopOperationStatus status=loop();
        switch (status.getStatus()){
            case Success:
                getDispacher().submitTaskThreadValueRetrievedEvent(getClass().getName(),status.getValue());
                break;
            case SameValueSituation:
                declareSameValueSituation();
                getDispacher().submitTaskThreadValueRetrievedEventButSame(
                        getClass().getName());
                break;
            case ExceptionThrown:
                getDispacher().submitTaskThreadValueRetrievedEventFailed(
                        getClass().getName(),
                        status.getError());
        }

        getDispacher().submitTaskThreadTerminatedEvent(getClass().getName());
    }
    /****
     * The main loop of RequestParser
     * the .run() method calls it
     * It is basically an  loop , stopping only if the ValueFilter detects the new value
     * @return LoopOperationStatus with further info
     */
    abstract protected LoopOperationStatus loop();

    /***
     * just informs the listeners for the destruction of this object.
     */
    @Override
    protected void finalize() {
        getDispacher().submitTaskThreadReleasedEvent(getClass().getName());
    }

    /***
     * This method should be implemented on all child classes
     * the parameter may be a pattern , or a string , anything that can be translted into a value
     * //TODO consider make it Generic (replacing the Double standard type)
     * @param context anything that be useful , for example a string or a table of possble values, is implementation depedent
     * @return the Double Value that extracted
     * @throws InvalidSourceArchitectureException in case of change and the source is un-parceable
     */
    abstract protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException;
}
