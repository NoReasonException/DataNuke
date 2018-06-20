package com.noreasonexception.datanuke.app.ValueFilter;

import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import org.junit.Before;
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
        this.filterNonFoundFile=new CsvValueFilter(2,NOTFOUND_FILE);
        this.filterFoundFile=new CsvValueFilter(2,FOUND_FILE);
    }

    @Test(expected = CsvValueFilterException.class)
    public void FileContextToArray_fileNotExistException() throws CsvValueFilterException{

        CsvValueFilter filter=new CsvValueFilter(2,NOTFOUND_FILE);

        System.out.println(filter.fileContextToArray());

    }
    @Test(expected = IOException.class)
    public void fileContextToString_fileNotExistException() throws IOException{

        CsvValueFilter filter=new CsvValueFilter(2,NOTFOUND_FILE);

        System.out.println(filter.fileContextToString());

    }

}
