package com.noreasonexception.datanuke.app.fileprotocol;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class IntervalCsvFileProtocol extends ListToFileProtocol<Double> {
    public IntervalCsvFileProtocol(String directoryPath) {
        super(directoryPath);
    }

    @Override
    public boolean saveList(ArrayList<Double> elementsToSave,Object[] generic_args) {
        return __saveCSVContext(elementsToSave);
    }

    /***
     * .getNewInternalFileName()
     * This method returns the filename used to save the runtime context
     * @return a string representing the filename
     */
    private String getNewInternalFileName() {
        return (System.currentTimeMillis() / 1000) + ".csv";
    }


    /***
     * Returns the filename with the proper directory as prefix
     * @param filename  the name of the file
     * @return the full path(use that to open a stream maybe?)
     */
    private String getFullPathOf(String filename) {
        return getDirectoryPath() + filename;
    }


    protected boolean __saveCSVContext(ArrayList<Double> values) {
        String filename;
        try {
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
