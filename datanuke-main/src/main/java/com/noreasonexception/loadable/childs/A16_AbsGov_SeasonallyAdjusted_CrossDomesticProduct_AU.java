package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.TableParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A16_AbsGov_SeasonallyAdjusted_CrossDomesticProduct_AU extends TableParser {
    public A16_AbsGov_SeasonallyAdjusted_CrossDomesticProduct_AU(ThreadRunnerTaskEventsDispacher disp,
                                                                 AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected String onTableSignatureStartLoad() {
        return "<p><a name=\"1714232832189950991714232832189950\"></a>\n" +
                "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
    }

    @Override
    protected String onTableSignatureEndLoad() {
        return "</table>\n" +
                " \n" +
                "<p><font size=\"4\"> </font>\n" +
                "<p";
    }

    @Override
    protected Pattern getValuePattern() {
        return Pattern.compile("div align=\"right\"><font size=\"2\">(.*) </font></div");
    }

    @Override
    protected int onRowIndexLoad() {
        return 6;
    }

    @Override
    protected int onCellIndexLoad() {
        return 3;
    }

    @Override
    protected String onUrlLoad() {
        return "http://www.abs.gov.au/ausstats/abs%40.nsf/mf/5206.0";
    }
}
