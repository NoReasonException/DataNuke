package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.ConvertionSourceToTextException;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

 public class ComplexCsvParserAdapter extends CsvParser {
    private CsvParser simpleParser;
    private AbstractParser parent;

    public ComplexCsvParserAdapter(AbstractParser parent,CsvParser simpleParser, ThreadRunnerTaskEventsDispacher dispacher, AbstractValueFilter<Double> valueFilter) {
        super(dispacher, valueFilter);
        this.simpleParser=simpleParser;
        this.parent=parent;
    }
    public Double onValueExtract() throws InvalidSourceArchitectureException {
        try {

            return simpleParser.onValueExtract(simpleParser.convertSourceToArrayList());
        }catch (ConvertionSourceToTextException e){
            throw new InvalidSourceArchitectureException(parent.getClass());
        }
    }

     @Override
     protected int onCsvValueIndexLoad(int numberOfValues) {
         return getSimpleParserObject().onCsvValueIndexLoad(numberOfValues);
     }

     @Override
     protected String onUrlLoad() {
         return getSimpleParserObject().onUrlLoad();
     }

     public CsvParser getSimpleParserObject(){
        return this.simpleParser;
    }
}
