package com.noreasonexception.datanuke.app.saverequestfilterhandler;


import com.noreasonexception.datanuke.app.fileprotocol.ClientRequirementFileProtocol;
import com.noreasonexception.datanuke.app.fileprotocol.IntervalCsvFileProtocol;
import com.noreasonexception.datanuke.app.fileprotocol.ListToFileProtocol;
import com.noreasonexception.datanuke.app.saverequestfilterhandler.error.ClassNotRegisteredException;
import com.noreasonexception.datanuke.app.saverequestfilterhandler.error.InconsistentStateException;
import com.noreasonexception.datanuke.app.saverequestfilterhandler.error.MailformedFileException;
import com.noreasonexception.datanuke.app.saverequestfilterhandler.error.GenericSaveRequestFilterException;
import com.noreasonexception.datanuke.app.saverequestfilterhandler.interfaces.MostRecentUnixTimestampFileFilter;
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
 * At first The DoubleSaveRequestFilterHandler reads all the contexts of the file given in constructor
 * Lets suppose that the file contains n values
 *
 * Now , the object has n values but 0 classes . so it is in Invalid State .
 * When the object is in invalid state ,every operation ,except .submitClass(),
 * will throw an InconsistentStateException .
 *
 * The object , to be consistent , expect n .submitClass() calls
 * The first call will correlate the first class into the first value came from file and vice versa
 *
 * =====================================================================================================================
 * Every call into .submitValue() will return true if the RequestParser class finds an new value
 *
 */
public class DoubleSaveRequestFilterHandler implements SaveRequestFilterHandler<Double> {
    private Hashtable<String,Integer>   classIDs;
    private ArrayList<Double>           classValues;
    private java.lang.String            directory;
    private java.lang.String            lastClassAquiredLock;
    private DataProvider                fileDataProvider;
    private ListToFileProtocol<Double>  internalCsvFileProtocol;
    private ListToFileProtocol<Double>  clientRequirementFileProtocol;
    private static int                  cnt=0;


    public DoubleSaveRequestFilterHandler(String directory){
        this.classIDs=new Hashtable<>();
        this.directory =directory; //TODO Remove , pass directly to InternalCsvFileProtocol
        this.internalCsvFileProtocol=new IntervalCsvFileProtocol(this.directory);
        this.clientRequirementFileProtocol=new ClientRequirementFileProtocol(this.directory);

    }

    /****
     * Call always before any operation , otherwise , a InconsistentStateException will be thrown.
     * @return this object
     * @throws GenericSaveRequestFilterException in case of any error(IOE or corrupted file)
     */
    @Override
    public DoubleSaveRequestFilterHandler buildFromFile() throws GenericSaveRequestFilterException {
        this.classValues=this.fileContextToArray();
        return this;
    }

