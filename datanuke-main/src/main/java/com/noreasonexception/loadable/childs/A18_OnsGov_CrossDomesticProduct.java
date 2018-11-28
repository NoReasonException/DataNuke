package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.CsvParser;
import com.noreasonexception.loadable.base.error.NoUserAgentRequired;
import com.noreasonexception.loadable.base.etc.UserAgent;

public class A18_OnsGov_CrossDomesticProduct extends CsvParser {
    public A18_OnsGov_CrossDomesticProduct(ThreadRunnerTaskEventsDispacher disp,
                                           SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    protected int onCsvValueIndexLoad(int numberOfValues){
        return numberOfValues-1;
    }
    @Override
    protected String onUrlLoad() {
        return "https://www.ons.gov.uk/generator?format=csv&uri=/economy/grossdomesticproductgdp/timeseries/abmi/pgdp";
    }

    @Override
    protected UserAgent onUserAgentFieldLoad() throws NoUserAgentRequired {
        return UserAgent.DATANUKE;
    }
}
