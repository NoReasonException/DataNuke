package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

abstract public class ChromeEngineDOMParser extends DOMParser {
    public ChromeEngineDOMParser(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
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
