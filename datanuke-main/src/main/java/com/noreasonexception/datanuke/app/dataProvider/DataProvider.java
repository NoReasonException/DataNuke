package com.noreasonexception.datanuke.app.dataProvider;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.NoSuchElementException;
import java.util.Optional;

/***
 * This class is used to provide a layer of abstraction between Context (As Buffer ) and Destination of the data
 * Provides a simple .provide() method who returns as Optional an buffer . in case of any error , the call of .get in
 * Optional Object will return NoSuchElementException.
 */
abstract public class DataProvider {
    /****
     * A bunch of useful tools
     */
    public static class Utills
    {
        /****
         * Converts any DataProvider object to plain java.lang.String
         * @param dataProvider the DataProvider Object
         * @return the content of DataProvider @param dataProvider object as string
         * @throws NoSuchElementException in case of any exception (IOException's mostly)
         */
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

    /***
     * Provides the Buffer with the content
     * @return a BufferObject
     */
    abstract public Optional<Buffer> provide();
}
