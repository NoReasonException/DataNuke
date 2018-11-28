package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A28_MarkitEconomics_IHSMarkitCipsUkManufacturingPMI_UK extends HtmlParser {
    public A28_MarkitEconomics_IHSMarkitCipsUkManufacturingPMI_UK(ThreadRunnerTaskEventsDispacher disp,
                                                                  SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected Pattern onPatternLoad() {
        return null;
    }

    @Override
    protected String onUrlLoad() {
        return null;
    }

    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        return null;
    }
}
