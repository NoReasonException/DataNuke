package com.noreasonexception.datanuke.app.ValueFilter;


import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterClassNotRegisteredException;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterInconsistentStateException;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterMailformedFileException;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.ValueFilter.interfaces.MostRecentUnixTimestampFileFilter;
import com.noreasonexception.datanuke.app.dataProvider.FileDataProvider;
import com.noreasonexception.datanuke.app.dataProvider.DataProvider;

import java.io.File;
import java.nio.file.StandardOpenOption;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.*;
import java.nio.file.Path;

/***
 * This is the main implementation of Save Subsystem
 * Saves the values into a .csv file , just numbers seperated by commas!
 *
 * How it works?
 *
 * At first The CsvValueFilter reads all the contexts of the file given in constructor
 * Lets suppose that the file contains n values
 *
 * Now , the object has n values but 0 classes . so it is in Invalid State .
 * When the object is in invalid state ,every operation ,except .submitClass(),
 * will throw an CsvValueFilterInconsistentStateException .
 *
 * The object , to be consistent , expect n .submitClass() calls
 * The first call will correlate the first class into the first value came from file and vice versa
 *
 * =====================================================================================================================
 * Every call into .submitValue() will return true if the RequestParser class finds an new value
 *
 */
public class CsvValueFilter extends AbstractValueFilter<Double> {
    private Hashtable<String,Integer> classIDs;
    private ArrayList<Double>           classValues;
    private java.lang.String            directory;
    private DataProvider                fileDataProvider;
    private static int                  cnt=0;


    public CsvValueFilter(String directory){
        this.classIDs=new Hashtable<>();
        this.directory =directory;
    }

    /****
     * Call always before any operation , otherwise , a CsvValueFilterInconsistentStateException will be thrown.
     * @return this object
     * @throws CsvValueFilterException in case of any error(IOE or corrupted file)
     */
    @Override
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
    /*Package-Private*/String fileContextToString() throws CsvValueFilterException {
        Path p;
        return DataProvider.Utills.DataProviderToString(this.fileDataProvider = new FileDataProvider(
                p = Paths.get(getMostRecentFile(this.directory))));

    }
    @SuppressWarnings("unchecked")
    private static String getMostRecentFile(String directory){
        List e;
        (e=Arrays.asList(
                new File(directory).
                        list(new MostRecentUnixTimestampFileFilter()))).
                sort((a1,a2)->{
                    String ref1,ref2;
                    return Integer.valueOf((ref1=(String)a1).substring(0,ref1.length()-4))<
                        Integer.valueOf((ref2=(String)a2).substring(0,ref2.length()-4))?1:-1;
        });
        return directory+e.get(0);
    }
    private static String getNewFilename(String directory){
        return directory+(System.currentTimeMillis()/1000)+".csv";
    }

    /****
     * parser of .csv files , returns a ArrayList with the values per-class
     * @return the ArrayList with the values
     * @throws CsvValueFilterMailformedFileException in case of error in sundax of file
     * @throws CsvValueFilterException in case of any IOException
     */
    /*Package-Private*/ ArrayList<Double> fileContextToArray()throws    CsvValueFilterException,
                                                                        CsvValueFilterMailformedFileException {
        String tmp="none";
        try{
            String str = fileContextToString();
            ArrayList<Double> retval=new ArrayList<>();
            for (String s:str.split(",")){ //NumberFormatException
                retval.add(Double.valueOf(tmp=s));
            }
            return retval;
        }catch (NumberFormatException|NoSuchElementException e){
            throw new CsvValueFilterMailformedFileException(
                    "The parser detected something that we cannot say for sure that is an number "+e.getMessage()+"("+tmp+")",e);
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

    @Override
    public boolean enforcesubmitValue() throws CsvValueFilterException {
        return saveCSVContext();
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
        String filePathStr;
        try {
            Path filePath=Paths.get(filePathStr=getNewFilename(directory));
            System.out.println(filePathStr+" created !");
            FileChannel byteChannel = (FileChannel) FileChannel.open(filePath,StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE);

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
            try {
                int id;
                if(this.classValues.size()!=this.classIDs.size())
                    throw new CsvValueFilterInconsistentStateException(classValues.size()+"-"+classIDs.size());
                if((id= getIdByClassObj(className))==-1){
                    throw new CsvValueFilterClassNotRegisteredException(className);
                }
                if(getCSVContext().get(id).compareTo(value)!=0){
                    getCSVContext().set(id,value);
                    saveCSVContext();
                    return true;
                }
                return false;
            }catch (Exception e){
                //TODO : Fix
                return false;
            }
        }

    }

    /****
     * Submit a class by his name before use
     *
     * @param klassName the class name
     * @return  true for success
     * @throws CsvValueFilterInconsistentStateException
     */
    @Override
    public boolean submitClass(String klassName) throws CsvValueFilterInconsistentStateException{
        if(this.classValues==null)throw new CsvValueFilterInconsistentStateException();
        this.classIDs.put(klassName,cnt);
        cnt+=1;
        return true;
    }
}