    /****
     * .fileContextToString()
     * just reads the initial file data returs it as plain string
     * @return the contexts of file as String
     * @throws GenericSaveRequestFilterException in case any type of IOException
     */
    /*Package-Private*/String fileContextToString() throws GenericSaveRequestFilterException {
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

    /***
     * .getNewInternalFileName()
     * This method returns the filename used to save the runtime context
     * @return a string representing the filename
     */
    private  String getNewInternalFileName(){
        return (System.currentTimeMillis()/1000)+".csv";
    }

    /****
     * .getnewUserDefinedFileName()
     * This method returns the filename used to interfere with the another product .is a terrible way of communication
     * i know , but whatever the client wants
     * @implNote luckily , the client-defined file name is simmilar with our internal file name
     *              just add a "news" prefix :) ;)
     * TODO consider making a major refactor to a more-flexible design
     * @return a string representing the filename
     */
    private  String getNewUserDefinedFileName(){
        return "news"+getNewInternalFileName();
    }

    /***
     * Returns the filename with the proper directory as prefix
     * @param filename  the name of the file
     * @return          the full path(use that to open a stream maybe?)
     */
    private String  getFullPathOf(String filename){return directory+filename;}
    /****
     * parser of .csv files , returns a ArrayList with the values per-class
     * @return the ArrayList with the values
     * @throws MailformedFileException in case of error in sundax of file
     * @throws GenericSaveRequestFilterException in case of any IOException
     */
    /*Package-Private*/ ArrayList<Double> fileContextToArray()throws GenericSaveRequestFilterException,
            MailformedFileException {
        String tmp="none";
        try{
            String str = fileContextToString();
            ArrayList<Double> retval=new ArrayList<>();
            for (String s:str.split(",")){ //NumberFormatException
                retval.add(Double.valueOf(tmp=s));
            }
            return retval;
        }catch (NumberFormatException|NoSuchElementException e){
            throw new MailformedFileException(
                    "The parser detected something is not an number "+e.getMessage()+"("+tmp+")",e);
        }

    }

    /****
     * gets the id of a class by using its name
     *
     * @param className the class name as string
     * @return the class id to be used in this.classValues
     * @throws InconsistentStateException in case of calling this method without call .buildFromFile() first
     */
    /*Package-Private*/ int getIdByClassObj(String className) throws GenericSaveRequestFilterException {
        if (this.classValues==null)throw new InconsistentStateException();
        Integer id;
        if((id=classIDs.get(className))==null)return -1;
        return id;

    }

    @Override
    public boolean sameValueSituation(String className) throws GenericSaveRequestFilterException {
        return __submitValue(className,getCSVContext().get(getIdByClassObj(className)),false);
    }

    protected Object getLockObject(){return this;}

    /***
     * just a funky getter
     */
    protected ArrayList<Double> getCSVContext(){
        return this.classValues;
    }


    /****
     * a thin wrapper over real __saveCSVContext
     * Why?
     *      After a request of changed requirements , i am forced to save the actual contents into two files
     *      one file is the <<interface>> with another product . It is a terrible way but , whatever the client wants :/
     *
     *      starting with version 4.0 , all content will be saved in two files
     * @see .saveCSVContext()
     */
    protected boolean saveCSVContext(int issuingClassID){
        return /*__saveCSVContext(getFullPathOf(getNewInternalFileName())) &&
                __saveCSVContext(getFullPathOf(getNewUserDefinedFileName()));*/
                this.internalCsvFileProtocol.saveList(this.classValues,null) &&
                this.clientRequirementFileProtocol.saveList(this.classValues,new Object[]{issuingClassID});

    }
    /****
     * saves every value of this.classValues into actual file
     * @return true on success
     */
    protected boolean  __saveCSVContext(String filename) {
        try {
            Path filePath=Paths.get(filename);
            FileChannel byteChannel = (FileChannel) FileChannel.open(filePath,StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE);

            byteChannel.write(
                    ByteBuffer.wrap(
                            Arrays.toString(
                                    this.classValues.toArray()).
                                    replace("[","").
                                    replace("]","").getBytes())
            );

            byteChannel.close();
            System.out.println("Operation on "+filename+" completed");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
    @Override
    public boolean submitValue(String klassName, Double value) throws GenericSaveRequestFilterException {
        return __submitValue(klassName,value,true);
    }
    /****
     * the real submitValue()
     * @param klassName the class submitted the value
     * @param value the actual value
     * @param sameRejectionCheck , if a request for a submit value received ,
     *                           with this option engaged , if the value is the same the call will be just return false (will fail)
     * @return true in success.
     */
    synchronized public boolean __submitValue(String klassName, Double value,boolean sameRejectionCheck) throws GenericSaveRequestFilterException {
        synchronized (getLockObject()){
            lastClassAquiredLock=klassName;
            try {
                int id;
                if(this.classValues.size()!=this.classIDs.size())
                    throw new InconsistentStateException(classValues.size()+"-"+classIDs.size());
                if((id= getIdByClassObj(klassName))==-1){
                    throw new ClassNotRegisteredException(klassName);
                }

                if(!sameRejectionCheck || getCSVContext().get(id).compareTo(value)!=0){
                    getCSVContext().set(id,value);
                    saveCSVContext(id);
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
     * @throws InconsistentStateException
     */
    @Override
    synchronized public boolean submitClass(String klassName) throws InconsistentStateException {
        if(this.classValues==null)throw new InconsistentStateException();
        this.classIDs.put(klassName,cnt);
        cnt+=1;
        return true;
    }
}
