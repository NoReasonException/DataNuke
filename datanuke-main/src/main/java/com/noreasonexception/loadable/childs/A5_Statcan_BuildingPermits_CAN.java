package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.SaveRequestFilterHandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.CanStatParser;

public class A5_Statcan_BuildingPermits_CAN extends CanStatParser {
    public A5_Statcan_BuildingPermits_CAN(ThreadRunnerTaskEventsDispacher disp,
                                          SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }
    @Override
    protected String onEventNameLoad() {
        return "Building permits";
    }

}
