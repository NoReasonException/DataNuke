package com.noreasonexception.datanuke.app.datastructures.interfaces;

public interface EarliestDeadlineFirst_able<Key,Value> {
    public void     insert  (Key key,Value value);
    public Value    search  (Key key);
    public Value    pollMin ();
}
