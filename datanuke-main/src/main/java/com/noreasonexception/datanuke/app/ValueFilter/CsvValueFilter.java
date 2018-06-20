package com.noreasonexception.datanuke.app.ValueFilter;


import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterClassNotRegisteredException;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterInconsistentStateException;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterMailformedFileException;
import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.dataProvider.FileDataProvider;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class CsvValueFilter implements ValueFilterable<Double> {
    private Hashtable<String,Integer> classIDs;
    private ArrayList<Double>           classValues;
    private java.lang.String            filename;
    private DataProvider                fileDataProvider;
    private static int                  cnt=0;


    public CsvValueFilter(int ensureCapacity,String filename){
        this.classIDs=new Hashtable<>();
        this.filename=filename;
    }
    public CsvValueFilter buildFromFile() throws CsvValueFilterException{
        this.classValues=this.fileContextToArray();
        return this;
    }
    /****
     * Return the contexts of value file as a string using the utills provided by @see DataProvider
     * @return the text of the file
     * @throws IOException
     */

    /*Package-Private*/String fileContextToString() throws IOException{
        Path p;
        return DataProvider.Utills.DataProviderToString(this.fileDataProvider=new FileDataProvider(
                p=Paths.get(this.filename),Files.readAttributes(p,BasicFileAttributes.class).size()));
    }

    /****
     * Parser of csv file , returns the data as ArrayList of doubles
     * @return the actual data as ArrayList
     * @throws IOException in case of any error
     */
    /*Package-Private*/ ArrayList<Double> fileContextToArray()throws CsvValueFilterException {
        try{
            String str = fileContextToString();
            ArrayList<Double> retval=new ArrayList<>();
            for (String s:str.split(",")){ //NumberFormatException
                retval.add(Double.valueOf(s));
            }
            return retval;
        }catch (NumberFormatException e){
            throw new CsvValueFilterMailformedFileException(
                    "The parser detected something that we cannot say for sure that is an number",e);
        }catch (IOException e){
            throw new CsvValueFilterException("File IO Error (missing file/permissions maybe?)",e);
        }

    }
    /****
     * Get the order in csv file of the submitted class provided by parameter @param classObj
     * @param className the class object
     * @return the id , this id will be used as index in csv array of values
     */
    /*Package-Private*/ int getIdByClassObj(Class className) throws CsvValueFilterException{
        if (this.classValues==null)throw new CsvValueFilterInconsistentStateException("please call .build() first!");
        Integer id;
        if((id=classIDs.get(className.getName()))==null)return -1;
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
     * saves everything in file, converts the actual values into .csv format
     * @return
     */
    synchronized protected boolean  saveCSVContext() {
        ByteBuffer buffer = ByteBuffer.allocate((122 * getCSVContext().size()));

        try {
            FileChannel byteChannel = (FileChannel) FileChannel.open(Paths.get(filename),StandardOpenOption.WRITE);

            byteChannel.write(
                    ByteBuffer.wrap(
                            Arrays.toString(
                                    this.classValues.toArray()).
                                    replace("[","").replace("]","").getBytes())
            );

            byteChannel.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }


    /****
     * .submitValue()
     * @param classObj the class submitted the value
     * @param value the actual value
     * @return
     */
    @Override
    public boolean submitValue(Class<?> classObj, Double value) throws CsvValueFilterException {
        synchronized (getLockObject()){
            int id;
            if(this.classValues.size()!=this.classIDs.size())
                throw new CsvValueFilterInconsistentStateException("");
            if((id= getIdByClassObj(classObj))==-1){
                throw new CsvValueFilterClassNotRegisteredException("");
            }
            if(getCSVContext().get(id= getIdByClassObj(classObj)).compareTo(value)!=0){
                getCSVContext().set(id,value);
                saveCSVContext();
                return true;
            }
            return false;
        }

    }

    public boolean submitClass(Class<?> klass){
        this.classIDs.put(klass.getName(),cnt);
        cnt+=1;
        return true;
    }
}
