package com.noreasonexception.datanuke.app.fileprotocol;

import java.util.ArrayList;

public class ClientRequirementFileProtocol extends ListToFileProtocol<Double> {
    public ClientRequirementFileProtocol(String directoryPath) {
        super(directoryPath);
    }

    @Override
    public boolean saveList(ArrayList<Double> elementsToSave,String[] generic_args) {
        return false;
    }
}
