package com.noreasonexception.datanuke.app.threadRunner;

import com.noreasonexception.datanuke.app.classloader.AtlasLoader;
import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.datastructures.BST_EDF;
import com.noreasonexception.datanuke.app.datastructures.interfaces.EarliestDeadlineFirst_able;
import com.noreasonexception.datanuke.app.threadRunner.error.*;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;

import javax.json.*;
import javax.json.stream.JsonParsingException;
import java.io.StringReader;
import java.security.InvalidParameterException;
import java.util.*;

import static com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerState.*;

public class AbstractThreadRunner implements Runnable , ThreadRunnerObservable {
    private Date                                        scheduledStart=null;
    private ThreadRunnerState                           currentState = null;
    private AtlasLoader                                 classLoader = null;
    private DataProvider                                configProvider = null;
    private DataProvider                                sourceProvider = null;
    private EarliestDeadlineFirst_able<Long,ClassInfo>  classSourcesDT=null;
    private LinkedList<ThreadRunnerStateListener>       stateListeners = null;
    private final ThreadRunnerStateEventsDispacher      stateEventsDispacher;
    private LinkedList<ThreadRunnerTaskListener>       taskListeners = null;
    private final ThreadRunnerTaskEventsDispacher       taskEventsDispacher;

    private int                                         initializationTime;
    private int                                         startupTarget;
    /*****
     * Simple tool to convert milliseconds to seconds
     * @param mills the milliseconds to convert
     * @return a long value , the seconds
     */
    private static long millsToSec(long mills){return mills/1000;}

    /****
     * Simple tool to convert seconds to milliseconds
     * @param sec the seconds to convert
     * @return a long value , the milliseconds
     */
    private static long secToMills(long sec){return sec/1000;}

