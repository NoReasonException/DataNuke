package com.noreasonexception.datanuke.app.threadRunner;

import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.threadRunner.error.ConfigurationLoaderException;
import com.noreasonexception.datanuke.app.threadRunner.error.LoopPrepareException;
import com.noreasonexception.datanuke.app.threadRunner.error.NoValidStateChangeException;
import com.noreasonexception.datanuke.app.threadRunner.error.SourcesLoaderException;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;

import javax.json.*;
import javax.json.stream.JsonParsingException;
import java.io.StringReader;
import java.security.InvalidParameterException;
import java.util.*;

import static com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerState.*;

public class AbstractThreadRunner implements Runnable , ThreadRunnerObservable {
    private Date                                    scheduledStart=null;
    private ThreadRunnerState                       currentState = null;
    private ClassLoader                             classLoader = null;
    private DataProvider                            configProvider = null;
    private DataProvider                            sourceProvider = null;
    private ArrayList<ClassInfo>                    classSources = null;
    private LinkedList<ThreadRunnerListener>        listeners = null;
    private final ThreadRunnerDispacher             eventDispacher;
    private int                                     initializationTime;
    private int                                     startupTarget;
    private static long millsToSec(long mills){return mills/1000;}
    private static long secToMills(long sec){return sec/1000;}
    private static long getRemainingTime(long then){
        if(System.currentTimeMillis()>then)throw new InvalidParameterException("desired target belongs to past "+new Date(System.currentTimeMillis())+new Date(then));
        return then-System.currentTimeMillis();
    }
    private static long getWaitTime(long p,long c,long i ){
        System.out.println("WAIT FOR "+String.valueOf(((p+(((int)(c-p)/i))*i)+i)-c));
        return (((p+(((int)(c-p)/i))*i)+i)-c);
    }
    private static JsonObject dataProviderToJsonObject(DataProvider dataProvider)
            throws NoSuchElementException,JsonParsingException,JsonException{
        java.lang.StringBuilder builder = new StringBuilder();
        String str;
        JsonObject object;
        str=DataProvider.Utills.DataProviderToString(dataProvider);
        JsonReader reader= Json.createReader(new StringReader(str));
        object=reader.readObject();
        return object;


    }

    private void loadConfiguration() throws ConfigurationLoaderException{
        JsonObject obj;
        try{
            obj=AbstractThreadRunner.dataProviderToJsonObject(configProvider);
        }
        catch(NoSuchElementException e){throw new ConfigurationLoaderException("DataProvider returned nothing",e);}
        catch(JsonParsingException e){  throw new ConfigurationLoaderException("Configuration file corrupted",e);}
        catch(JsonException e){         throw new ConfigurationLoaderException("Configuration load failed due to unnown IO error",e);}
        initializationTime=obj.getInt("initializationTime");            //remember , values in mils
        startupTarget=obj.getInt("startupTarget");                      //remember , values in mils



    }
    private void loadSources() throws SourcesLoaderException{
        JsonObject obj;
        try{
            obj=AbstractThreadRunner.dataProviderToJsonObject(sourceProvider);
        }
        catch(NoSuchElementException e){throw new SourcesLoaderException("DataProvider returned nothing",e);}
        catch(JsonParsingException e){  throw new SourcesLoaderException("Configuration file corrupted",e);}
        catch(JsonException e){         throw new SourcesLoaderException("Configuration load failed due to unnown IO error",e);}
        ClassInfo pair;
        for (String string: obj.keySet()) {
            JsonArray array=obj.getJsonArray(string);
            ClassInfo i;
            this.classSources.add(i=new ClassInfo(
                    new Date(Long.valueOf(array.getString(0))),         //remember! Date works with mils
                    Integer.valueOf(array.getString(1)),
                    string));
            System.out.println(i.getClassname());
        }
    }
    private void prepareLoop() throws LoopPrepareException {
        Collections.sort(classSources);
        scheduledStart=new Date(java.lang.System.currentTimeMillis()+startupTarget);
        classSources.stream().forEach((e)->{
            e.setDate(new Date());
            System.out.println("class "+e.getClassname()+" is about to start in "+new Date(e.getDate().getTime()-initializationTime) +" to take samples close to "+e.getDate());
        });

    }
    synchronized private void loop() {
        while (!classSources.isEmpty()){
            try{wait(getWaitTime(classSources.get(0).getDate().getTime(),System.currentTimeMillis(),classSources.get(0).getInterval()));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("DONE");

        }
    }
    /***
     * eventHappened
     * This method activated in every state change of AbstractThreadRunner . it informs all
     * the subscribed observers about the event
     * Thread-Safe
     */
    private void eventHappened() {
        eventDispacher.submitEvent(currentState);

    }

    /***
     * .changeStateTo(state)
     * @param state  the new target state
     * This method is responsible for setting a valid value of internal state
     * @Note What this condition means actually!?
     * by conversion : in case of state with id x fails to finish , then the -x state will requested to occur
     * In another case , the x+1 state will occur!
     * @Note that no every state defines this behavior . for example every state with _SUCC lastfix (succeed)
     * has not -x (because if it succeed ,then it cant failure!)
     * @see ThreadRunnerState
     *
     * @throws NoValidStateChangeException in case of invalid request
     */
    private void changeStateTo(ThreadRunnerState state) throws NoValidStateChangeException {
        if(currentState==null) {
            if (state != NONE) {
                throw new NoValidStateChangeException(
                        "One of the Implementations of AbstractThreadRunner requested target(" + state.getId() + ") when currstate==null",
                        currentState, state);
            }
        }
        else if(!(currentState.getId()+1==state.getId()
                || currentState.getId()*-1==state.getId())){throw new NoValidStateChangeException(
                        "One of the Implementations of AbstractThreadRunner requested a invalid state change operation.",
                        currentState,state);
        }
        currentState=state;
        eventHappened();
    }
    /****
     * This is the main entry point for ThreadRunner
     *
     */
    synchronized public void run() {
        changeStateTo(INITIALIZATION);
        changeStateTo(LOAD_CONF);
        try{loadConfiguration();changeStateTo(LOAD_CONF_SUCC);}catch (ConfigurationLoaderException e){ changeStateTo(LOAD_CONF_ERR);return; }
        changeStateTo(LOAD_SRC);
        try{loadSources();changeStateTo(LOAD_SRC_SUCC);}catch (SourcesLoaderException e){ changeStateTo(LOAD_SRC_ERR);e.printStackTrace();return; }
        changeStateTo(PREPARE_LOOP);
        try{prepareLoop();changeStateTo(PREPARE_LOOP_SUCC);}catch (LoopPrepareException e){ changeStateTo(PREPARE_LOOP_ERR);e.printStackTrace();return; }
        try{wait(getRemainingTime(scheduledStart.getTime()));}catch (InterruptedException e){}
        loop();
    }

    public boolean subscribeListener(ThreadRunnerListener listener) {
        return this.listeners.add(listener);
    }

    public AbstractThreadRunner(ClassLoader classLoader,DataProvider configProvider,DataProvider sourceProvider) {
        this.listeners = new LinkedList<>();
        this.classLoader=classLoader;
        this.configProvider=configProvider;
        this.sourceProvider=sourceProvider;
        this.classSources=new ArrayList<>();
        this.eventDispacher=new ThreadRunnerDispacher(this,listeners);
        changeStateTo(NONE);
        this.eventDispacher.start();
    }

    public ThreadRunnerState getCurrentState() {
        return currentState;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.eventDispacher.interrupt();
    }
}
