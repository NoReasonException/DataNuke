package com.noreasonexception.datanuke.app.dataProvider;

import com.noreasonexception.datanuke.app.threadRunner.error.ConvertException;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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

        public static void writeDataProviderToFile(DataProvider provider, Path file)throws IOException{
            SeekableByteChannel channel= Files.newByteChannel(file,StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING);
            channel.write((ByteBuffer) provider.provide().get());
        }
        /***
         * Builds a JsonObject using a DataProvider object
         * @param dataProvider the @see DataProvider Object used to construct the Json String and pass it in JsonObject
         * @return a JsonObject
         * @throws NoSuchElementException if DataProvider return no data
         * @throws JsonParsingException if a JSON object cannot be created due to incorrect representation
         * @throws JsonException  if a JSON object cannot be created due to i/o error (IOException would be cause of JsonException)
         */
        public static JsonObject dataProviderToJsonObject(DataProvider dataProvider) throws ConvertException {

            java.lang.StringBuilder builder = new StringBuilder();
            String str;
            JsonObject object;
            try{
                str=DataProvider.Utills.DataProviderToString(dataProvider);
                JsonReader reader= Json.createReader(new StringReader(str));
                object=reader.readObject();
            }catch(NoSuchElementException e){throw new ConvertException("DataProvider returned nothing",e);}
            catch(JsonParsingException e){  throw new ConvertException("Configuration file corrupted",e);}
            catch(JsonException e){         throw new ConvertException("Configuration load failed due to I/O error",e);}

            return object;
        }
        public static DataProvider jsonObjectToDataProvider(JsonObject object) throws ConvertException{
            return new StringDataProvider(object.toString());
        }
    }

    /***
     * Provides the Buffer with the content
     * @return a BufferObject
     */
    abstract public Optional<Buffer> provide();
}
