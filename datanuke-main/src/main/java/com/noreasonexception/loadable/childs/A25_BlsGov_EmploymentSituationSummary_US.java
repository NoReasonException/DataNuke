package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.TableParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A25_BlsGov_EmploymentSituationSummary_US extends TableParser {
    public A25_BlsGov_EmploymentSituationSummary_US(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected String onTableSignatureStartLoad() {
        return "<!-- HTML Format -->\n" +
                "\n" +
                "<table class=\"regular\" id=\"ces_table10\">";
    }

    @Override
    protected String onTableSignatureEndLoad() {
        return "</table>\n" +
                "<BR>\n" +
                "<PRE>";
    }

    @Override
    protected Pattern getValuePattern() {
        return Pattern.compile("\"datavalue\">(.*?)</sp");
    }

    @Override
    protected int onRowIndexLoad() {
        return 3;
    }

    @Override
    protected int onCellIndexLoad() {
        return 4;
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.bls.gov/news.release/empsit.htm";
    }
}
