package com.noreasonexception.datanuke.app.fileprotocol;
import jnr.ffi.Pointer;
import jnr.ffi.types.off_t;
import jnr.ffi.types.size_t;
import ru.serce.jnrfuse.ErrorCodes;
import ru.serce.jnrfuse.FuseFillDir;
import ru.serce.jnrfuse.FuseStubFS;
import ru.serce.jnrfuse.struct.FileStat;
import ru.serce.jnrfuse.struct.FuseFileInfo;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class FuseFileProtocol extends FuseStubFS implements ListToFileProtocol<Double>{

    public static  String HELLO_PATH = "/hello";
    public static  ArrayList<Double> HELLO_STR ;
    private static final String DRIVE_NAME = "J:/";

    @Override
    public int getattr(String path, FileStat stat) {
        int res = 0;
        if (Objects.equals(path, "/")) {
            stat.st_mode.set(FileStat.S_IFDIR | 0755);
            stat.st_nlink.set(2);
        } else if (HELLO_PATH.equals(path)) {
            stat.st_mode.set(FileStat.S_IFREG | 0444);
            stat.st_nlink.set(1);
            stat.st_size.set(Arrays.toString(HELLO_STR.toArray()).getBytes().length);
        } else {
            res = -ErrorCodes.ENOENT();
        }
        return res;
    }

    @Override
    public int readdir(String path, Pointer buf, FuseFillDir filter, @off_t long offset, FuseFileInfo fi) {
        if (!"/".equals(path)) {
            return -ErrorCodes.ENOENT();
        }

        filter.apply(buf, ".", null, 0);
        filter.apply(buf, "..", null, 0);
        filter.apply(buf, HELLO_PATH.substring(1), null, 0);
        return 0;
    }

    @Override
    public int open(String path, FuseFileInfo fi) {
        if (!HELLO_PATH.equals(path)) {
            return -ErrorCodes.ENOENT();
        }
        return 0;
    }

    @Override
    public int read(String path, Pointer buf, @size_t long size, @off_t long offset, FuseFileInfo fi) {
        if (!HELLO_PATH.equals(path)) {
            return -ErrorCodes.ENOENT();
        }

        byte[] bytes = Arrays.toString(HELLO_STR.toArray())
                .replace("[","")
                .replace("]","")
                .getBytes();
        int length = bytes.length;
        if (offset < length) {
            if (offset + size > length) {
                size = length - offset;
            }
            buf.put(0, bytes, 0, bytes.length);
        } else {
            size = 0;
        }
        return (int) size;
    }

    public FuseFileProtocol() {
        //initial status until first .saveList() call
        HELLO_STR=new ArrayList<Double>();
        HELLO_STR.add(0d);
        this.mount(Paths.get("J:\\"), false, true);
    }
    public boolean saveList(ArrayList<Double> elementsToSave, Object[] generic_args) {
        this.HELLO_STR=elementsToSave;
        this.HELLO_PATH="/news"+System.currentTimeMillis()/1000+".csv";
        for (int i = 0; i < elementsToSave.size(); i++) {
            if(!generic_args[0].equals(i)){
                this.HELLO_STR.set(i,0d);
            }

        }
        System.out.println("Operation on "+HELLO_PATH+ " completed");
        return true;

    }
}

