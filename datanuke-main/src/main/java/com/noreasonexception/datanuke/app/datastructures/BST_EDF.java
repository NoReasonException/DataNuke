package com.noreasonexception.datanuke.app.datastructures;

import com.noreasonexception.datanuke.app.datastructures.interfaces.EarliestDeadlineFirst_able;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;

public class BST_EDF extends BinarySearchTree<Long,ClassInfo>implements EarliestDeadlineFirst_able<Long,ClassInfo> {

    @Override
    public void insert(Long aLong, ClassInfo classInfo) {
        _insert(root,aLong,classInfo);
    }

    @Override
    public ClassInfo search(Long aLong) {

        return _search(root,aLong);
    }

    @Override
    public ClassInfo pollMin() {

        Node min, newMin,retval;
        min = (retval=_getMin(root)).getParent();
        if (min == null) {
            root = root.getRight();
            root.setParent(null);

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

