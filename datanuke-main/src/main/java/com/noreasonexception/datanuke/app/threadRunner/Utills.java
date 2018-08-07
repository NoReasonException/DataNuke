package com.noreasonexception.datanuke.app.threadRunner;

import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.threadRunner.error.ConvertException;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import java.io.StringReader;
import java.util.NoSuchElementException;

public class Utills {
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
}
