package com.noreasonexception.datanuke.app.threadRunner;

import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.threadRunner.error.ConvertException;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import java.io.StringReader;
import java.security.InvalidParameterException;
import java.util.Date;
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
    /*****
     * Simple tool to convert milliseconds to seconds
     * @param mills the milliseconds to convert
     * @return a long value , the seconds
     */
    public static long millsToSec(long mills){return mills/1000;}
    /*****
     * Simple tool to convert milliseconds to seconds
     * @param mills the milliseconds to convert
     * @return a long value , the seconds
     */
    public static long millsToMins(long mills){return mills/1000;}

    /****
     * Simple tool to convert seconds to milliseconds
     * @param sec the seconds to convert
     * @return a long value , the milliseconds
     */
    public static long secToMills(long sec){return sec*1000;}

    /****
     * Simple tool to get the remaining time from now on.
     * @param then the future timestamp
     * @return the time until @param then
     */
    public static long getRemainingTime(long then){
        if(System.currentTimeMillis()>then)throw new InvalidParameterException("desired target belongs to past "+new Date(System.currentTimeMillis())+new Date(then));
        return then-System.currentTimeMillis();
    }

    /****
     * Get Deadline based on previous event timestamp,the current timestamp and event interval
     *
     * @param p any previous event
     * @param c the current timestamp
     * @param i the interval
     * @return the next time a event will occur
     */
    public static long getDeadline(long p ,long c,long i){
        return (p+(((int)((c-p)/i))*i))+i;
    }
    /*static long getDeadline(long p ,long c,long i){
        return c+(i-(i-(c-(p+(((int)((c-p)/i))*i)))));
    }*/
    /****
     * Get Deadline just defined above , but with the scheduled timestamp instead of current one!
     * @param p the previous timestamp
     * @param i the interval
     * @return the next deadline that the event will occur
     */
    public static long getDeadlineFromScheduledStart(long p,long i,long scheduledStart){
        return getDeadline(p,scheduledStart,i);
    }

    /****
     * Get the wait time from the future timestamp
     * @param p the previous timestamp
     * @param c the current timestamp
     * @param i the event's interval
     * @return the wait time (to use in this.wait() method)
     */
    public static long getWaitTime(long p,long c,long i ){
        return getDeadline(p,c,i)-c;
    }

    /****
     * A simple wrapper over getWaitTime that accepts a @see ClassInfo object
     * @param e
     * @return
     */
    public static long getWaitTime(ClassInfo e ){
        return getWaitTime(e.getDate().getTime(),System.currentTimeMillis(),e.getInterval());
    }
}
