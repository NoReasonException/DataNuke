package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A44_RbnzGovt_OCR_NZD extends HtmlParser {
    public A44_RbnzGovt_OCR_NZD(ThreadRunnerTaskEventsDispacher disp,
                                SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected Pattern onPatternLoad() {
        return Pattern.compile("(\\d\\.\\d\\d)");
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.rbnz.govt.nz";
    }

}

