package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.CanStatParser;

public class A3_Statcan_CanadianInternationalMerchandiseTrade_CAN extends CanStatParser {

    public A3_Statcan_CanadianInternationalMerchandiseTrade_CAN(ThreadRunnerTaskEventsDispacher disp,
                                                                SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }
    @Override
    protected String onEventNameLoad() {
        return "Canadian international merchandise trade";
    }
}
