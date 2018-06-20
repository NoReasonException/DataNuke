package com.noreasonexception.datanuke.app.ValueFilter;


import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.dataProvider.FileDataProvider;

import javax.naming.OperationNotSupportedException;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Optional;

public class CsvValueFilter implements ValueFilterable<Double> {
    private Hashtable<Class<?>,Integer> classIDs;
    private ArrayList<Double>           classValues;
    private java.lang.String            filename;
    private DataProvider                fileDataProvider;

    private  String contextToString(ArrayList<Double> d) throws IOException{
        Path p;
        return DataProvider.Utills.DataProviderToString(this.fileDataProvider=new FileDataProvider(
                p=Paths.get(this.filename),Files.readAttributes(p,BasicFileAttributes.class).size()));
    }
    private static ArrayList<Double>    stringToContext(java.lang.String str){
        return null;
    }
    public CsvValueFilter(int ensureCapacity){
        this.classIDs=new Hashtable<>();
        this.classValues=new ArrayList<>(ensureCapacity);

    }
    /****
     * Get the order in csv file of the submitted class provided by parameter @param classObj
     * @param classObj the class object
     * @return the id , this id will be used as index in csv array of values
     */
    private int getIdByClassObject(Class<?>classObj){
        Integer id;
        if((id=classIDs.get(classObj))==null)return -1;
        return id;

    }

    protected Object getLockObject(){return this;}
    /****
     * Gets the context of csv file and translate it into Double[]
     * @implSpec if you change something , then saveCSVContext must be called
     * @return
     */
    protected ArrayList<Double> getCSVContext(){
        return this.classValues;
    }

    /****
     * set the context of @param values into file
     * @implNote WARNING! setCSVContext is not saves actually the data , only affects the corresponding member
     * you must manually call .applyCSVContext() or call .saveCSVContext()
     * (a simple wrapper over setCSVContext()+applyCSVContext)
     *
     * @return this(fluent Interface)
     */
    protected CsvValueFilter  setCSVContext(ArrayList<Double> values){
        this.classValues=values;
        return this;
    }

    /****
     * Apply the Values set by setCSVContext into actual file
     * @return true on success, false otherwise
     */
    protected boolean  applyCSVContext(){return false;}

    /****
     * a simple wrapper over setCSVContext()+applyCSVContext()
     * @param values
     * @return
     */
    protected boolean  saveCSVContext(ArrayList<Double> values){
        return setCSVContext(values).applyCSVContext();
    }


    /****
     * .submitValue()
     * @param classObj the class submitted the value
     * @param value the actual value
     * @return
     */
    @Override
    public boolean submitValue(Class<?> classObj, Double value) {
        ArrayList<Double> tmp=getCSVContext();
        int id;
        if(tmp.get(id=getIdByClassObject(classObj)).compareTo(value)!=0){
            tmp.set(id,value);
            saveCSVContext(tmp);
            return true;
        }
        return false;
    }
}
