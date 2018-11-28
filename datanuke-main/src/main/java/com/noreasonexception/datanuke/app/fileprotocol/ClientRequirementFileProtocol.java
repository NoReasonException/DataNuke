package com.noreasonexception.datanuke.app.fileprotocol;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientRequirementFileProtocol extends ListToFileProtocol<Double> {
    public ClientRequirementFileProtocol(String directoryPath) {
        super(directoryPath);
    }

    /***
     * docs come soon...
     * generic_args[0] contains the index of the class issued the save command
     * @param elementsToSave
     * @param generic_args
     * @return
     */
    @Override
    public boolean saveList(ArrayList<Double> elementsToSave,Object[] generic_args) {
        return __saveCSVContext(elementsToSave,(Integer)generic_args[0]);
    }

    /***
     * .getNewInternalFileName()
     * This method returns the filename used to save the runtime context
     * @return a string representing the filename
     */
    private String getNewInternalFileName() {
        return "news"+(System.currentTimeMillis() / 1000) + ".csv";
    }


    /***
     * Returns the filename with the proper directory as prefix
     * @param filename  the name of the file
     * @return the full path(use that to open a stream maybe?)
     */
    private String getFullPathOf(String filename) {
        return getDirectoryPath() + filename;
    }


    protected boolean __saveCSVContext(ArrayList<Double> values,int position) {
        String filename;
        try {
            for (int i = 0; i < values.size(); i++) {
                if((i!=position))values.set(i,0d);
            }
            Path filePath = Paths.get(filename = getFullPathOf(getNewInternalFileName()));
            FileChannel byteChannel = (FileChannel) FileChannel.open(filePath, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);

            byteChannel.write(
                    ByteBuffer.wrap(
                            Arrays.toString(
                                    values.toArray()).
                                    replace("[", "").
                                    replace("]", "").getBytes())
            );

            byteChannel.close();
            System.out.println("Operation on " + filename + " completed");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
}
