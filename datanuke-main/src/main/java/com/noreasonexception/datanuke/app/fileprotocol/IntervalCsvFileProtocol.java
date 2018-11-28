package com.noreasonexception.datanuke.app.fileprotocol;

import java.util.ArrayList;

public class IntervalCsvFileProtocol extends ListToFileProtocol<Double> {
    public IntervalCsvFileProtocol(String directoryPath) {
        super(directoryPath);
    }

    @Override
    public boolean saveList(ArrayList<Double> elementsToSave) {
        return false;
    }
}
