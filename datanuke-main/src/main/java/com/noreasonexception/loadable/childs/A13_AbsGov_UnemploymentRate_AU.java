package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.SaveRequestFilterHandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.TableParser;

import java.util.regex.Pattern;

public class A13_AbsGov_UnemploymentRate_AU extends TableParser {
    public A13_AbsGov_UnemploymentRate_AU(ThreadRunnerTaskEventsDispacher disp,
                                          SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected String onUrlLoad() {
        return "http://www.abs.gov.au/ausstats/abs@.nsf/mf/6202.0?opendocument&ref=HPKI";
    }

    @Override
    protected String onTableSignatureStartLoad(){
        return "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\\s";
    }
    @Override
    protected String onTableSignatureEndLoad(){
        return "</table>\n" +
                " \n" +
                "<p><font size=\"4\"> </font>";
    }@Override
    protected Pattern getValuePattern(){
        return Pattern.compile("div align=\"right\"><font size=\"2\">(.*) </font></div",Pattern.DOTALL);
    }@Override
    protected int onRowIndexLoad(){
        return 6;
    }
    @Override
    protected int onCellIndexLoad(){
        return 3;
    }



}
