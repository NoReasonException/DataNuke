package com.noreasonexception.datanuke.app.dataProvider;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Optional.of;

public class FileDataProvider extends DataProvider {
    ByteChannel channel = null;
    ByteBuffer  buffer=null;
    //refactor , remove buffsize TODO
    public FileDataProvider(Path file,long buffsize){
        this.buffer=ByteBuffer.allocate((int)buffsize);//TODO : check for possible overflow
        try{
            this.channel= Files.newByteChannel(file);
        }catch (IOException e){
            this.channel=null;
        }

    }

    @Override
    public Optional<Buffer> provide() {
        try {
            this.channel.read(this.buffer);
            this.buffer.rewind();
            return Optional.of(this.buffer);

        }catch (IOException|NullPointerException e){
            return Optional.empty();
        }

    }
}
