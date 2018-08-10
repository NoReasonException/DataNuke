package com.noreasonexception.datanuke.app.dataProvider;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

public class StringDataProvider extends DataProvider {
    ByteBuffer buffer=null;
    public StringDataProvider(String data){
        try {
            byte[]bytes=data.getBytes("utf-8");
            this.buffer=ByteBuffer.allocate(bytes.length);
            this.buffer.put(bytes);
        }catch (Exception e){
            buffer=null;
        }


    }

    @Override
    public Optional<Buffer> provide() {
        try {
            this.buffer.rewind();
            return Optional.of(this.buffer);

        }catch (NullPointerException e){
            return Optional.empty();
        }

    }
}
