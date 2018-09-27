package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

abstract public class ChromeEngineDOMParser extends DOMParser {
    public ChromeEngineDOMParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected WebDriver getWebDriver() {
        System.setProperty("webdriver.chrome.driver","chromedriver.exe"); //set where the driver will located
        WebDriver driver = new ChromeDriver();      //get the ChromeDriver objecy
        driver.get(onUrlLoad());        //set the link
        return driver;
    }
}
