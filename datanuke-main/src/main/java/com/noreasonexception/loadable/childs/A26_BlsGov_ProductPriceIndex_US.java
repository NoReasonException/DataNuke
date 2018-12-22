package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.TableParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A26_BlsGov_ProductPriceIndex_US extends TableParser {
    public A26_BlsGov_ProductPriceIndex_US(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected String onTableSignatureStartLoad() {
        return "</PRE>\n" +
                "<!-- HTML Format -->\n" +
                "\n" +
                "<table class=\"regular\" id=\"ppinr2014_tablea\">";
    }

    @Override
    protected String onTableSignatureEndLoad() {
        return "</table>\n" +
                "\n" +
                "<PRE>";
    }

    @Override
    protected Pattern getValuePattern() {
        return Pattern.compile("datavalue\">(.*?)</spa");
    }

    @Override
    protected int onRowIndexLoad() {
        return getRowCount()-2;
    }

    @Override
    protected int onCellIndexLoad() {
        return 1;
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.bls.gov/news.release/ppi.nr0.htm";
    }
}
