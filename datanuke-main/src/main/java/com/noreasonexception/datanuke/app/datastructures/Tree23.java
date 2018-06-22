package com.noreasonexception.datanuke.app.datastructures;

import com.noreasonexception.datanuke.app.datastructures.interfaces.ITree;
import com.sun.istack.internal.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import sun.reflect.generics.tree.Tree;

import java.security.InvalidParameterException;

/***
 * Balanced 2-3 tree (worst time log(n))
 * @param <Key>
 * @param <Value>
 */
public class Tree23<Key extends Comparable<Key>,Value> implements ITree<Key,Value> {
    protected Node2 root;
    protected class Node2{
        public                  Node2   (Key key, Value value) { this.key = key;this.value = value;}
        public  void            setRight(Tree23.Node2 right) { this.right = right; }
        public  void            setLeft (Tree23.Node2 left) { this.left = left; }
        public  Tree23.Node2    getLeft()   { return left; }
        public  Tree23.Node2    getRight()  { return right;}
        public  Value           getValue()  { return value;}
        public  Key             getKey()    { return key;}
        private Tree23.Node2    left,right;
        private Value           value;
        private Key             key;

    }
    protected class Node3 extends Node2{
        private Key key2;
        private Value value2;
        private Tree23.Node2 mid;
        public          Node3       (Key key, Value value, Key key2, Value value2) { super(key, value);this.key2 = key2;this.value2 = value2; }
        public void     setValue2   (Value value2) { this.value2 = value2; }
        public void     setKey2     (Key key2) {this.key2 = key2;}
        public void     setMid      (Node3 mid) {this.mid = mid;}
        public Value    getValue2   () { return value2; }
        public Key      getKey2     () { return key2; }
        public Node2    getMid()    { return mid; }
    }

    protected static boolean isNode3(Tree23.Node2 node2){
        return node2 instanceof Tree23.Node3;
    }
    protected static Tree23.Node3 convertToNode3(Tree23.Node2 node2){
        return isNode3(node2)?(Tree23.Node3)node2:null;
    }
    @Override
    public void insert(Key k, Value v) {
        if(root==null)root=new Node2(k,v);
        else throw new NotImplementedException();
    }

    @Override
    public void delete(Key k) {
        throw new NotImplementedException();

    }

    @Override
    public Value search(Key k) {
        Node2 node2=_search(root,k);
        if(node2.getKey().compareTo(k)==0)return node2.value;
        else if(isNode3(node2))return ((Node3)node2).getValue2();
        throw new InvalidParameterException("neither node 2 or 3 ...?");
    }

    @SuppressWarnings("unchecked")
    protected Node2 _search(Node2 curr,Key k){
        Tree23.Node3 node3ref;
        int compare;
        if((compare=curr.getKey().compareTo(k))==0)return curr;
        else if(compare<0)return _search(curr.getLeft(),k);
        if((node3ref=convertToNode3(curr))!=null){
            if(node3ref.getKey2().compareTo(k)<-1) return _search(node3ref.getMid(),k);
        }
        return _search(curr.getRight(),k);


    }

    @Override
    public Value pollMin() {
        throw new NotImplementedException();

    }

    public Tree23() {
        this.root = null;
    }
}
