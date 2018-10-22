package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.CsvParser;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import com.noreasonexception.loadable.base.error.NoUserAgentRequired;
import com.noreasonexception.loadable.base.requestParserEtc.UserAgent;

import java.util.regex.Pattern;

public class A21_OnsGov_EarningsAndWorkingHours_UK extends CsvParser {
    public A21_OnsGov_EarningsAndWorkingHours_UK(ThreadRunnerTaskEventsDispacher disp,
                                                 AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected int onCsvValueIndexLoad(int numberOfValues) {
        return numberOfValues-1;
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.ons.gov.uk/generator?format=csv&uri=/employmentandlabourmarket/peopleinwork/earningsandworkinghours/timeseries/kab9/emp";
    }

    @Override
    protected UserAgent onUserAgentFieldLoad() throws NoUserAgentRequired {
        return UserAgent.DATANUKE;
    }
}
