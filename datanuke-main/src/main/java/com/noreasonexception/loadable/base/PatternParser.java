package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

abstract public class PatternParser extends StringParser {
    private java.util.regex.Pattern         pattern;

    public PatternParser(ThreadRunnerTaskEventsDispacher dispacher, AbstractValueFilter<Double> valueFilter) {
        super(dispacher, valueFilter);
    }
    /****
     * @Overridable_By_Children
     * This is called when the Parser is loading the corresponding pattern Object , just return the
     * needed pattern by compile it (Pattern.compile(..))
     * @return an Java.utill.regex.Pattern object
     */
    abstract protected java.util.regex.Pattern onPatternLoad();
    protected Pattern getPattern() {
        return pattern;
    }

    protected void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }




    /****
     * The main loop of PatternParser
     * the .run() method calls it
     * It is basically an infinite loop , stopping only if the ValueFilter detects the new value
     * //TODO in case of changed date in source , this will fail in infinite loop , so a maximum inteval is needed!
     * @return true in success
     */
    protected boolean loop() {
        setPattern(onPatternLoad());
        System.out.println("started"+getClass().getName());
        String temp;
        Double tempValue;
        for (int j = 0; j < REQUESTS_MAX; j++) {
            System.out.println("attempt on -> "+getClass().getName());
            try{
                temp=convertSourceToText();
                tempValue=onValueExtract(temp);
                if(informValueFilter(tempValue)){
                    getDispacher().submitTaskThreadValueRetrievedEvent(getClass().getName(),tempValue);
                    System.out.println("temp -> "+tempValue);
                    return true;
                }
            }catch (NumberFormatException|InvalidSourceArchitectureException e){
                System.out.println(getClass().getName()+": This Event need update , the page format is unknown");
                break;
            }catch (com.noreasonexception.loadable.base.error.ConvertionSourceToTextException e){
                System.out.println(getClass().getName()+": Connection error maybe? "+getClass().getName()+"Failed");
                break;
            }
        }



        return super.loop();
    }
}
