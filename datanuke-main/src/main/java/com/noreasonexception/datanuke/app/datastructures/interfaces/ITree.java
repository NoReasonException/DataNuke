package com.noreasonexception.datanuke.app.datastructures.interfaces;

public interface ITree<Key,Value> {
    public void     insert  (Key key,Value value);
    public void     delete  (Key key);
    public Value    search  (Key key);
    public Value    pollMin ();
}
