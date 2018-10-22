package com.noreasonexception.loadable.base.requestParserEtc;

public enum UserAgent {
    MOZILLA47_WIN64("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"),
    MOZILLA42_MAC("Mozilla/5.0 (Macintosh; Intel Mac OS X x.y; rv:42.0) Gecko/20100101 Firefox/42.0"),
    DATANUKE("DataNuke/2.0 (+https://github.com/NoReasonException/DataNuke)");


    private String userAgentField;
    UserAgent(String agent) {
        this.userAgentField=agent;
    }

    public String getUserAgentField() {
        return userAgentField;
    }
}
