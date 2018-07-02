import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.*;

import jdk.nashorn.api.tree.Tree;
import org.w3c.dom.Node;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;

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
        @SuppressWarnings("unchecked")
        public Node2    swapLinks(String one,String two){
            try{
                Method onesGet=getClass().getMethod("get"+one);
                Method twosGet=getClass().getMethod("get"+two);
                Method onesSet=getClass().getMethod("set"+one,Tree23.Node2.class);
                Method twosSet=getClass().getMethod("set"+two,Tree23.Node2.class);
                Node2 oneNode=(Tree23.Node2)onesGet.invoke(this);
                Node2 twoNode=(Tree23.Node2)twosGet.invoke(this);

                System.out.println(oneNode.getKey()+"<->"+twoNode.getKey());
                onesSet.invoke(this,twoNode);
                System.out.println(onesSet.getName()+"("+twoNode+")");
                twosSet.invoke(this,oneNode);
                System.out.println(twosSet.getName()+"("+oneNode+")");

                return this;

            }catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e){
                e.printStackTrace();
            }
            return null;

        }
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

        @Override public Tree23.Node2 setRight(Tree23.Node2 right) { return super.setRight(right); }

        @Override public Tree23.Node2 setLeft(Tree23.Node2 left) { return super.setLeft(left); }

        @Override public Tree23.Node2 getLeft() { return super.getLeft(); }

        @Override public Tree23.Node2 getRight() { return super.getRight(); }

        public Tree23.Node2    swapKeysAndValues(){
            Key k=getKey2();
            Value v=getValue2();
            setKey2(getKey());
            setValue2(getValue());
            setKey(k);
            setValue(v);
            return this;
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

        @Override
        public Tree23.Node2 setRight(Tree23.Node2 right) { return super.setRight(right); }
        @Override public Tree23.Node2 setLeft(Tree23.Node2 left) { return super.setLeft(left); }
        @Override public Tree23.Node2 getLeft() { return super.getLeft(); }
        @Override public Tree23.Node2 getRight() { return super.getRight(); }
        @Override public Tree23.Node3 setMid(Tree23.Node2 mid) { return super.setMid(mid); }
        @Override public Tree23.Node2 getMid() { return super.getMid(); }

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

    /***
     * delete all nodes child's
     * @param node the node to delete
     */
    protected void dismissAllChildsOfNode(Tree23.Node2 node){
        Node4 node4=(Tree23.Node4)node;
        node4.setLeft(null);
        node4.setMid(null);
        node4.setMidToLeftLink(null);
        node4.setRight(null);
    }
    protected boolean isChildlessNode(Tree23.Node2 node2){
        Tree23.Node4 node4=(Tree23.Node4)node2;
        return node4.getLeft()==null&&
                node4.getMid()==null&&
                node4.getMidToLeftLink()==null&&
                node4.getRight()==null;
    }
    /***
     * deletes the node given in @param child from their parent , given in @param parent
     * @param parent the parent
     * @param child the child
     * @return true in success , false in case that any of the @param parent childen is not the given @param children
     */
    protected static boolean deleteChild(Tree23.Node2 parent,Tree23.Node2 child){
        Tree23.Node3 ref=(Tree23.Node3)parent;
        if(parent==null||child==null)return false;
        if(ref.getLeft()==child){ref.setLeft(null);return true;}
        else if(ref.getMid()==child){ref.setMid(null);return true;}
        else if(ref.getRight()==child){ref.setRight(null);return true;}
        return false;
    }

    /***
     * Add the given child in the parent node(of any type)
     * @Note if we just assign childs like that , the tree will be inconsistent ! remember to call always the makeNodeXConsistent()
     * to perform the needeed transofrmations to the node!
     * @param parent the parent node
     * @param child the child to be added
     * @return true on success , false if the @param parent has the maximum amount of children by its type
     */
    protected static boolean addChild(Tree23.Node2 parent,Tree23.Node2 child){
        if(parent.getLeft()==null){parent.setLeft(child);return true;}
        if(parent.getLeft()==null){parent.setRight(child);return true;}
        if(isNode3(parent))return _addChild3(getNode3Ref(parent),child);
        if(isNode4(parent))return _addChild4(getNode4Ref(parent),child);
        return false;
    }

    /****
     * tool method to add a child in Node3 parent , DONT call it directly , instead call .addChild()
     * @param parent the parent
     * @param child the child
     * @return true on success
     */
    protected static boolean _addChild3(Tree23.Node3 parent,Tree23.Node2 child){
        if(parent.getMid()==null){parent.setMid(child);return true;}
        return false;
    }

    /****
     * tool method to add a child in Node3 parent , DONT call it directly , instead call .addChild()
     * @param parent the parent
     * @param child the child
     * @return true on success
     */
    protected static boolean _addChild4(Tree23.Node4 parent,Tree23.Node2 child){
        if(_addChild3(parent,child))return true;
        if(parent.getMidToLeftLink()==null){parent.setMidToLeftLink(child);return true;}
        return false;
    }

    /***
     * checks if the given Node2 reference is a Node2 node
     * @param node2 the node to check
     * @return true if the given parameter is a node2
     */
    protected static boolean isNode2(Tree23.Node2 node2){
        return node2!=null&&node2.getNodeType()==2;
    }

    /***
     * returns the given parameter if it is a Node2
     * @param node2 the parameter node
     * @return the @param node2 or NULL
     */
    protected static Tree23.Node2 getNode2Ref(Tree23.Node2 node2){
        return isNode2(node2)?(Tree23.Node2)node2:null;
    }

    /***
     * makes all the needed transformations to ensure that the node given is consistent
     * @param node2
     */
    private Tree23.Node2 makeNode2Consistent(Node2 node2) {
        Node2 tmp1,tmp2;
        if((tmp1=node2.getLeft())!=null&&(tmp2=node2.getRight())!=null){
            if(tmp1.compareTo(tmp2)>0)node2.swapLinks("Left","Right");
        }
        return node2;
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

    /***
     * returns the given parameter if it is a Node3
     * @param node2 the parameter node
     * @return the @param node2 or NULL
     */
    protected static Tree23.Node3 getNode3Ref(Tree23.Node2 node2){
        return isNode3(node2)?(Tree23.Node3)node2:null;
    }

    /***
     * makes the given @param node2 a node3 node
     * @param node2 the given node to transform
     * @return the @param node2 casted to Tree23.Node3
     */
    protected static Tree23.Node3 transformIntoNode3(Tree23.Node2 node2){
        if(isNode3(node2))throw new InvalidParameterException("already node3");
        return (Tree23.Node3)node2.setNodeType(3);
    }

    /***
     * Makes all the needed transformations to ensure that the node3 given is consistent
     * it performs
     *          1)Key swaps if needed
     *          2) Link swaps if needed
     * @param node3
     * @return the parameter given
     */
    protected Tree23.Node3 makeNode3Consistent(Tree23.Node3 node3){

        if(node3.getKey().compareTo(node3.getKey2())>0){ node3.swapKeysAndValues(); }

        return makeNode3LinksConsistent(node3);
    }

    protected ArrayList<Tree23.Node2> getChildsOfNode3(Tree23.Node3 node3){
        ArrayList<Tree23.Node2> retval=new ArrayList<>(3);
        Node2 tmp;
        if((tmp=node3.getLeft())!=null)retval.add(tmp);
        if((tmp=node3.getMid())!=null)retval.add(tmp);
        if((tmp=node3.getRight())!=null)retval.add(tmp);
        return retval;
    }
    /****
     * Make the node3 links consistent
     * @param node the node3 to perform consistency transformations
     * @return the @param node
     */

    protected Tree23.Node3 makeNode3LinksConsistent(Tree23.Node3 node){
        Node2 min, max, tmp1, tmp2;
        try{
            ArrayList<Tree23.Node2> childs = getChildsOfNode3(node);
            dismissAllChildsOfNode(node);
            childs.remove(tmp1 = Collections.min(childs));//left is already at right position
            node.setLeft(tmp1);
            childs.remove(tmp1 = Collections.min(childs)); //the second lowest will set at MidToLeft
            node.setMid(tmp1);
            childs.remove(tmp1 = Collections.min(childs)); //the second max will set at Mid!
            node.setRight(tmp1);
        }catch (NoSuchElementException e){}


        return node;
    }
    protected static boolean isNode4(Tree23.Node2 node2){
        return node2!=null&&node2.getNodeType()==4;
    }
    protected static Tree23.Node4 getNode4Ref(Tree23.Node2 node2){
        return isNode4(node2)?(Tree23.Node4)node2:null;
    }

    /***
     * get all children of node 4 given
     * @param node4
     * @return a ArrayList<Tree23.Node2> object
     */
    protected ArrayList<Tree23.Node2> getChildsOfNode4(Tree23.Node4 node4){
        ArrayList<Tree23.Node2> childs=new ArrayList<>(4);
        Tree23.Node2 node2;
        if((node2=node4.getLeft())!=null)childs.add(node2);
        if((node2=node4.getMidToLeftLink())!=null)childs.add(node2);
        if((node2=node4.getMid())!=null)childs.add(node2);
        if((node2=node4.getRight())!=null)childs.add(node2);
        return childs;
    }

    /***
     * convert a node3 into node4
     * @param node3
     * @return
     */
    protected static Tree23.Node4 transformIntoNode4(Tree23.Node3 node3){
        if(isNode4(node3))throw new InvalidParameterException("already node4");
        return (Tree23.Node4)node3.setNodeType(4);
    }

    /***
     * makes all the needed transformations to ensure the node4 given consistency
     * @param node4
     * @return the @param node4
     */
    protected Tree23.Node4 makeNode4Consistent(Tree23.Node4 node4){
        makeNode3Consistent(node4);
        if(node4.getKey3().compareTo(node4.getKey())<0) node4.swapKeysAndValues(0,2);
        if(node4.getKey3().compareTo(node4.getKey2())<0) node4.swapKeysAndValues(1,2);
        makeNode4LinksConsistent(node4);


        return node4;
    }

    /***
     * utill method who ensures that the links of the node4 given will be in right order
     * @param node4
     * @return the parameter given
     */
    private Node4 makeNode4LinksConsistent(Node4 node4) {
        Node2 min, max, tmp1, tmp2;
        try{
            ArrayList<Tree23.Node2> childs = getChildsOfNode4(node4);
            dismissAllChildsOfNode(node4);
            childs.remove(tmp1 = Collections.min(childs));//left is already at right position
            node4.setLeft(tmp1);
            childs.remove(tmp1 = Collections.min(childs));//right is already at right position
            node4.setMidToLeftLink(tmp1);
            childs.remove(tmp1 = Collections.min(childs)); //the second lowest will set at MidToLeft
            node4.setMid(tmp1);
            childs.remove(tmp1 = Collections.min(childs)); //the second max will set at Mid!
            node4.setRight(tmp1);
        }catch (NoSuchElementException e){}


        return node4;

    }

    /****
     * transform a valid node4 into node2
     * @param node4 the node 4 to be transform
     * @return the Node2
     */
    @SuppressWarnings("unchecked")
    protected Tree23.Node2 transformNode4IntoNode2(Tree23.Node4 node4){
        if(!isNode4(node4))throw new InvalidParameterException("node given is not type of Tree23.Node4");
        node4.setMid(null);
        node4.setMidToLeftLink(null);
        return node4.
                setKey3(0).
                setValue3(0).
                setKey2(0).
                setValue2(0).
                setNodeType(2);
    }

    /***
     * splits a valid and consistent Node4 into a Node2 subtree
     *               (x,y,z)
     *               / |  | \
     *             (1)(2)(3)(4)
     *=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/
     *                    (y)
     *                  /    \
     *                (x)    (z)
     *               / \    /  \
     *            (1)  (2)(3)  (4)
     *
     * @param node4 the node4 to be split
     * @return the node2 root of subtree
     */
    @SuppressWarnings("unchecked")
    protected Tree23.Node2 transformNode4IntoNode2Subtree(Tree23.Node4 node4){
        if(!isNode4(node4)) throw new InvalidParameterException("node given is not type of Tree23.Node4");
        node4.setLeft(getInstance2((Key)node4.getKey(),(Value) node4.getValue())
                .setLeft(node4.getLeft())
                .setRight(node4.getMidToLeftLink()));
        node4.setRight(getInstance2((Key)node4.getKey3(),(Value)node4.getValue3())
                .setLeft(node4.getMid())
                .setRight(node4.getRight()));
        node4.swapKeysAndValues(0,1);
        return transformNode4IntoNode2(node4);

    }

    /***
     * the real insert operation
     * @param k the key
     * @param v the value
     * @param trace if true , prints messages about stuff
     */
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

        else if(isChildlessNode(root)&&isNode3(root)){
            if(trace)System.out.println("root is node3");
            root=transformNode4IntoNode2Subtree(
                    makeNode4Consistent(
                            node4ref0=transformIntoNode4(
                                    getNode3Ref(root)).
                                    setKey3(k).
                                    setValue3(v)));
            return;
        }
        else if(!isNode3(getPathElement(0))){
            if(trace)System.out.println("parent is no node3");
            makeNode3Consistent(
                    transformIntoNode3(getPathElement(0))
                            .setKey2(k)
                            .setValue2(v));
            return;
        }

        else if(isNode3(node3ref0=(Node3)getPathElement(0))&&isNode2(node2ref0=getPathElement(1))){
            if(deleteChild(node2ref0,node3ref0)){
                node4ref0=(makeNode4Consistent(transformIntoNode4(node3ref0).setKey3(k).setValue3(v)));
                migrateNode4IntoNode2Parent(node4ref0,node2ref0);
            }
        }
        else if(isNode3(node3ref0=(Node3)getPathElement(0))&& //child
                isNode3(node3ref1=(Node3)getPathElement(1))){ //parent

            if(deleteChild(node3ref1,node3ref0)){               //delete child from parent

                //insert in child the new values
                node4ref0=makeNode4Consistent(transformIntoNode4(node3ref0).setKey3(k).setValue3(v));
                //smash the new node4 into a node2 subtree
                node2ref0=transformNode4IntoNode2Subtree(node4ref0);


                //make parent node4 by inserting the node2 subtree
                node4ref0=(migrateNode2IntoNode3Parent(node2ref0,node3ref1));
                for (int i = 1; i < cachedPath.size(); i++) {
                    if(trace)System.out.print(cachedPath.get(i));
                    if(cachedPath.get(i)==root){
                        if(trace)System.out.println("split on root");
                        root=transformNode4IntoNode2Subtree(getNode4Ref(root));
                        break;

                    }
                    else if(isNode2(cachedPath.get(i+1))){
                        if(trace)System.out.println("migrate on node2");
                        migrateNode4IntoNode2Parent(getNode4Ref(cachedPath.get(i)),cachedPath.get(i+1));
                        break;
                    }
                    else if(deleteChild(cachedPath.get(i+1),cachedPath.get(i))){
                        if(trace)System.out.println("migrate on node3");
                        node2ref0=transformNode4IntoNode2Subtree(getNode4Ref(cachedPath.get(i)));
                        migrateNode2IntoNode3Parent(node2ref0,getNode3Ref(cachedPath.get(i+1)));
                    }
                    else{ throw new InvalidParameterException("corrupt tree due to invalid state");}
                }



            }

        }
        return;

    }

    /***
     * migrate node2 given into node3(also given) by transform the parent into node4
     *                       (x,y)         <- the parent
     *                      /  |  \
     *       the child -> (a) (b) (c)
     *                   /  \
     *                 (1)  (2)
     *
     *              <(a,x,y)>
     *             /  |   |  \
     *           (1) (2) (b)  (c)
     *
     *
     *
     * @param child
     * @param parent
     * @return
     */
    protected Tree23.Node4 migrateNode2IntoNode3Parent(Tree23.Node2 child,Tree23.Node3 parent){
        Tree23.Node4 newParent=transformIntoNode4(parent);
        newParent.setKey3(child.getKey());
        newParent.setValue3(child.getValue());

        //====
        if(addChild(newParent,child.getLeft())&&addChild(newParent,child.getRight()))return makeNode4Consistent(newParent);
        throw new InvalidParameterException("imposssible state , after deletion of 1 child and converting any node of" +
                "Node3 to Node4 must have at least 2 empty links!");

    }

    /****
     * migrate a Node4 into a node2 parent
     *                       (x)         <- the parent
     *                      /  \
     *                    (a)  (b)
     *
     *              <(f,g,i)>       <-the child
     *             /  |   |  \
     *           (1) (2) (3)  (4)
     *
     *
     *
     *=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/
     *
     *                       (x)         <- the parent
     *                      /  \
     *               (5)--(a)  (b)
     *
     *
     *
     *                    (g)
     *                  /    \
     *                (f)    (i)
     *               / \    /  \
     *            (1)  (2)(3)  (4)
     *
     *=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/
     *                       (x)         <- the parent
     *                      /  \
     *               (5)--(a,g)  (b)
     *                   /    \
     *                 (f)    (i)
     *                 / \    /  \
     *              (1)  (2)(3)  (4)
     * @param child
     * @param parent
     * @return
     */
    protected Tree23.Node3 migrateNode4IntoNode2Parent(Node4 child,Node2 parent){
        Node2 node2ref1;
        Node3 node3ref0;
        node2ref1=(transformNode4IntoNode2Subtree(child));
        node3ref0=(makeNode3Consistent((Node3)transformIntoNode3(parent)
                .setKey2(node2ref1.getKey())
                .setValue2(node2ref1.getValue())
                .setMid(node2ref1.getLeft())));
        return addChild(node3ref0,node2ref1.getRight())?node3ref0:null;
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

    public void insert(Key k, Value v) {
        _insert(k,v,false);
    }

    public void insert(Key k, Value v,boolean trace) {
        _insert(k,v,trace);
    }
    public  Tree23<Integer,String> _test_node2parent_node3child(){
        Tree23<Integer,String> s=new Tree23<>();
        s.root=s.getInstance3(100,"A",200,"b");
        s.root.setLeft(s.getInstance3(9,"he",2,"se"));
        s.root.getLeft().setLeft(s.getInstance3(7,"he",1,"se"));
        s.root.getLeft().getLeft().setLeft(s.getInstance3(5,"lalal",9,"PSOFA"));
        return s;
    }


    public void test(){
        System.out.println(getNode2Ref(root).getLeft().getKey());
        System.out.println(getNode2Ref(root).getRight().getKey());
    }
    public void print(){
        bfs(root,0);
    }
    private void bfs(Node2 root,int i){
        if(root==null)return;
        bfs(root.getLeft(),i+1);
        if(root.getNodeType()==3)bfs(((Node3)root).getMid(),i+1);
        if(root.getNodeType()==4)bfs(((Node4)root).getMidToLeftLink(),i+1);
        for (int j = 0; j < i; j++) {
            System.out.print("--");
        }
        System.out.print("("+i+")"+root+"\n");
        bfs(root.getRight(),i+1);
    }
    public Tree23() {
        this.root = null;
        this.cachedParent=null;
        this.cachedPath=new ArrayList<Node2>();
    }
}