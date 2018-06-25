package com.noreasonexception.datanuke.app.datastructures;

import com.noreasonexception.datanuke.app.datastructures.interfaces.ITree;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;

public class BST_EDF extends BinarySearchTree<Long,ClassInfo>implements ITree<Long,ClassInfo> {
    @Override
    public ClassInfo pollMin() {
        Node min;
        ClassInfo classInfo=(min=_getMin(root)).getValue();
        delete(min.getKey());
        return classInfo;



    }
}

