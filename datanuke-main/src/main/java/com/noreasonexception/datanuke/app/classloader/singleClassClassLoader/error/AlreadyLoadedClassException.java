package com.noreasonexception.datanuke.app.classloader.singleClassClassLoader.error;

public  class AlreadyLoadedClassException extends Error {
    public AlreadyLoadedClassException() {

        super("SingleClassLoader allows only one class per instance to be loaded");
    }
}