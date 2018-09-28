package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.CanStatParser;
import com.noreasonexception.loadable.base.CsvParser;
import com.noreasonexception.loadable.base.TableParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class A7_Statcan_ConsumerPriceIndex_Table1_Line1_5Collumn_CAN extends CanStatParser {
    public A7_Statcan_ConsumerPriceIndex_Table1_Line1_5Collumn_CAN(ThreadRunnerTaskEventsDispacher disp,
                                                                   AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }
    @Override
    protected String onEventNameLoad() {
        return "Consumer Price Index";
    }

    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        CsvParser secondaryParser=null;
        String tempUrlToCsv=null;
        WebDriver mainDriver=(WebDriver)context;
        WebElement table1 = mainDriver.findElements(By.cssSelector(".tablebtn.btn-group.btn-group-justified")).get(0);
        mainDriver.navigate().to(tempUrlToCsv=table1.findElement(By.tagName("a")).getAttribute("href"));

        secondaryParser= OnA7SecondaryCsvParserLoad(
                mainDriver.findElement(
                        By.cssSelector(".release_nav"))
                .findElements(By.cssSelector(".btn.btn-default.btn-sm"))
                .get(1).getAttribute("href"));
        secondaryParser.run();


        return 0d;
    }

    private CsvParser OnA7SecondaryCsvParserLoad(final String csvUrl){
        return new CsvParser(getDispacher(),getValueFilter()) {
            @Override
            protected int onCsvValueIndexLoad(int numberOfValues) {
                return 20;
            }

            @Override
            protected String onUrlLoad() {
                return csvUrl;
            }
        };
    }
    /***
     *  Override for testing purposes
     *  //TODO REMOVE!
     *  ///TODO It must happen at least 1 time to be able to see the architecture of the page
     */
    @Override
    protected boolean loop() {

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
        return super.loop();
    }
}
