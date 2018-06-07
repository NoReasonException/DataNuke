package com.noreasonexception.datanuke.app.classloader.singleClassClassLoader;


public class SingleClassLoader extends ClassLoader {
    Class<?> singleClass                =null;
    boolean haveAlreadyLoadClass        =false;
    boolean resolveFlag                 =false;

    public SingleClassLoader(boolean resolveFlag) {
        this.resolveFlag = resolveFlag;
    }

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
        try{
            super.findSystemClass(s);
        }catch (ClassNotFoundException e){;}
        if(haveAlreadyLoadClass) throw new AlreadyLoadedClassException();
        haveAlreadyLoadClass=true;
        return singleClass=super.loadClass(s,resolveFlag);

    }
}
