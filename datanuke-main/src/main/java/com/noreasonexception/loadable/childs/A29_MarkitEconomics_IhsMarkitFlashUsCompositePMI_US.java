package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.MarkitEconomicsDOMParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A29_MarkitEconomics_IhsMarkitFlashUsCompositePMI_US extends MarkitEconomicsDOMParser {
    public A29_MarkitEconomics_IhsMarkitFlashUsCompositePMI_US(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        return null;
    }

    @Override
    protected String onEventNameLoad() {
        return "IHS Markit Flash US Composite PMI";
    }
}
