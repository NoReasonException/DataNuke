package com.noreasonexception.datanuke.app.datastructures;


import com.noreasonexception.datanuke.app.datastructures.interfaces.ITree;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/***
 * Balanced 2-3 tree (worst time log(n))
 * @param <Key>
 * @param <Value>
 */
public class Tree23<Key extends Comparable<Key>,Value> implements ITree<Key,Value> {
    protected Node2 root;
    protected Node2 cachedParent;
    protected ArrayList<Node2> cachedPath;
    @SuppressWarnings("unchecked")
    protected Tree23.Node2 getInstance2(Key k,Value v){return new Tree23.Node3(k,v,0,0).setNodeType(2);}
    protected class Node2{
        public                  Node2   (Key key, Value value) { this.key = key;this.value = value;}
        public  Node2           setRight(Tree23.Node2 right) { this.right = right; return this;}
        public  Node2           setLeft (Tree23.Node2 left) { this.left = left;return this; }
        public  Node2           setNodeType(int nodeType) { this.nodeType = nodeType;return this; }
        public  int             getNodeType() { return nodeType; }
        public  Tree23.Node2    getLeft()   { return left; }
        public  Tree23.Node2    getRight()  { return right;}
        public  Value           getValue()  { return value;}
        public  Key             getKey()    { return key;}
        private int             nodeType=2;
        private Tree23.Node2    left,right;
        private Value           value;
        private Key             key;


    }

    @SuppressWarnings("unchecked")
    protected Tree23.Node3 getInstance3(Key k1,Value v1,Key k2,Value v2){return (Tree23.Node3)new Tree23.Node3(k1,v1,k2,v2).setNodeType(3);}
    protected class Node3 extends Node2{

        private Key key2;
        private Value value2;
        private Tree23.Node2 mid;
        public          Node3       (Key key, Value value, Key key2, Value value2) { super(key, value);this.key2 = key2;this.value2 = value2;setNodeType(3);}
        public Node3    setValue2   (Value value2) { this.value2 = value2;return this; }
        public Node3    setKey2     (Key key2) {this.key2 = key2;return this;}
        public Node3    setMid      (Node3 mid) {this.mid = mid;return this;}
        public Value    getValue2   () { return value2; }
        public Key      getKey2     () { return key2; }
        public Node2    getMid()    { return mid; }
    }

    /***
     * Why we remove the instanceof implementation?
     * Because is SOOOOOO EASY to transform a Node2 to Node3! just change a member
     * To transform a node2 to Node3 with the previous implementation , we must
     * 1) find the father of node
     * 2) make a new Node3 node
     * 3) transfer values
     * 3) change references in father to new Node3 child
     *
     * now we can just...
     * 1)curr.setNodeType(3)
     * 2)cast and put the new values :)
     *
     * @param node2
     * @return
     */
    protected static boolean isNode3(Tree23.Node2 node2){
        return node2.getNodeType()==3;
    }
    protected static Tree23.Node3 convertToNode3(Tree23.Node2 node2){
        return isNode3(node2)?(Tree23.Node3)node2:null;
    }
    @Override
    public void insert(Key k, Value v) {
        _insert(root,k,v);
    }

    public Node2 getCachedParent() {
        return cachedParent;
    }

    protected void _insert(Node2 curr, Key k, Value v){
        if(root==null){root=getInstance2(k,v);return;}
        Node2 ref;
        try{ search(k);return;
        }catch (InvalidParameterException e){ }
        if(!isNode3(getPathElement(0))){

        }

    }
    @Override
    public void delete(Key k) {
        throw new InvalidParameterException();

    }

    @Override
    public Value search(Key k) {
        clearCache();
        Node2 node2=_search(root,k);
        if(node2.getKey().compareTo(k)==0)return node2.value;
        else if(isNode3(node2))return ((Node3)node2).getValue2();
        throw new InvalidParameterException("neither node 2 or 3 ...?");
    }

    @SuppressWarnings("unchecked")
    protected Node2 _search(Node2 curr,Key k){
        if(curr==null)throw new InvalidParameterException("Not exists!");
        Tree23.Node3 node3ref;
        int compare;
        if((compare=curr.getKey().compareTo(k))==0)return curr;
        cachedParent=curr;
        this.cachedPath.add(curr);
        if(compare<0)return _search(curr.getLeft(),k);
        if((node3ref=convertToNode3(curr))!=null){
            if(node3ref.getKey2().compareTo(k)<-1) return _search(node3ref.getMid(),k);
        }
        return _search(curr.getRight(),k);


    }

    @Override
    public Value pollMin() {
        throw new InvalidParameterException();

    }
    protected void clearCache(){
        this.cachedPath.clear();
        this.cachedParent=null;
    }
    protected Node2 getPathElement(int i){return this.cachedPath.get(i);}
    public Tree23() {
        this.root = null;
        this.cachedParent=null;
        this.cachedPath=new ArrayList<Node2>();
    }
}
