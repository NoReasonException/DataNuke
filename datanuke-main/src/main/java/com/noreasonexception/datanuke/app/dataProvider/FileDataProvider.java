package com.noreasonexception.datanuke.app.dataProvider;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.util.Optional.of;

public class FileDataProvider extends DataProvider {
    ByteChannel channel = null;
    ByteBuffer  buffer=null;
    public FileDataProvider(Path file,int buffsize) throws IOException{
        this.buffer=ByteBuffer.allocate(buffsize);
        this.channel= Files.newByteChannel(file);
    }

    @Override
    Optional<Buffer> provide() {
        try {
            this.channel.read(this.buffer);
            this.buffer.rewind();
            return Optional.of(this.buffer);

        }catch (IOException e){
            return Optional.empty();
        }

    }
}
