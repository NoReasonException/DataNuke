package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Matcher;

abstract public class HtmlParser extends PatternParser {
    public HtmlParser(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
        super(disp,valueFilter);
    }
    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        Matcher m = getPattern().matcher(context.toString());
        AbstractParser.Utills.triggerMacherMethodFindNTimes(m,1);
        return Double.valueOf(finalTransformToDouble(m.group(1)));

    }
}
