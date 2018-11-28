package com.noreasonexception.datanuke.app.fileprotocol;

import java.util.ArrayList;

abstract public class  ListToFileProtocol <TypeOfElements>{
    private String directoryPath;
    ListToFileProtocol(String directoryPath){
        this.directoryPath=directoryPath;
    }
    abstract public boolean saveList(ArrayList<TypeOfElements> elementsToSave,Object [] generic_args);

    public String getDirectoryPath() {
        return directoryPath;
    }
}
