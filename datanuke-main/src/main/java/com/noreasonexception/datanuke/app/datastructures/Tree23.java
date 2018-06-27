package com.noreasonexception.datanuke.app.datastructures;


import com.noreasonexception.datanuke.app.datastructures.interfaces.ITree;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import org.w3c.dom.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/***
 * Balanced 2-3 tree (worst time log(n))
 * @param <Key>
 * @param <Value>
 */
public class Tree23<Key extends Comparable<Key>,Value> implements ITree<Key,Value>{
    protected Node2 root;
    protected Node2 cachedParent;
    protected ArrayList<Node2> cachedPath;

    /***
     * Create an instance of node 2 , exists only to call .setNodeType and declare it as node2
     * @param k the key
     * @param v the value
     * @return a Tree23.Node2 reference
     */
    @SuppressWarnings("unchecked")
    protected Tree23.Node2 getInstance2(Key k,Value v){
        return (Tree23.Node2)new Tree23.Node4(k,v,0,0,0,0).setNodeType(2);
    }
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
        public  Node2           setKey(Key k)  { key=k;return this;}
        public  Node2           setValue(Value v)    { value=v;return this;}
        private int             nodeType=2;
        private Tree23.Node2    left,right;
        private Value           value;
        private Key             key;


    }

    /***
     * Creates an Tree23.Node3
     * @param k1 key 1
     * @param v1 value 1
     * @param k2 key 2
     * @param v2 value 2
     * @return a Tree23.Node 3 ref
     */
    @SuppressWarnings("unchecked")
    protected Tree23.Node3 getInstance3(Key k1,Value v1,Key k2,Value v2){return (Tree23.Node3)new Tree23.Node4(k1,v1,k2,v2,0,0).setNodeType(3);}
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
        public Node2    swapKeysAndValues(){
            Key k=getKey2();
            Value v=getValue2();
            setKey2(getKey());
            setValue2(getValue());
            setKey(k);
            setValue(v);
            return this;
        }
    }
    /***
     * Creates an Tree23.Node3
     * @param k1 key 1
     * @param v1 value 1
     * @param k2 key 2
     * @param v2 value 2
     * @return a Tree23.Node 3 ref
     */
    @SuppressWarnings("unchecked")
    protected Tree23.Node4 getInstance4(Key k1,Value v1,Key k2,Value v2,Key k3,Value v3){
        return (Tree23.Node4)new Node4(k1,v1,k2,v2,k3,v3).setNodeType(4);
    }
    protected class Node4 extends Node3{
        public Key key3;
        public Value value3;
        public Tree23.Node2 midToLeftLink;
        public Node4(Key key, Value value, Key key2, Value value2,Key k3,Value v3) { super(key, value, key2, value2);this.key3=k3;this.value3=v3; }
        public Key getKey3() { return key3; }
        public Node4 setKey3(Key key3) { this.key3 = key3;return this; }
        public Value getValue3() { return value3; }
        public Node4 setValue3(Value value3) { this.value3 = value3;return this; }
        public Node2 getMidToLeftLink() { return midToLeftLink; }
        public void setMidToLeftLink(Node2 midToLeftLink) { this.midToLeftLink = midToLeftLink; }
        //using in reflection hack...//TODO change this junk
        public Node2 setKey(Key k){return super.setKey(k);}
        public Key getKey(){return super.getKey();}
        public Node2 setValue(Value v){return super.setValue(v);}
        public Value getValue(){return super.getValue();}
        public Node3 setKey2(Key k){return super.setKey2(k);}
        public Key getKey2(){return super.getKey2();}
        public Node3 setValue2(Value v){return super.setValue2(v);}
        public Value getValue2(){return super.getValue2();}
        @SuppressWarnings("Duplicates , unchecked")
        public Node4 swapKeysAndValues(int one,int two) {
            if(one==two)throw new InvalidParameterException("same on swap , err");
            if(one>2||one<0||two>2||two<0)throw new IndexOutOfBoundsException("invalid parameter on swapKeysAndValues param");

            Key k;
            Value v;
            Method setterOfOneKey,
                    getterOfOneKey,
                    setterOfOneValue,
                    getterOfOneValue,
                    setterOfTwoKey,
                    getterOfTwoKey,
                    setterOfTwoValue,
                    getterOfTwoValue;
            try {
                switch (one){
                    case 0:{setterOfOneKey=getClass().getMethod("setKey", Comparable.class);
                        getterOfOneKey=getClass().getMethod("getKey");
                        setterOfOneValue=getClass().getMethod("setValue", Object.class);
                        getterOfOneValue=getClass().getMethod("getValue");break;}
                    case 1:{setterOfOneKey=getClass().getMethod("setKey2", Comparable.class);
                        getterOfOneKey=getClass().getMethod("getKey2");
                        setterOfOneValue=getClass().getMethod("setValue2", Object.class);
                        getterOfOneValue=getClass().getMethod("getValue2");break; }
                    case 2:{setterOfOneKey=getClass().getMethod("setKey3", Comparable.class);
                        getterOfOneKey=getClass().getMethod("getKey3");
                        setterOfOneValue=getClass().getMethod("setValue3", Object.class);
                        getterOfOneValue=getClass().getMethod("getValue3");break; }
                    default:return null;

                }
                switch (two){
                    case 0:{setterOfTwoKey=getClass().getMethod("setKey", Comparable.class);
                        getterOfTwoKey=getClass().getMethod("getKey");
                        setterOfTwoValue=getClass().getMethod("setValue", Object.class);
                        getterOfTwoValue=getClass().getMethod("getValue");break;}
                    case 1:{setterOfTwoKey=getClass().getMethod("setKey2", Comparable.class);
                        getterOfTwoKey=getClass().getMethod("getKey2");
                        setterOfTwoValue=getClass().getMethod("setValue2", Object.class);
                        getterOfTwoValue=getClass().getMethod("getValue2");break; }
                    case 2:{setterOfTwoKey=getClass().getMethod("setKey3", Comparable.class);
                        getterOfTwoKey=getClass().getMethod("getKey3");
                        setterOfTwoValue=getClass().getMethod("setValue3", Object.class);
                        getterOfTwoValue=getClass().getMethod("getValue3");break; }
                    default:return null;

                }
                k=(Key)getterOfTwoKey.invoke(this);
                v=(Value)getterOfTwoValue.invoke(this);
                setterOfTwoKey.invoke(this,getterOfOneKey.invoke(this));
                setterOfTwoValue.invoke(this,getterOfOneValue.invoke(this));
                setterOfOneKey.invoke(this,k);
                setterOfOneValue.invoke(this,v);
            } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }

            return this;
        }
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
     * @param node2 a reference to potentially Node2
     * @return true if @param node2 is really an node2
     */
    protected static boolean isNode3(Tree23.Node2 node2){
        return node2.getNodeType()==3;
    }
    protected static Tree23.Node3 getNode3Ref(Tree23.Node2 node2){
        return isNode3(node2)?(Tree23.Node3)node2:null;
    }
    protected static Tree23.Node3 transformIntoNode3(Tree23.Node2 node2){
        if(isNode3(node2))throw new InvalidParameterException("already node3");
        return (Tree23.Node3)node2.setNodeType(3);
    }
    protected Tree23.Node3 makeNode3Consistent(Tree23.Node3 node3){
        if(!isNode3(node3))throw new InvalidParameterException("is no node3 actually");

        if(node3.getKey().compareTo(node3.getKey2())>0){ node3.swapKeysAndValues(); }
        return node3;
    }
    protected static boolean isNode4(Tree23.Node2 node2){
        return node2.getNodeType()==4;
    }
    protected static Tree23.Node4 getNode4Ref(Tree23.Node2 node2){
        return isNode4(node2)?(Tree23.Node4)node2:null;
    }
    protected static Tree23.Node4 transformIntoNode4(Tree23.Node3 node3){
        if(isNode4(node3))throw new InvalidParameterException("already node4");
        return (Tree23.Node4)node3.setNodeType(4);
    }

    protected Tree23.Node4 makeNode4Consistent(Tree23.Node4 node4){
        if(!isNode4(node4))throw new InvalidParameterException("is no node4 actually");

        if(node4.getKey3().compareTo(node4.getKey())<0) node4.swapKeysAndValues(0,2);
        if(node4.getKey3().compareTo(node4.getKey2())<0) node4.swapKeysAndValues(1,2);
        return node4;
    }
    @SuppressWarnings("unchecked")
    protected Tree23.Node2 transformNode4IntoNode2(Tree23.Node4 node4){
        if(!isNode4(node4))throw new InvalidParameterException("node given is not type of Tree23.Node4");
        return node4.
                setKey3(0).
                setValue3(0).
                setKey2(0).
                setValue2(0).
                setNodeType(2);
    }
    @SuppressWarnings("unchecked")
    protected Tree23.Node2 transformNode4IntoNode2Subtree(Tree23.Node4 node4){
        if(!isNode4(node4)) throw new InvalidParameterException("node given is not type of Tree23.Node4");
        node4.setLeft(getInstance2((Key)node4.getKey(),(Value) node4.getValue()));
        node4.setRight(getInstance2((Key)node4.getKey3(),(Value)node4.getValue3()));
        node4.swapKeysAndValues(0,1);
        return transformNode4IntoNode2(node4);

    }
    public void insert(Key k, Value v) {
        _insert(root,k,v);
    }

    public Node2 getCachedParent() {
        return cachedParent;
    }
    @SuppressWarnings("unchecked")
    protected void _insert(Node2 curr, Key k, Value v){
        Node4 node4ref;
        if(root==null){
            root=getInstance2(k,v);
            return;
        }
        else if(isNode3(root)){
            root=transformNode4IntoNode2Subtree(
                    makeNode4Consistent(
                            node4ref=transformIntoNode4(
                                    getNode3Ref(root)).
                                    setKey3(k).
                                    setValue3(v)));
            return;
        }
        Node2 ref;
        try{
            search(k);return; //in case that exists , then will immidiatelly return
        }catch (InvalidParameterException e){ }
        if(!isNode3(getPathElement(0))){
            makeNode3Consistent(
                    transformIntoNode3(getPathElement(0))
                            .setKey2(k)
                            .setValue2(v));
            return;
        }

    }
    public void delete(Key k) {
        throw new InvalidParameterException();

    }
    public Value search(Key k) {
        clearCache();
        Node2 node2=_search(root,k);
        if(node2.getKey().compareTo(k)==0)return node2.value;
        else if(isNode3(node2) && ((Node3)node2).getKey2().compareTo(k)==0)return ((Node3)node2).getValue2();
        throw new InvalidParameterException("neither node 2 or 3 ...?");
    }

    @SuppressWarnings("unchecked")
    protected Node2 _search(Node2 curr,Key k){
        if(curr==null)throw new InvalidParameterException("Not exists!");
        Tree23.Node3 node3ref;
        int compare;
        if((compare=curr.getKey().compareTo(k))==0)return curr;
        if((node3ref=getNode3Ref(curr))!=null &&node3ref.getKey2().compareTo(k)==0){ return curr; }
        cachedParent=curr;
        this.cachedPath.add(curr);
        System.out.println(compare);
        if(compare>0)return _search(curr.getLeft(),k);

        else if(node3ref!=null){
            if(node3ref.getKey2().compareTo(k)>-1) return _search(node3ref.getMid(),k);
        }
        return _search(curr.getRight(),k);


    }
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
