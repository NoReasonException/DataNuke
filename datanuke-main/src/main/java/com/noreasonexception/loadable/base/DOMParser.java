package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import org.openqa.selenium.WebDriver;


abstract public class DOMParser extends RequestParser {
    public DOMParser(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    abstract protected WebDriver getWebDriver();

}
