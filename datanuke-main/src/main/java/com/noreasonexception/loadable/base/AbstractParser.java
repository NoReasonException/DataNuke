package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Matcher;

abstract public class AbstractParser implements Runnable {
    private AbstractValueFilter<Double> valueFilter;
    private ThreadRunnerTaskEventsDispacher dispacher;
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

    AbstractParser(ThreadRunnerTaskEventsDispacher dispacher,AbstractValueFilter<Double> valueFilter){
        this.valueFilter=valueFilter;
        this.dispacher=dispacher;

    }

    public void setValueFilter(AbstractValueFilter<Double> valueFilter) {
        this.valueFilter = valueFilter;
    }

    public ThreadRunnerTaskEventsDispacher getDispacher() {
        return dispacher;
    }

    public void setDispacher(ThreadRunnerTaskEventsDispacher dispacher) {
        this.dispacher = dispacher;
    }

    /***
     * Informs the value filter for new values , is called by .loop()
     * @param value
     * @return
     */
    protected boolean informValueFilter(Double value) {
        try{
            return getValueFilter().submitValue(getClass().getName(),value);

        }catch (CsvValueFilterException e){
            e.printStackTrace();
            return false;
        }
    }
    protected boolean declareSameValueSituation(){
        try{
            return getValueFilter().enforcesubmitValue();
        }catch (CsvValueFilterException e){
            e.printStackTrace();
            return false;
        }

    }
    @Override
    public void run() {
        System.out.println("run \t"+getClass().getName());
        if(!loop()){
            getDispacher().submitTaskThreadValueRetrievedEventFailed(
                    getClass().getName(),
                    new CsvValueFilterException("Value Not found after 10000 request , a broken pattern maybe?"));
        }
        getDispacher().submitTaskThreadTerminatedEvent(getClass().getName());
    }
    /****
     * The main loop of RequestParser
     * the .run() method calls it
     * It is basically an infinite loop , stopping only if the ValueFilter detects the new value
     * @return true in success
     */
    protected boolean loop(){
        return declareSameValueSituation();
    }


    @Override
    protected void finalize() {
        getDispacher().submitTaskThreadReleasedEvent(getClass().getName());
    }

    abstract protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException;
}
