package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.CsvParser;
import com.noreasonexception.loadable.base.error.NoUserAgentRequired;
import com.noreasonexception.loadable.base.etc.UserAgent;

public class A20_OnsGov_CpiAnnualRate_ChangeOver12Months_UK extends CsvParser {
    public A20_OnsGov_CpiAnnualRate_ChangeOver12Months_UK(ThreadRunnerTaskEventsDispacher disp,
                                                          SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    protected String    onUrlLoad(){
        return "https://www.ons.gov.uk/generator?format=csv&uri=/economy/inflationandpriceindices/timeseries/l55o/mm23";
    }

    @Override
    protected UserAgent onUserAgentFieldLoad() throws NoUserAgentRequired {
        return UserAgent.DATANUKE;
    }

    protected int onCsvValueIndexLoad(int numberOfValues){
        return numberOfValues-1;
    }
}
