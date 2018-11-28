package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.ConvertionSourceToTextException;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import com.noreasonexception.loadable.base.etc.LoopOperationStatus;

import java.util.ArrayList;
import java.util.Collections;

abstract public class CsvParser extends StringParser{
    public CsvParser(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }
    @Override
    @SuppressWarnings("unchecked")

    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        ArrayList<String> ref;
        return Double.valueOf((ref=(ArrayList<String >)context).get(onCsvValueIndexLoad(ref.size())).replace("\"",""));
    }

    abstract protected int onCsvValueIndexLoad(int numberOfValues);

    @Override
    protected LoopOperationStatus loop() {
        ArrayList<String> csvParts=null;
        Double tempValue=0d;
        try {
            for (int i = 0; i < super.REQUESTS_MAX; i++) {
                if(informValueFilter(tempValue=onValueExtract(csvParts=convertSourceToArrayList()))){
                    return LoopOperationStatus.buildSuccess(tempValue);
                }
                System.out.println(tempValue);

            }
        } catch (Exception e) {
            return LoopOperationStatus.buildExceptionThrown(e);
        }
        return LoopOperationStatus.buildSameValueSituation(tempValue);
    }
    protected ArrayList<String> convertSourceToArrayList() throws ConvertionSourceToTextException{
        ArrayList<String> retval=new ArrayList<>();
        if(!Collections.addAll(retval,convertSourceToText().split(","))){
            throw new ConvertionSourceToTextException(getClass(),new RuntimeException("Unknown error caused exception"));
        }
        return retval;
    }
}
