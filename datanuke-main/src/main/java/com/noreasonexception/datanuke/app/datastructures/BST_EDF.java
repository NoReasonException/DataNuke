package com.noreasonexception.datanuke.app.datastructures;

import com.noreasonexception.datanuke.app.datastructures.interfaces.ITree;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;

public class BST_EDF extends BinarySearchTree<Long,ClassInfo>implements ITree<Long,ClassInfo> {

    @Override
    public void insert(Long aLong, ClassInfo classInfo) {
        _insert(root,aLong,classInfo);
    }

    @Override
    public ClassInfo search(Long aLong) {

        return _search(root,aLong);
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public ClassInfo pollMin() {

        Node min, newMin,retval;
        min = (retval=_getMin(root)).getParent();
        if (min == null) {
            root.setParent(null);
            root = root.getRight();

        }
        else {
            min.setLeft(newMin=min.getLeft().getRight());
            if(newMin!=null){
                min.getLeft().setParent(min);

            }
        }
        return retval.getValue();

    }
}

