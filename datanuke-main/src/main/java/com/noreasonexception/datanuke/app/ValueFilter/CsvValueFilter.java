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


    public CsvValueFilter(String filename){
        this.classIDs=new Hashtable<>();
        this.filename=filename;
    }
    public CsvValueFilter buildFromFile() throws CsvValueFilterException{
        this.classValues=this.fileContextToArray();
        return this;
    }

    /****
     * .fileContextToString()
     * just reads the initial file data returs it as plain string
     * @return the contexts of file as String
     * @throws CsvValueFilterException in case any type of IOException
     */
    /*Package-Private*/String fileContextToString() throws CsvValueFilterException{
        try{
            Path p;
            return DataProvider.Utills.DataProviderToString(this.fileDataProvider=new FileDataProvider(
                    p=Paths.get(this.filename),Files.readAttributes(p,BasicFileAttributes.class).size()));
        }catch (IOException e){
            throw new CsvValueFilterException("IO error during reading the file...",e);
        }

    }

    /****
     * parser of .csv files , returns a ArrayList with the values per-class
     * @return the ArrayList with the values
     * @throws CsvValueFilterMailformedFileException in case of error in sundax of file
     * @throws CsvValueFilterException in case of any IOException
     */
    /*Package-Private*/ ArrayList<Double> fileContextToArray()throws    CsvValueFilterException,
                                                                        CsvValueFilterMailformedFileException {
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
        }

    }

    /****
     * gets the id of a class by using its name
     *
     * @param className the class name as string
     * @return the class id to be used in this.classValues
     * @throws CsvValueFilterInconsistentStateException in case of calling this method without call .buildFromFile() first
     */
    /*Package-Private*/ int getIdByClassObj(String className) throws CsvValueFilterException{
        if (this.classValues==null)throw new CsvValueFilterInconsistentStateException();
        Integer id;
        if((id=classIDs.get(className))==null)return -1;
        return id;

    }

    protected Object getLockObject(){return this;}

    /***
     * just a funky getter
     */
    protected ArrayList<Double> getCSVContext(){
        return this.classValues;
    }

    /****
     * saves every value of this.classValues into actual file
     * @return true on success
     */
    synchronized protected boolean  saveCSVContext() {
        ByteBuffer buffer = ByteBuffer.allocate((122 * getCSVContext().size()));

        try {
            FileChannel byteChannel = (FileChannel) FileChannel.open(Paths.get(filename),StandardOpenOption.WRITE);

            byteChannel.write(
                    ByteBuffer.wrap(
                            Arrays.toString(
                                    this.classValues.toArray()).
                                    replace("[","").
                                    replace("]","").getBytes())
            );

            byteChannel.close();


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    /****
     * .submitValue()
     * @param className the class submitted the value
     * @param value the actual value
     * @return true in success.
     */
    @Override
    public boolean submitValue(String className, Double value) throws CsvValueFilterException {
        synchronized (getLockObject()){
            int id;
            if(this.classValues.size()!=this.classIDs.size())
                throw new CsvValueFilterInconsistentStateException();
            if((id= getIdByClassObj(className))==-1){
                throw new CsvValueFilterClassNotRegisteredException(className);
            }
            if(getCSVContext().get(id= getIdByClassObj(className)).compareTo(value)!=0){
                getCSVContext().set(id,value);
                saveCSVContext();
                return true;
            }
            return false;
        }

    }
    public boolean submitClass(String klassName) throws CsvValueFilterInconsistentStateException{
        if(this.classValues==null)throw new CsvValueFilterInconsistentStateException();
        this.classIDs.put(klassName,cnt);
        cnt+=1;
        return true;
    }
}
