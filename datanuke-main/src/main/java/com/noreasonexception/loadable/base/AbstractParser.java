package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;

abstract public class AbstractParser implements Runnable {
    private AbstractValueFilter<Double> valueFilter;
    private ThreadRunnerTaskEventsDispacher dispacher;
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
     * The main loop of PattermParser
     * the .run() method calls it
     * It is basically an infinite loop , stopping only if the ValueFilter detects the new value
     * //TODO in case of changed date in source , this will fail in infinite loop , so a maximum inteval is needed!
     * @return true in success
     */
    abstract protected boolean loop();


    @Override
    protected void finalize() {
        getDispacher().submitTaskThreadReleasedEvent(getClass().getName());
    }
    /****
     * @Overridable_By_Children
     * This routine is combines the results of convertSourceToText( given in tmpString parameter)
     * with the given pattern (using getPattern()) to extract the value needed
     * @param tmpString the source text provided by convertSourceToText
     * @return a Double Object //TODO maybe this methods need to return a plain Object object?
     */
    abstract protected Double onValueExtract(String tmpString);
}
