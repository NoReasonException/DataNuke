package com.noreasonexception.loadable.base;

public class AbstractParser implements Runnable{

    @Override
    public void run() {
        System.out.println("RUN COMPLETED");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("FINALIZE");
    }
}
