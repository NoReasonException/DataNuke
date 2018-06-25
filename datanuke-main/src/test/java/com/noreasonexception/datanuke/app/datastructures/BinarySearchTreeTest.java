package com.noreasonexception.datanuke.app.datastructures;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.security.InvalidParameterException;


public class BinarySearchTreeTest {
    private BinarySearchTree<Integer,String> ref=null;
    @Before
    public void init(){
        ref=new BinarySearchTree<>();
    }
    @Test(expected = InvalidParameterException.class)
    public void emptyDeleteCheck()throws InvalidParameterException{
        ref.delete(10);
    }
    @Test
    public void checkIfDeleteEverythingAndReInsertNotThrowsNullPointerException(){
        ref.insert(10,"Hey");
        ref.delete(10);
        ref.insert(20,"Hey");
        assertEquals(ref.search(20),"Hey");
    }

}
