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
    abstract protected String getEventName();

    @Override
    protected boolean loop() {
        WebDriver driver    = getWebDriver();
        String    eventName = getEventName();
        Double    temp      = null;
        for (int i = 0; i < super.REQUESTS_MAX; i++) {
            WebElement element = driver.findElement(By.id("release-list")); //get the q element (query element)
            WebElement firstTableElement = element.findElements(By.className("odd")).get(0);
            WebElement abchorToFirstNew = firstTableElement.findElement(By.tagName("a")); //get the anchor of first element
            WebElement titleElement = firstTableElement.findElement(By.tagName("h3"));
            System.out.println(abchorToFirstNew.getAttribute("href")); //get the redirect link of anchor
            System.out.println(titleElement.getText());
            if(titleElement.equals(eventName)){
                try {
                    driver.navigate().to(abchorToFirstNew.getAttribute("href"));
                    if(informValueFilter(temp=onValueExtract(driver))){
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
}
