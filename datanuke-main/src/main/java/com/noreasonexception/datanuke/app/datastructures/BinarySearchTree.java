package com.noreasonexception.datanuke.app.datastructures;

import java.security.InvalidParameterException;

public class BinarySearchTree<Key extends Comparable<Key>,Value> {

    protected Node root=null;

    protected class Node{
        private Key key;
        private Value value;
        private Node left,right,parent;

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        public Node(Key key, Value value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }
    }
    protected void _insert(Node currnode,Key key,Value value){
        if(root==null){
            root=new Node(key,value,null);return;
        }
        if(key.compareTo(currnode.key)<0){
            if(currnode.getLeft()!=null)
                _insert(currnode.getLeft(),key,value);
            else{
                currnode.setLeft(new Node(key,value,currnode));
            }
        }
        else if (key.compareTo(currnode.key)>0){
            if(currnode.getRight()!=null)
                _insert(currnode.getRight(),key,value);
            else{
                currnode.setRight(new Node(key,value,currnode));
            }

        }else{
            throw new InvalidParameterException("exists!");
        }
    }
    protected Value _search(Node curr,Key key){
        if(key.compareTo(curr.key)<0){
            if(curr.getLeft()!=null){
                return _search(curr.getLeft(),key);
            }
            throw new InvalidParameterException("Not exists!");
        }else if(key.compareTo(curr.key)>0){
            if(curr.getRight()!=null){
                return _search(curr.getRight(),key);
            }
            throw new InvalidParameterException("Not exists");
        }
        else return curr.getValue();
    }
    protected Node _getMin(Node curr){
        if(curr.getLeft()!=null)return _getMin(curr.getLeft());
        return curr;

    }

}

