package com.noreasonexception.datanuke.app.classloader.singleClassClassLoader;


import com.noreasonexception.datanuke.app.classloader.singleClassClassLoader.error.AlreadyLoadedClassException;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleClassLoader extends ClassLoader {
    private final static java.lang.String PATH_PREFIX="src/main/java/com/noreasonexception/loadable/childs/";
    private final static java.lang.String CLASS_PREFIX="com.noreasonexception.loadable.childs";
    Class<?> singleClass                =null;
    boolean haveAlreadyLoadClass        =false;

    public Class<?> getSingleClass() {

        return singleClass;
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
        return this.loadClass(s,true);

    }

    /****
     * load class data from /loadable
     * @param s name
     * @return
     */
    protected byte[] loadClassBinaryData(String s) throws ClassNotFoundException{

        try{
            InputStream is = new FileInputStream(PATH_PREFIX+s+".class");
            ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
            //write into byte
            int len =0;
            try {
                while((len=is.read())!=-1){
                    byteSt.write(len);
                }
            } catch (IOException e) {
                throw new ClassNotFoundException("IO Error ",e);
            }
            //convert into byte array
            return byteSt.toByteArray();

        }catch (FileNotFoundException e){throw new ClassNotFoundException("File not found ",e);}
    }


    public SingleClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    public SingleClassLoader() {
        super();
    }

    @Override
    public Class<?> loadClass(String s, boolean b) throws ClassNotFoundException {
        System.out.println("LOAD CLASS CALLED on "+s);
        byte[]binaryData=null;
        String realName=CLASS_PREFIX+"."+s;
        try{
            super.findSystemClass(realName);
        }catch (ClassNotFoundException e){;}

        if(haveAlreadyLoadClass) throw new AlreadyLoadedClassException();
        haveAlreadyLoadClass=true;
        singleClass=defineClass(realName,binaryData=loadClassBinaryData(s),0,binaryData.length);
        if(b)resolveClass(singleClass);
        return singleClass;
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
