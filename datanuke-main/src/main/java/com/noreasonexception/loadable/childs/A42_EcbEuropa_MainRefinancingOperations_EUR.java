package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;

import java.util.regex.Pattern;

public class A42_EcbEuropa_MainRefinancingOperations_EUR extends HtmlParser {
    public A42_EcbEuropa_MainRefinancingOperations_EUR(ThreadRunnerTaskEventsDispacher disp,
                                                       SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected Pattern onPatternLoad() {
        return Pattern.compile("(0\\.\\d\\d)&nbsp;%</td>",Pattern.MULTILINE|Pattern.DOTALL);
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.ecb.europa.eu/home/html/index.en.html";
    }


}
