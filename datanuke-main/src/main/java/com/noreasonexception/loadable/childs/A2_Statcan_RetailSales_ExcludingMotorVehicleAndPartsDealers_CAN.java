package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.CanStatParser;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Pattern;

public class A2_Statcan_RetailSales_ExcludingMotorVehicleAndPartsDealers_CAN extends CanStatParser {

    public A2_Statcan_RetailSales_ExcludingMotorVehicleAndPartsDealers_CAN(ThreadRunnerTaskEventsDispacher disp,
                                                                           AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }
    @Override
    protected String onEventNameLoad() {
        return "Retail Trade";
    }

    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        WebDriver d = (WebDriver) context;
        ((WebDriver) context).getPageSource();
        return 0d;
    }


    /***
     *  Override for testing purposes
     *  //TODO REMOVE!
     *  ///TODO It must happen at least 1 time to be able to see the architecture of the page
     */
    @Override
    protected boolean loop() {
        /*
        WebDriver driver    = getWebDriver();
        String    eventName = onEventNameLoad();
        Double    temp      = null;
        for (int i = 0; i < super.REQUESTS_MAX; i++) {
            WebElement element = driver.findElement(By.id("release-list")); //get the q element (query element)
            List<WebElement> firstTableElementlist = element.findElements(By.className("odd"));
            firstTableElementlist.addAll(element.findElements(By.className("even")));

            WebElement firstTableElement=null;
            for (WebElement e:
                    firstTableElementlist) {
                WebElement titleElement = e.findElement(By.tagName("h3"));
                System.out.println(titleElement.getText());
                if(eventName.equals(titleElement.getText())){
                    firstTableElement=e;
                    break;
                }
            }

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
        try {
            Thread.sleep(999999999);

        }catch (InterruptedException e){}
        driver.close();
*/
        return super.loop();
    }
}
