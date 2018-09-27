package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

abstract public class DOMParser extends RequestParser {
    public DOMParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    abstract protected WebDriver getWebDriver();

    @Override
    protected String onUrlLoad() {
        return null;
    }

    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        return null;
    }

    @Override
    protected boolean loop() {
        WebDriver driver=getWebDriver();
        Double temp;
        for (int i = 0; i <super.REQUESTS_MAX ; i++) {
            try {
                if(informValueFilter(temp=onValueExtract(driver))) return true;
            }catch (InvalidSourceArchitectureException e){
                e.printStackTrace();
                break;
            }
        }
        return super.loop();
    }
}
