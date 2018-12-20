package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.TableParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A23_BlsGov_TotalNonfarmPayrollEmployment_US extends TableParser {
    @Override
    protected String onTableSignatureStartLoad() {
        return "<table class=\"regular\" id=\"cps_empsit_sum\">";
    }

    @Override
    protected String onTableSignatureEndLoad() {
        return "</table>";
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
        return 1;
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.bls.gov/news.release/empsit.a.htm";
    }

    @Override
    protected String finalTransformToDouble(String returnedValue) {
        return returnedValue.replace(",",".");
    }

    public A23_BlsGov_TotalNonfarmPayrollEmployment_US(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }
}
