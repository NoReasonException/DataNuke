package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.classloader.AtlasLoader;

public class AbstractParser implements Runnable{

    @Override
    public void run() {
        System.out.println("RUN COMPLETED");

    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalize");
    }
}
