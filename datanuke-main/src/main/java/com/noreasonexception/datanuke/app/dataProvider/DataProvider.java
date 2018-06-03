package com.noreasonexception.datanuke.app.dataProvider;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.NoSuchElementException;
import java.util.Optional;

abstract public class DataProvider {
    public static class Utills{
        public static java.lang.String DataProviderToString(DataProvider dataProvider) throws NoSuchElementException{
            java.lang.StringBuilder stringBuilder = new StringBuilder();
            ByteBuffer buff;
            buff=(ByteBuffer) dataProvider.provide().get();
            for (int i = 0; i < buff.limit();i++) {
                stringBuilder.append((char)buff.get());
            }
            return stringBuilder.toString();

        }
    }
    abstract public Optional<Buffer> provide();
}
