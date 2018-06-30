import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import jdk.nashorn.api.tree.Tree;
import org.w3c.dom.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

/***
 * Balanced 2-3 tree (worst time log(n))
 * @param <Key>
 * @param <Value>
 */
public class Tree23<Key extends Comparable<Key>,Value>{
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
    protected class Node2 implements Comparable<Node2>{
        public int compareTo(Node2 o) {
            return getKey().compareTo(o.getKey());
        }
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

        @Override
        public String toString() {
            return "Node2{" +
                    "key=" + key +
                    '}';
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
    protected Tree23.Node3 getInstance3(Key k1,Value v1,Key k2,Value v2){
        return (Tree23.Node3)new Tree23.Node4(k1,v1,k2,v2,0,0).setNodeType(3);
    }
    protected class Node3 extends Node2{

        private Key key2;
        private Value value2;
        private Tree23.Node2 mid;
        public          Node3       (Key key, Value value, Key key2, Value value2) { super(key, value);this.key2 = key2;this.value2 = value2;setNodeType(3);}
        public Node3    setValue2   (Value value2) { this.value2 = value2;return this; }
        public Node3    setKey2     (Key key2) {this.key2 = key2;return this;}
        public Node3    setMid      (Node2 mid) {this.mid = mid;return this;}
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
        @SuppressWarnings("unchecked")
        public Node2    swapLinks(String one,String two){
            try{
                Method onesGet=getClass().getMethod("get"+one);
                Method twosGet=getClass().getMethod("get"+two);
                Method onesSet=getClass().getMethod("set"+one);
                Method twosSet=getClass().getMethod("set"+two);
                Node2 oneNode=(Tree23.Node2)onesGet.invoke(this);
                Node2 twoNode=(Tree23.Node2)twosGet.invoke(this);
                onesSet.invoke(this,twoNode);
                twosSet.invoke(this,oneNode);
                return this;

            }catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e){
                throw new InvalidParameterException(".swapLinks faild due to "+e.getMessage());
            }

        }

        @Override
        public String toString() {
            if(getNodeType()!=3)return super.toString();
            return "Node3{" +
                    "key=" + getKey() +
                    ", key2=" + key2 +
                    '}';
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

        /***
         * swapKeysAndValues does exactly what her name suggests .
         * For example , if we have a node3 with values (1,9,2) , then a call with parameters 1,2 will
         * result in a node3 with values  (1,2,9)
         *
         * @param one
         * @param two
         * @return
         */
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

        @Override
        public String toString() {
            if(getNodeType()!=4)return super.toString();
            return "Node4{" +
                    "key=" + getKey() +
                    ", key2=" + getKey2()+
                    ", key3=" + key3 +
                    '}';
        }
    }
    protected static boolean deleteChild(Tree23.Node2 parent,Tree23.Node2 child){
        Tree23.Node3 ref=(Tree23.Node3)parent;
        if(parent==null||child==null)return false;
        if(ref.getLeft()==child){ref.setLeft(null);return true;}
        else if(ref.getMid()==child){ref.setMid(null);return true;}
        else if(ref.getRight()==child){ref.setRight(null);return true;}
        return false;
    }
    protected static boolean isNode2(Tree23.Node2 node2){
        return node2!=null&&node2.getNodeType()==2;
    }
    protected static Tree23.Node2 getNode2Ref(Tree23.Node2 node2){
        return isNode2(node2)?(Tree23.Node2)node2:null;
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
        return node2!=null&&node2.getNodeType()==3;
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

        return makeNode3LinksConsistent(node3);
    }
    protected Tree23.Node3 makeNode3LinksConsistent(Tree23.Node3 node){
        ArrayList<Tree23.Node2> childs=getChildsOfNode3(node);
        Node2 tmp1,tmp2,tmp3;
        switch (childs.size()){
            case 0:return node;
            case 1:{node.setLeft(childs.get(0));return node;}
            case 2:{
                tmp1=Collections.min(childs);
                tmp2=Collections.max(childs);
                dismissAllChildsOfNode3(node);
                node.setLeft(tmp1);
                node.setRight(tmp2);
                return node;
            }
            case 3:{
                childs.remove(tmp1=Collections.min(childs));
                childs.remove(tmp2=Collections.max(childs));
                dismissAllChildsOfNode3(node);
                tmp3=childs.get(0);
                node.setLeft(tmp1);
                node.setRight(tmp2);
                node.setMid(tmp3);
                return node;
            }

        }
        return null;
    }
    protected ArrayList<Tree23.Node2> getChildsOfNode3(Tree23.Node3 node3){
        ArrayList<Tree23.Node2> retval=new ArrayList<>();
        Node2 tmp;
        if((tmp=node3.getLeft())!=null)retval.add(tmp);
        if((tmp=node3.getMid())!=null)retval.add(tmp);
        if((tmp=node3.getRight())!=null)retval.add(tmp);
        return retval;
    }
    protected void dismissAllChildsOfNode3(Tree23.Node3 node3){
        node3.setLeft(null);
        node3.setMid(null);
        node3.setRight(null);
    }
    protected static boolean isNode4(Tree23.Node2 node2){
        return node2!=null&&node2.getNodeType()==4;
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
        _insert(k,v,false);
    }

    public void insert(Key k, Value v,boolean trace) {
        _insert(k,v,trace);
    }

    public Node2 getCachedParent() {
        return cachedParent;
    }
    @SuppressWarnings("unchecked")
    protected void _insert(Key k, Value v,boolean trace){
        try{search(k);return; /*in case that exists , then will immidiatelly return */}catch (InvalidParameterException e){ }
        Node2 node2ref0,node2ref1;
        Node3 node3ref0,node3ref1;
        Node4 node4ref0;
        if(root==null){
            if(trace) System.out.println("root == null condition");
            root=getInstance2(k,v);
            return;
        }

        else if(isNode3(root)){
            System.out.println("root is node3");
            root=transformNode4IntoNode2Subtree(
                    makeNode4Consistent(
                            node4ref0=transformIntoNode4(
                                    getNode3Ref(root)).
                                    setKey3(k).
                                    setValue3(v)));
            return;
        }
        else if(!isNode3(getPathElement(0))){
            System.out.println("parent is no node3");
            makeNode3Consistent(
                    transformIntoNode3(getPathElement(0))
                            .setKey2(k)
                            .setValue2(v));
            return;
        }

        else if(isNode3(node3ref0=(Node3)getPathElement(0))&&isNode2(node2ref0=getPathElement(1))){
            if(deleteChild(node2ref0,node3ref0)){
                node4ref0=(makeNode4Consistent(transformIntoNode4(node3ref0).setKey3(k).setValue3(v)));
                node2ref1=(transformNode4IntoNode2Subtree(node4ref0));
                node3ref0=(makeNode3Consistent((Node3)transformIntoNode3(node2ref0)
                        .setKey2(node2ref1.getKey())
                        .setMid(node2ref1.getLeft())
                        .setRight(node2ref1.getRight())));
            }
        }
        return;

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
        cachedParent=curr;
        this.cachedPath.add(0,curr);
        int compare;
        if((compare=curr.getKey().compareTo(k))==0)return curr;
        if((node3ref=getNode3Ref(curr))!=null &&node3ref.getKey2().compareTo(k)==0){ return curr; }

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
    }
    protected Node2 getPathElement(int i){
        try{
            return this.cachedPath.get(i);

        }catch (IndexOutOfBoundsException e){return null;}
    }
    public static Tree23<Integer,String> _test_node2parent_node3child(){
        Tree23<Integer,String> s=new Tree23<>();
        s.root=s.getInstance2(10,"hey!");
        s.root.setLeft(s.getInstance3(3,"he",5,"ho"));
        return s;
    }
    public void print(){
        bfs(root);
    }
    private void bfs(Node2 root){
        if(root==null)return;
        bfs(root.getLeft());
        if(root.getNodeType()==3)bfs(((Node3)root).getMid());
        System.out.println(root);
        bfs(root.getRight());
    }
    public Tree23() {
        this.root = null;
        this.cachedParent=null;
        this.cachedPath=new ArrayList<Node2>();
    }
}