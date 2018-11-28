package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.SaveRequestFilterHandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A36_InstituteForSupplyManagment_MostRecentNMIReport_US extends HtmlParser {
    public A36_InstituteForSupplyManagment_MostRecentNMIReport_US(ThreadRunnerTaskEventsDispacher disp,
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
