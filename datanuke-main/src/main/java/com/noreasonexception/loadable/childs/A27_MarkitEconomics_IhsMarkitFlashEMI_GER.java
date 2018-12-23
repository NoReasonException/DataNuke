package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.*;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class A27_MarkitEconomics_IhsMarkitFlashEMI_GER extends MarkitEconomicsDOMParser {
    public A27_MarkitEconomics_IhsMarkitFlashEMI_GER(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected String onEventNameLoad() {
        return "IHS Markit Flash Germany Composite PMI";
    }

    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        return new ComplexPdfParserAdapter(
                this,
                new PdfParser(this.getDispacher(),this.getValueFilter()) {
                    protected String linkurl;

                    protected Pattern onPatternLoad(){
                        return Pattern.compile("(\\d\\d\\.\\d) \\(.*\\d");

                    }
                    protected String    onUrlLoad(){
                        return this.linkurl;

                    }
                    protected Double    onValueExtract(Object context) throws InvalidSourceArchitectureException {
                        System.out.println(getPattern());
                        ///TODO : i will use onPatternLoad() here , but getPattern() must used
                        ///TODO: due to .loop() overriding , .setPattern() is not called
                        //TODO : consider fixing this
                        Matcher matcher=onPatternLoad().matcher((String)context);
                        Utills.triggerMacherMethodFindNTimes(matcher,1);
                        return Double.valueOf(matcher.group(1));
                    }

                    @Override
                    protected String onPdfFileNameGet() {
                        return null;
                    }
                    public PdfParser init(String url) {
                        this.linkurl = url;
                        return this;
                    }
                }.init(
                        (String)context),
                        getDispacher(),
                        getValueFilter()).onValueExtract();

    }
}
