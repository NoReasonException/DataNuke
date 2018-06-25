package com.noreasonexception.datanuke.app.threadRunner;

import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;
import org.junit.Test;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.Date;

public class ThreadRunnerStaticTests {
    @Test
    public void millsToSec(){
        assertEquals(AbstractThreadRunner.millsToSec(1000),1);
    }

    @Test
    public void secToMills(){
        assertEquals(AbstractThreadRunner.secToMills(1),1000);

    }

    @Test
    public void getRemainingTime(){
    }

    @Test
    public void getDeadline(){
    }



    @Test
    public void getWaitTime(){

    }

}
