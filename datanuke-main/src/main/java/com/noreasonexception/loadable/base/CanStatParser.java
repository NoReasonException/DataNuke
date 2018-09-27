package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


abstract public class CanStatParser extends ChromeEngineDOMParser {
    public CanStatParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected String onUrlLoad() {
        return "https://www150.statcan.gc.ca/n1/dai-quo/ind1-eng.htm";
    }

    abstract protected String onEventNameLoad();

    /***
     * Loop on CanStatParser
     * The logic
     * In the link returned by onUrlLoad() , we have a js-generated table , full of events
     * we are interested for the first one
     * so , this Abstract Implementation gets the first row of this table .
     * In case the value returned by onEventNameLoad is equal to the first event , this is the
     * required new-event , and the control is transfered to any child-Implementation , who will
     * take the WebDriver Object via onValueExtract(Object content) call.
     * The only thing the implementation has to do , is to retrieve the new value
     *
     * @Note This is the only case of non-repetitive call of .onValueExtract(Object content)
     * @Note ...It is expected to run only once . in case of return the same value ,
     * the default AbstractParser implementation will declare declareSameValueSituation.
     * @return true on success
     */
    @Override
    protected boolean loop() {
        WebDriver driver    = getWebDriver();
        String    eventName = onEventNameLoad();
        Double    temp      = null;
        for (int i = 0; i < super.REQUESTS_MAX; i++) {
            WebElement element = driver.findElement(By.id("release-list")); //get the q element (query element)
            WebElement firstTableElement = element.findElements(By.className("odd")).get(0);
            WebElement abchorToFirstNew = firstTableElement.findElement(By.tagName("a")); //get the anchor of first element
            WebElement titleElement = firstTableElement.findElement(By.tagName("h3"));
            System.out.println(abchorToFirstNew.getAttribute("href")); //get the redirect link of anchor
            System.out.println(titleElement.getText());
            if(titleElement.getText().equals(eventName)){
                try {
                    driver.navigate().to(abchorToFirstNew.getAttribute("href"));
                    if(informValueFilter(temp=onValueExtract(driver))){
                        System.out.println(temp);
                        return true;
                    }
                    break;
                }catch (InvalidSourceArchitectureException e){
                    e.printStackTrace();
                    break;
                }
            }
            driver.navigate().refresh();
        }
        driver.close();
        return super.loop();
    }



    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        WebDriver driver = (WebDriver)context;
        WebElement growthIndicator = driver.findElements(By.className("sd-indicator-growth")).get(0);
        System.out.println(growthIndicator.getText().replace("%",""));
        return Double.valueOf(growthIndicator.getText().replace("%",""));
    }
}
