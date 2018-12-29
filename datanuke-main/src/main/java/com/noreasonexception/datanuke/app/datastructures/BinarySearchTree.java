package com.noreasonexception.datanuke.app.datastructures;


import java.security.InvalidParameterException;
import java.security.spec.InvalidParameterSpecException;

/****
 * Simple Binary Search Tree
 * TODO this problem need balanced tree , consider make a 2-3 Tree in future!

 */
public class BinarySearchTree<Key extends Comparable<Key>,Value> {

    protected Node root=null;
    protected Node cachedParent=null;

    /***
     * Internal Node
     */
    protected class Node{
        private Key key;
        private Value value;
        private Node left,right;

        public Node getLeft() { return left; }

        public void setLeft(Node left) { this.left = left; }

        public Node getRight() { return right; }

        public void setRight(Node right) { this.right = right; }

        public Key getKey() { return key; }

        public Value getValue() { return value; }

        public Node(Key key, Value value) { this.key = key;this.value = value;}

    }

    /***
     * interval insert routine , returns the actual Node
     * @param currnode the current node (using for recursive)
     * @param key
     * @param value
     */
    protected void _insert(Node currnode,Key key,Value value){
        if(root==null){
            root=new Node(key,value);return;
        }
        if(key.compareTo(currnode.key)<0){
            if(currnode.getLeft()!=null)
                _insert(currnode.getLeft(),key,value);
            else{
                currnode.setLeft(new Node(key,value));
            }
        }
        else if (key.compareTo(currnode.key)>0){
            if(currnode.getRight()!=null)
                _insert(currnode.getRight(),key,value);
            else{
                currnode.setRight(new Node(key,value));
            }

        }else{
            throw new InvalidParameterException("exists!");
        }
    }

    /****]
     * The interval search , returns the actual node
     * @param curr
     * @param key
     * @return the node defined by the @key
     * @throws InvalidParameterException in case of not found!
     */
    protected Node _search(Node curr,Key key){
        if(key.compareTo(curr.key)<0){
            if(curr.getLeft()!=null){
                cachedParent=curr;
                return _search(curr.getLeft(),key);
            }
            throw new InvalidParameterException("Not exists!");
        }else if(key.compareTo(curr.key)>0){
            if(curr.getRight()!=null){
                cachedParent=curr;
                return _search(curr.getRight(),key);
            }
            throw new InvalidParameterException("Not exists");
        }
        else return curr;
    }

    /***
     * Insert wrapper over internal routine , returns the actual value
     * @param k
     * @param v
     */
    public void insert(Key k , Value v){
        _insert(root,k,v);
    }

    /****
     * search wrapper over internal routine , returns the actual value
     * @param key
     * @return the value defined by key
     */
    public Value search(Key key){
        return _search(root,key).getValue();
    }

    /****
     * returns the parent of the node at last search operation
     * @return Node object , the parent
     */
    public Value getCachedParent(){return cachedParent.getValue();}

    /****
     * The delete method , deletes the desired node defined by the key . maintains the
     * tree consistent
     * @param k the key to delete
     */
    public void delete(Key k){
        if(root==null)throw new InvalidParameterException("There is no values inside");
        Node n ;
        if(isLeaf(n= _search(root,k))){
            if(isRoot(n)){root=null;return;}
            removeMe(cachedParent,n);
        }
        else if(hasOnlyOneChild(n)){
            if(n==root){root=getOnlyChild(n);return;}
            connectMe(cachedParent,n,getOnlyChild(n));
        }
        else{
            Node next=_getMax(_getMin(n.getRight()));
            next.setLeft(n.getLeft());
            next.setRight(n.getRight());
            if(n==root){ root=next;return; }
            if(cachedParent.getLeft()==n){
                cachedParent.setLeft(next);
            }
            else{
                cachedParent.setRight(next);
            }

        }





    }
    protected boolean isRoot(BinarySearchTree.Node n){
        return n==root;
    }
    ///==================utills section
    /***
     * Returns true if the given node is leaf
     * @param n the node to check
     * @return true if the node is leaf (has no childen)
     */
    protected static boolean isLeaf(BinarySearchTree.Node n){
        if(n.getLeft()==null&&n.getRight()==null)return true;
        return false;
    }


    /****
     * Returns true if has the given node has only one child
     * @param n the node to check
     * @return true if the node given has only one child
     */
    protected static boolean hasOnlyOneChild(BinarySearchTree.Node n){
        return (n.getLeft()==null&&n.getRight()!=null)||
                (n.getLeft()!=null&&n.getRight()==null);
    }

    /***
     * Returns the child in node with only one child (left or right)
     * @param n the node to take the child
     * @return the child of node
     */
    protected static BinarySearchTree.Node getOnlyChild(BinarySearchTree.Node n){
        return n.getLeft()!=null?n.getLeft():n.getRight();
    }

    /***
     * connects the grandfather with the parents child in case of deletion
     * @param grandpa
     * @param parent
     * @param child
     * @return true on success
     */
    protected static boolean connectMe(BinarySearchTree.Node grandpa,
                                       BinarySearchTree.Node parent,
                                       BinarySearchTree.Node child){
        if(grandpa.getRight()==parent){
            grandpa.setRight(child);
            return true;
        }
        else if(grandpa.getLeft()==parent){
            grandpa.setLeft(child);
            return true;
        }

        return false;
    }

    /***
     * Removes from parent the reference to given child
     * @param parent the parent
     * @param child the child
     * @return true on success
     */
    protected static boolean removeMe(BinarySearchTree.Node parent,BinarySearchTree.Node child){
        if(parent.getLeft()==child){parent.setLeft(null);return true;}
        if(parent.getRight()==child){parent.setRight(null);return true;}
        return false;
    }

    /****
     * Returns the minimum value exists on tree
     * @param curr the current node
     * @return the min node
     */
    protected Node _getMin(Node curr){
        if(curr.getLeft()!=null)return _getMin(curr.getLeft());
        return curr;
    }

    /****
     * Returns the max value exists on tree
     * @param curr the current node
     * @return the max node
     */
    protected Node _getMax(Node curr){
        if(curr.getRight()!=null)return _getMax(curr.getRight());
        return curr;
    }

    /****
     * A utillity to see the tree based on BFS
     * @param curr the current node
     */
    protected void BFS(Node curr){
        if(curr.getLeft()!=null){BFS(curr.getLeft());}
        System.out.println(curr.key);
        if(curr.getRight()!=null){BFS(curr.getRight());}

    }
    public void BFS(){BFS(root);}

}

