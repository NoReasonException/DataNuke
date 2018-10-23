package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.AbstractParser;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.TableParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class A15_AbsGov_SeasonallyAdjusted_Change_AU extends TableParser {
    public A15_AbsGov_SeasonallyAdjusted_Change_AU(ThreadRunnerTaskEventsDispacher disp,
                                                   AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected String onTableSignatureStartLoad() {
        return
                "<a name=\"101112131415995353\"></a> \n" +
                "<p><a name=\"253028282010995357992530282820109951\"></a>\n" +
                "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
    }

    @Override
    protected String onTableSignatureEndLoad() {
        return "</table>\n" +
                " \n" +
                "<p><font size=\"4\"> </font>\n" +
                "<p>";
    }

    @Override
    protected Pattern getValuePattern() {
        return Pattern.compile("div align=\"right\"><font size=\"2\">(.*)</font></div");
    }

    @Override
    protected int onRowIndexLoad() {
        return 6;
    }

    @Override
    protected int onCellIndexLoad() {
        return 4;
    }
    @Override
    protected String onUrlLoad() {
        return "http://www.abs.gov.au/ausstats/abs@.nsf/mf/8501.0";
    }

}
