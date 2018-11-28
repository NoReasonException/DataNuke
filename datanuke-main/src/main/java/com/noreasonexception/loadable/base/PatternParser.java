package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.SaveRequestFilterHandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import com.noreasonexception.loadable.base.etc.LoopOperationStatus;

import java.util.regex.Pattern;

/***
 * Uses the Standard .regex package to extract values
 */
abstract public class PatternParser extends StringParser {
    private java.util.regex.Pattern         pattern;

    public PatternParser(ThreadRunnerTaskEventsDispacher dispacher, SaveRequestFilterHandler<Double> valueFilter) {
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
     * It is basically an infinite loop , stopping only if the SaveRequestFilterHandler detects the new value
     * //TODO in case of changed date in source , this will fail in infinite loop , so a maximum inteval is needed!
     * @return true in success
     */
    protected LoopOperationStatus loop() {
        setPattern(onPatternLoad());
        System.out.println("started"+getClass().getName());
        String temp;
        Double tempValue=0d;
        for (int j = 0; j < REQUESTS_MAX; j++) {
            System.out.println("attempt on -> "+getClass().getName());
            try{
                temp=convertSourceToText();
                tempValue=onValueExtract(temp);
                if(informValueFilter(tempValue)){
                    return LoopOperationStatus.buildSuccess(tempValue);
                }
            }catch (NumberFormatException|
                    InvalidSourceArchitectureException|
                    com.noreasonexception.loadable.base.error.ConvertionSourceToTextException e){
                return LoopOperationStatus.buildExceptionThrown(e);
            }
        }
        return LoopOperationStatus.buildSameValueSituation(tempValue);
    }
}