    /****
     * Simple tool to get the remaining time from now on.
     * @param then the future timestamp
     * @return the time until @param then
     */
    private static long getRemainingTime(long then){
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
    private static long getDeadline(long p ,long c,long i){
        return ((p+(((int)(c-p)/i))*i)+i);
    }

    /****
     * Get Deadline just defined above , but with the scheduled timestamp instead of current one!
     * @param p the previous timestamp
     * @param i the interval
     * @return the next deadline that the event will occur
     */
    private long getDeadlineFromScheduledStart(long p,long i){
        return getDeadline(p,scheduledStart.getTime(),i);
    }

    /****
     * Get the wait time from the future timestamp
     * @param p the previous timestamp
     * @param c the current timestamp
     * @param i the event's interval
     * @return the wait time (to use in this.wait() method)
     */
    private static long getWaitTime(long p,long c,long i ){
        return getDeadline(p,c,i)-c;
    }

    /****
     * A simple wrapper over getWaitTime that accepts a @see ClassInfo object
     * @param e
     * @return
     */
    private static long getWaitTime(ClassInfo e ){
        return getWaitTime(e.getDate().getTime(),System.currentTimeMillis(),e.getInterval());
    }

    /***
     * Builds a JsonObject using a DataProvider object
     * @param dataProvider the @see DataProvider Object used to construct the Json String and pass it in JsonObject
     * @return a JsonObject
     * @throws NoSuchElementException if DataProvider return no data
     * @throws JsonParsingException if a JSON object cannot be created due to incorrect representation
     * @throws JsonException  if a JSON object cannot be created due to i/o error (IOException would be cause of JsonException)
     */
    private static JsonObject dataProviderToJsonObject(DataProvider dataProvider) throws ConvertException {

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

    /**
     * Loads the fields with proper values loaded by configuration file
     *
     * @throws ConfigurationLoaderException in case of any error
     * @apiNote ConfiguratuonLoaderException causes may be NoSuchElementException (ConfigProvider returns no data) , JsonParsingException in case of invalid JSON syndax or JsonException for generic I/O error)
     */
    private void loadConfiguration() throws ConfigurationLoaderException{
        JsonObject obj;
        try{
            obj=AbstractThreadRunner.dataProviderToJsonObject(configProvider);
        }
        catch(ConvertException e){throw new ConfigurationLoaderException("Convert DataProvider to JsonObject gone bad :( ",e);}
        initializationTime=obj.getInt("initializationTime");            //remember , values in mils
        startupTarget=obj.getInt("startupTarget");                      //remember , values in mils
        scheduledStart=new Date(java.lang.System.currentTimeMillis()+startupTarget);



    }
    private void loadSources() throws SourcesLoaderException{
        JsonObject obj;
        try{
            obj=AbstractThreadRunner.dataProviderToJsonObject(sourceProvider);
        }
        catch(ConvertException e){throw new SourcesLoaderException("Convert DataProvider to JsonObject gone bad :(",e);}
        ClassInfo pair;
        for (String string: obj.keySet()) {
            JsonArray array=obj.getJsonArray(string);
            ClassInfo i;
            this.classSourcesDT.insert(
                    getDeadlineFromScheduledStart(Long.valueOf(array.getString(0)),Integer.valueOf(array.getString(1))),
                    new ClassInfo(
                        new Date(Long.valueOf(array.getString(0))),         //remember! Date works with mils
                        Integer.valueOf(array.getString(1)),
                        string));
            this.taskEventsDispacher.submitClassReadInfoEvent(string);
            long j;
            System.out.println(string+"have deadline in "+
                    (j=getDeadlineFromScheduledStart(Long.valueOf(array.getString(0)),Integer.valueOf(array.getString(1))))+" wait ->"+
                    (j-scheduledStart.getTime()));

        }
    }
    private void prepareLoop() throws LoopPrepareException {
        try{wait(getRemainingTime(scheduledStart.getTime()));}catch (InterruptedException e){throw new LoopPrepareException("Interrupted on startup wait() call",e);}

    }

    /****
     * This is the main loop of AbstractThreadRunner
     * The performed operations is listed below
     * 1) Removes the task with the earliest deadline
     * 2) Calculates the tasks next deadline and re-insert it in the tree
     * 3) Waits untill deadline
     * 4) Load the desired class
     * 5) runs it in new thread
     * LOOP;
     */
    synchronized private void loop() {
        ClassInfo tmp;
        String tmpclassname;
        Class kl;
        Thread taskThread;
        Runnable task;
        while (true){

            tmp=classSourcesDT.pollMin();
            classSourcesDT.insert(tmp.getDate().getTime()+tmp.getInterval(),tmp);
            this.taskEventsDispacher.submitClassWaitUntillDeadlineEvent(tmp.getClassname());
            try{
                wait(getWaitTime(tmp.getDate().getTime(),System.currentTimeMillis(),tmp.getInterval()));
                this.taskEventsDispacher.submitClassLoadingEvent(tmp.getClassname());
                kl=classLoader.loadClass(tmp.getClassname());
                this.taskEventsDispacher.submitClassInstanceCreatedEvent(tmp.getClassname());
                task=(Runnable) kl.newInstance();
                taskThread=new Thread(task);
                this.taskEventsDispacher.submitTaskThreadStartedEvent(tmp.getClassname());
                taskThread.start();
                tmpclassname=kl.getName();
                kl=null;
                classLoader.removeClass(tmpclassname);
            }catch (InterruptedException|ClassNotFoundException e){
                e.printStackTrace();
            }catch (InstantiationException|IllegalAccessException e){

            }
            System.gc();
            try{
                Thread.currentThread().sleep(2000);

            }catch (InterruptedException e){}
            return;

        }
    }
    /***
     * stateEventHappened
     * This method activated in every state change of AbstractThreadRunner . it informs all
     * the subscribed observers about the event
     * Thread-Safe
     */
    private void stateEventHappened() {
        stateEventsDispacher.submitEvent(currentState);

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
        stateEventHappened();
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
        loop();
    }


    public boolean subscribeStateListener(ThreadRunnerStateListener listener) {
        return this.stateListeners.add(listener);
    }
    public boolean subscribeTaskListener(ThreadRunnerTaskListener listener){

        return taskListeners.add(listener);
    }

    public AbstractThreadRunner(AtlasLoader classLoader,DataProvider configProvider,DataProvider sourceProvider) {
        this.stateListeners = new LinkedList<>();
        this.taskListeners=new LinkedList<>();
        this.classLoader=classLoader;
        this.configProvider=configProvider;
        this.sourceProvider=sourceProvider;
        this.classSourcesDT=new BST_EDF();

        this.stateEventsDispacher =new ThreadRunnerStateEventsDispacher(stateListeners);
        this.taskEventsDispacher=new ThreadRunnerTaskEventsDispacher(taskListeners);
        changeStateTo(NONE);
        this.stateEventsDispacher.start();
        this.taskEventsDispacher.start();
    }

    public ThreadRunnerState getCurrentState() {
        return currentState;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.stateEventsDispacher.interrupt();
    }
}
