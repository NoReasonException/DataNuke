package com.noreasonexception.datanuke.app.ValueFilter;
import static org.junit.Assert.*;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterInconsistentStateException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CsvValueFilterTest {
    private CsvValueFilter filterFoundFile;
    private CsvValueFilter filterNonFoundFile;
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
        this.filterNonFoundFile=new CsvValueFilter(NOTFOUND_FILE);
        this.filterFoundFile=new CsvValueFilter(FOUND_FILE);
    }

    @Test(expected = CsvValueFilterInconsistentStateException.class)
    public void invalidStateCheck() throws CsvValueFilterException{

        System.out.println(filterFoundFile.getIdByClassObj(String.class.getName()));

    }
    @Test
    public void buildTest() throws CsvValueFilterException{

        filterFoundFile.buildFromFile();

    }

    /***
     * Tests if the builder method will throw Exception in case of any error(example nonexistent file)
     * @throws CsvValueFilterException
     */
    @Test(expected = CsvValueFilterException.class )
    public void buildTestFail() throws CsvValueFilterException{

        filterNonFoundFile.buildFromFile();

    }
    @Test(expected = CsvValueFilterInconsistentStateException.class)
    public void classNotRegisteredTest() throws CsvValueFilterException{
        filterFoundFile.buildFromFile().submitValue(String.class.getName(),0d);
    }

    @Test
    public void registerValuesWithConsistentStateTest() throws CsvValueFilterException{
        filterFoundFile.buildFromFile();
        filterFoundFile.submitClass(String.class.getName());
        //filterFoundFile.submitClass(Integer.class);

        System.out.println(filterFoundFile.submitValue(String.class.getName(),3d));
        //filterFoundFile.submitValue(Integer.class,0d); //when we submitClass , the initial value is zero
    }
    @Test(expected = CsvValueFilterInconsistentStateException.class)
    public void registerClassInInvalidState()throws CsvValueFilterInconsistentStateException{
        filterFoundFile.submitClass(String.class.getName());
    }

}
