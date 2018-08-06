package com.noreasonexception.datanuke.app.gui.rightBorder;

import javafx.scene.Node;

public class DataNukeGuiOption {
    private String name;
    private Node node;

    public DataNukeGuiOption(String name, Node node) {
        this.name = name;
        this.node=node;
    }

    public String getName() {
        return name;
    }
    public Node getNode(){
        return this.node;
    }
}
