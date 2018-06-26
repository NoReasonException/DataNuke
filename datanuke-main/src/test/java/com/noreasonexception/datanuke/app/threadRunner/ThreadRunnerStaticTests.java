package com.noreasonexception.datanuke.app.threadRunner;

import com.noreasonexception.datanuke.app.threadRunner.error.ThreadRunnerStaticTestException;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;
import junit.framework.TestFailure;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.Date;

public class ThreadRunnerStaticTests {
    @Test
    public void millsToSecTest(){
        assertEquals(AbstractThreadRunner.millsToSec(1000),1);
    }

    @Test
    public void secToMillsTest(){
        assertEquals(AbstractThreadRunner.secToMills(1),1000);

    }

    @Test
    public void getRemainingTimeTest() throws ThreadRunnerStaticTestException{
        if(AbstractThreadRunner.getRemainingTime(System.currentTimeMillis()+1500)<1400){
            throw new ThreadRunnerStaticTestException("getRemainingTime failed in the .getRemainingTimeTest() Test");
        }
    }

    @Test
    public void getDeadlineTest(){
        long i;
        assertEquals(AbstractThreadRunner.getDeadline((i=System.currentTimeMillis())-1500,i,500),
                i+500);
    }



    @Test
    public void getWaitTimeTest(){
        long i;
        assertEquals(AbstractThreadRunner.getWaitTime((i=System.currentTimeMillis())-1500,i,500),
                500);
    }

}
