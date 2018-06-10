package com.noreasonexception.datanuke.app.classloader.singleClassClassLoader;


import com.noreasonexception.datanuke.app.classloader.singleClassClassLoader.error.AlreadyLoadedClassException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleClassLoader extends ClassLoader {
    Class<?> singleClass                =null;
    boolean haveAlreadyLoadClass        =false;
    boolean resolveFlag                 =false;
    private static Pattern classVerifier=null;

    static{
        classVerifier=Pattern.compile("^com\\.noreasonexception\\.loadable\\.childs.*");

    }
    public SingleClassLoader(boolean resolveFlag) {
        this.resolveFlag = resolveFlag;
    }

    public Class<?> getSingleClass() {

        return singleClass;
    }
    public static boolean verifyClassNamespace(java.lang.String str){
        return classVerifier.matcher(str).matches();
    }
    /***
     * Only one class Is allowed (with depedencies!)
     * @param s the class string
     * @return the Class object
     * @throws ClassNotFoundException if not found
     * @throws AlreadyLoadedClassException if loadClass is called for more than 1 time
     */
    @Override
    public Class<?> loadClass(String s) throws ClassNotFoundException {
        try{
            super.findSystemClass(s);
        }catch (ClassNotFoundException e){;}
        if(haveAlreadyLoadClass) throw new AlreadyLoadedClassException();
        haveAlreadyLoadClass=true;
        return singleClass=super.loadClass(s,resolveFlag);

    }


    public SingleClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    public SingleClassLoader() {
        super();
    }

    @Override
    public Class<?> loadClass(String s, boolean b) throws ClassNotFoundException {
        return super.loadClass(s, b);
    }

    @Override
    public Object getClassLoadingLock(String s) {
        return super.getClassLoadingLock(s);
    }

    @Override
    public Class<?> findClass(String s) throws ClassNotFoundException {
        return super.findClass(s);
    }


    @Override
    public URL findResource(String s) {
        return super.findResource(s);
    }

    @Override
    public Enumeration<URL> findResources(String s) throws IOException {
        return super.findResources(s);
    }


    @Override
    public Package definePackage(String s, String s1, String s2, String s3, String s4, String s5, String s6, URL url) throws IllegalArgumentException {
        return super.definePackage(s, s1, s2, s3, s4, s5, s6, url);
    }

    @Override
    public Package getPackage(String s) {
        return super.getPackage(s);
    }

    @Override
    public Package[] getPackages() {
        return super.getPackages();
    }

    @Override
    public String findLibrary(String s) {
        return super.findLibrary(s);
    }
}
