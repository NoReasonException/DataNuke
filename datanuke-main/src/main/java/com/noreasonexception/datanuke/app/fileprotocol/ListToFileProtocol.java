package com.noreasonexception.datanuke.app.fileprotocol;

import java.util.ArrayList;

public interface   ListToFileProtocol <TypeOfElements>{

    boolean saveList(ArrayList<TypeOfElements> elementsToSave,Object [] generic_args);


}
