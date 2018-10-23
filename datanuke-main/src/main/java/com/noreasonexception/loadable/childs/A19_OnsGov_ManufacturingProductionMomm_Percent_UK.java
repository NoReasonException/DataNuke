package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.CsvParser;
import com.noreasonexception.loadable.base.error.NoUserAgentRequired;
import com.noreasonexception.loadable.base.etc.UserAgent;

public class A19_OnsGov_ManufacturingProductionMomm_Percent_UK extends CsvParser {
    public A19_OnsGov_ManufacturingProductionMomm_Percent_UK(ThreadRunnerTaskEventsDispacher disp,
                                                             AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }
    protected String    onUrlLoad(){
        return "https://www.ons.gov.uk/generator?format=csv&uri=/economy/economicoutputandproductivity/output/timeseries/k27y/diop";
    }
    protected int onCsvValueIndexLoad(int numberOfValues){
        return numberOfValues-1;
    }

    @Override
    protected UserAgent onUserAgentFieldLoad() throws NoUserAgentRequired {
        return UserAgent.DATANUKE;
    }
}
