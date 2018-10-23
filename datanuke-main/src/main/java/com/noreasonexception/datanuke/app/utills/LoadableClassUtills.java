package com.noreasonexception.datanuke.app.utills;

public class LoadableClassUtills {

    public static String classNametoClassID(String className){
        String str;
        System.out.println(className);
        return (str=className.substring(className.lastIndexOf("."))).substring(1,str.indexOf("_"));
    }
}
