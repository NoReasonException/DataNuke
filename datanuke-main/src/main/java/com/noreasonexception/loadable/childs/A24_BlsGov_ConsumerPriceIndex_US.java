package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.TableParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import javafx.scene.control.Tab;

import java.util.regex.Pattern;

public class A24_BlsGov_ConsumerPriceIndex_US extends TableParser {
    public A24_BlsGov_ConsumerPriceIndex_US(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected String onTableSignatureStartLoad() {
        return "<table class=\"regular\" id=\"cpipress1\">";
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
        return 9;
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.bls.gov/news.release/cpi.t01.htm";
    }
}
