package com.noreasonexception.datanuke.app.fileprotocol;

abstract public class StaticDirectoryFile {
    private String directoryPath;

    StaticDirectoryFile(String directoryPath){
        this.directoryPath=directoryPath;
    }
    public String getDirectoryPath() {
        return directoryPath;
    }
}
