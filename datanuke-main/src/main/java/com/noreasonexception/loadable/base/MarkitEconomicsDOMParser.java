package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import com.noreasonexception.loadable.base.etc.LoopOperationStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class MarkitEconomicsDOMParser extends EventFromDynamicListParser {
    public MarkitEconomicsDOMParser(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.markiteconomics.com/Public/Release/PressReleases?language=en";
    }


    /***
     * Loop on MarkitEconomicsDOMParser
     * The logic
     * In the link returned by onUrlLoad() , we have a js-generated table , full of events
     * we are interested for the first one
     * so , this Abstract Implementation gets the first 5 rows of this table and checks if the name of the
     * event is the name we are looking for (5 because every event translates to 5 languages).
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
    protected LoopOperationStatus loop() {
        WebDriver driver    = getWebDriver();
        String    eventName = onEventNameLoad();
        Double    temp      = null;
        for (int i = 0; i < super.REQUESTS_MAX; i++) {
            List<WebElement> element = driver.findElements(By.className("pressReleaseList")); //get the q element (query element)
            List<WebElement> listItem = element
                    .get(0)
                    .findElements(
                            By.className("listItem"));

            for (int j = 0; j < listItem.size(); j++) {
                WebElement titleElement=listItem.get(j).findElement(
                        By.className(("releaseTitle")));
                String linkurl=listItem.get(j).findElement(By.tagName("a")).getAttribute("href");
                if(titleElement.getText().equals(eventName)){
                    System.out.println("found at "+j);
                    try {
                        if(informValueFilter(temp=onValueExtract(linkurl))){

                            return LoopOperationStatus.buildSuccess(temp);
                        }
                        System.out.println(temp);
                        break;

                    }catch (InvalidSourceArchitectureException e){
                        LoopOperationStatus.buildExceptionThrown(e);
                    }
                }
            }

            driver.navigate().refresh();
        }
        driver.close();


        return LoopOperationStatus.buildSameValueSituation(temp);
    }


    /***
     *
     * @param context anything that be useful ,
     *                for example a string or a table of possble values, is implementation depedent
     *                here , the context parameter is the final url, in order to trigger the secondary parser
     *
     * @return
     * @throws InvalidSourceArchitectureException
     * @Note i override it here only to add the above comment :)
     */

    @Override
    abstract protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException ;
}
