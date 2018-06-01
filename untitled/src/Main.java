import com.sun.istack.internal.localization.NullLocalizable;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args)throws NoSuchMethodException ,IllegalAccessException,InvocationTargetException,InstantiationException{
        StefClassLoader la=new StefClassLoader();
        Class<?> test=la.findClass("be");
        Object o=test.newInstance();
        Method me=test.getMethod("bo");
        me.invoke(o);
        System.out.println(new Main().getClass().getName().replace(".", "/")+".class");

    }

}

class StefClassLoader extends ClassLoader{

    @Override
    public Class<?> findClass(String name) {
        byte[] bt = loadClassData(name);
        return defineClass(name, bt, 0, bt.length);
    }
    private byte[] loadClassData(String className) {
        //read class
        InputStream is;
        try{
            is = new FileInputStream("/home/noreasonexception/Desktop/DataNukeRepo/DataNuke/untitled/out/production/untitled/be.class");

        }catch (FileNotFoundException e){
            System.out.println("ERR");
            return null;
        }
        System.out.println("->"+className.replace(".", "/")+".class");
        ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
        //write into byte
        int len =0;
        try {
            while((len=is.read())!=-1){
                byteSt.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //convert into byte array
        return byteSt.toByteArray();
    }
}