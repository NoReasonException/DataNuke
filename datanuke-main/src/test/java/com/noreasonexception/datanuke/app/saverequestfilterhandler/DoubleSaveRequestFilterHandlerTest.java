package com.noreasonexception.datanuke.app.saverequestfilterhandler;
import com.noreasonexception.datanuke.app.saverequestfilterhandler.error.GenericSaveRequestFilterException;
import com.noreasonexception.datanuke.app.saverequestfilterhandler.error.InconsistentStateException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Ignore
public class DoubleSaveRequestFilterHandlerTest {
    private DoubleSaveRequestFilterHandler filterFoundFile;
    private DoubleSaveRequestFilterHandler filterNonFoundFile;
    private static final String NOTFOUND_FILE="bla";
    private static final String FOUND_FILE="dat.csv";
    @Before
    public void restoreFilter(){
        Path p;
        try{
            if(Files.exists(p=Paths.get(NOTFOUND_FILE))){
                Files.delete(p);
            }
            if(!Files.exists(p=Paths.get(FOUND_FILE))){
                Files.createFile(p);
            }
        }catch (IOException e){}
        this.filterNonFoundFile=new DoubleSaveRequestFilterHandler(NOTFOUND_FILE);
        this.filterFoundFile=new DoubleSaveRequestFilterHandler(FOUND_FILE);
    }

    @Test(expected = InconsistentStateException.class)
    public void invalidStateCheck() throws GenericSaveRequestFilterException {

        System.out.println(filterFoundFile.getIdByClassObj(String.class.getName()));

    }
    @Test
    public void buildTest() throws GenericSaveRequestFilterException {

        filterFoundFile.buildFromFile();

    }

    /***
     * Tests if the builder method will throw Exception in case of any error(example nonexistent file)
     * @throws GenericSaveRequestFilterException
     */
    @Test(expected = GenericSaveRequestFilterException.class )
    public void buildTestFail() throws GenericSaveRequestFilterException {

        filterNonFoundFile.buildFromFile();

    }
    @Test(expected = InconsistentStateException.class)
    public void classNotRegisteredTest() throws GenericSaveRequestFilterException {
        filterFoundFile.buildFromFile().submitValue(String.class.getName(),0d);
    }

    @Test
    public void registerValuesWithConsistentStateTest() throws GenericSaveRequestFilterException {
        //TODO make test independent from real dat.csv file
        filterFoundFile.buildFromFile();
        filterFoundFile.submitClass(String.class.getName());
        filterFoundFile.submitClass(Integer.class.getName());

        System.out.println(filterFoundFile.submitValue(String.class.getName(),3d));
        //filterFoundFile.submitValue(Integer.class,0d); //when we submitClass , the initial value is zero
    }
    @Test(expected = InconsistentStateException.class)
    public void registerClassInInvalidState()throws InconsistentStateException {
        filterFoundFile.submitClass(String.class.getName());
    }

}
