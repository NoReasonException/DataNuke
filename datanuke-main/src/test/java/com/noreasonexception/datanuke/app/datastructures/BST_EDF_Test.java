package com.noreasonexception.datanuke.app.datastructures;

import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.Date;
@Ignore
public class BST_EDF_Test {
    private BST_EDF ref=null;
    @Before
    public void init(){
        ref=new BST_EDF();
    }

    /***
     * check if pollMin actually returns the minimum node
     */
    @Test
    public void pollMinTest(){
        ref.insert(10l,new ClassInfo("A1",new Date(),120,"ten"));
        ref.insert(20l,new ClassInfo("A2",new Date(),240,"twenty"));
        ref.insert(50l,new ClassInfo("A3",new Date(),360,"fifty"));
        ref.insert(1l,new ClassInfo("A4",new Date(),480,"one"));
        assertEquals(ref.pollMin().getClassname(),"one");

    }
}
