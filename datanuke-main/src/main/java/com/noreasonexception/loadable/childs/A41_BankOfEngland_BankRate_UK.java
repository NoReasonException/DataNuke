package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A41_BankOfEngland_BankRate_UK extends HtmlParser {
    public A41_BankOfEngland_BankRate_UK(ThreadRunnerTaskEventsDispacher disp,
                                         SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected Pattern onPatternLoad() {
        return Pattern.compile("(\\d\\.\\d\\d)%",Pattern.MULTILINE);
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.bankofengland.co.uk/";
    }


}
