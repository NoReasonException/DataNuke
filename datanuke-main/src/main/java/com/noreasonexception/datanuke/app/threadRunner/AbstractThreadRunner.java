package com.noreasonexception.datanuke.app.threadRunner;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterInconsistentStateException;
import com.noreasonexception.datanuke.app.datastructures.interfaces.ITree;
import static com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerState.*;

import com.noreasonexception.datanuke.app.gui.menu.dynamicwindows.intefaces.MessageExporter;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;
import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.classloader.AtlasLoader;
import com.noreasonexception.datanuke.app.datastructures.BST_EDF;
import com.noreasonexception.datanuke.app.threadRunner.error.*;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import javax.json.stream.JsonParsingException;

import java.io.StringReader;
import javax.json.*;
import java.util.*;

/****
 * The threadRunner
 * What is this "threadRunner" stuff?
 * The threadRunner is a subsystem , which is responsible for
 * 1) Starts the parsers , to fetch a new value
 * 2) Informs the observers (The GUI probably) for events
 * ====================================================== Task Events vs State Events============================================
 * What is a Task Event?
 * The whole point in threadRunner's operation is ..
 *
 * 0)fetch the class , who their deadline is the smallest
 * 1)Wait untill it is time to load the class (untill deadline - initialization time)
 * 2)Load the class
 * 3)Calculate when this class will be re-started
 * 4)GOTO 0
 *
 * So , a Task event is an event about a loadable class. if the class x is loaded , then onClassLoading() will be called on every task-observer
 * so remember ! Task = Loadable class
 *
 * What is State event?
 *
 * The state events is about the progress of threadRunner itself. for example if initialization just started , then the INITIALIZATION
 * state will be emitted to every state-observer
 *
 */
// TODO ThreadRunnerTaskListener consider making abstract parent class
public class AbstractThreadRunner implements    Runnable ,
                                                ThreadRunnerStateObservable,
                                                ThreadRunnerTaskObservable{

    private ITree<Long,ClassInfo> classSourcesDT=null;    //The Data Structure to implement EDF
    private LinkedList<ThreadRunnerStateListener>       stateListeners = null;  //The observers for state changes inside threadRunner
    private ThreadRunnerStateEventsDispacher            stateEventsDispacher;   //The thread to inform all state - observers for events
    private LinkedList<ThreadRunnerTaskListener>        taskListeners = null;   //The task observers(task changes inside threadRunner)
    private ThreadRunnerTaskEventsDispacher             taskEventsDispacher;    //The thread to inform all task - observers
    private ThreadRunnerState                           currentState = null;    //Current state of threadRunner subsystem
    private AbstractValueFilter<Double>                 valueFilter=null;       //The value filter subsystem
    private DataProvider                                configProvider = null;  //The Configuration Data Provider
    private DataProvider                                sourceProvider = null;  //The Sources Data Provider
    private Random                                      randomGenerator=null;
    private Thread                                      mainThread=null;
    private AtlasLoader                                 classLoader = null;     //The ClassLoader of threadRunner , responsible for removing everything after finish
    private Date                                        scheduledStart=null;    //Scheduled start of ThreadRunners main loop
    private int                                         initializationTime;     //initializationTime
    private int                                         startupTarget;          //--

    private MessageExporter                             logMessageExporter = null;
    private MessageExporter                             errorMessageExporter=null;

    int getEnsureOffset(){
        return (randomGenerator.nextInt()%5)+5;
    }
    /*****
     * Simple tool to convert milliseconds to seconds
     * @param mills the milliseconds to convert
     * @return a long value , the seconds
     */
    static long millsToSec(long mills){return mills/1000;}
    /*****
     * Simple tool to convert milliseconds to seconds
     * @param mills the milliseconds to convert
     * @return a long value , the seconds
     */
    static long millsToMins(long mills){return mills/1000;}

    /****
     * Simple tool to convert seconds to milliseconds
     * @param sec the seconds to convert
     * @return a long value , the milliseconds
     */
    static long secToMills(long sec){return sec*1000;}

    /****
     * Simple tool to get the remaining time from now on.
     * @param then the future timestamp
     * @return the time until @param then
     */
    static long getRemainingTime(long then){
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
    static long getDeadline(long p ,long c,long i){
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
    static long getWaitTime(long p,long c,long i ){
        return getDeadline(p,c,i)-c;
    }

    /****
     * A simple wrapper over getWaitTime that accepts a @see ClassInfo object
     * @param e
     * @return
     */
    static long getWaitTime(ClassInfo e ){
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

    /****
     * This method loads the sources file , witch the classes information exists about the location , name , interval e.t.c
     * @throws SourcesLoaderException
     */
    private void loadSources() throws SourcesLoaderException {
        JsonObject obj;
        try{
            obj=AbstractThreadRunner.dataProviderToJsonObject(sourceProvider);
        }
        catch(ConvertException e){throw new SourcesLoaderException("Convert DataProvider to JsonObject gone bad :(",e);}
        ClassInfo info;
        for (String klass: obj.keySet()) {
            JsonArray array=obj.getJsonArray(klass);
            //ensure offset will ensure that the same specific deadline will not exists 2 times , in order to be
            //succeed the insert operation
            //

            int ensureOffset=0;
            while(true){
                try{

                    this.classSourcesDT.insert(
                            getDeadlineFromScheduledStart(Long.valueOf(array.getString(0)),Long.valueOf(array.getString(1)))-ensureOffset,
                            info=new ClassInfo(array.getString(2),
                                    new Date(Long.valueOf(array.getString(0))),         //remember! Date works with mils
                                    Long.valueOf(array.getString(1)),
                                    klass));
                    ensureOffset=0;
                    break;
                }catch (InvalidParameterException e){
                    ensureOffset=getEnsureOffset();
                }
            }

            this.taskEventsDispacher.submitClassReadInfoEvent(klass,info);
            try{
                this.valueFilter.submitClass(klass);

            }catch (CsvValueFilterInconsistentStateException e){
                throw new RuntimeException("valueFilter in invalid state");
            }
            long j;
            logMessageExporter.sendMessage(klass+" have deadline in "+
                    (new Date(j=getDeadlineFromScheduledStart(Long.valueOf(array.getString(0)),Long.valueOf(array.getString(1)))))+" wait ->"+
                    getWaitTime(
                            Long.valueOf(array.getString(0)),
                            System.currentTimeMillis(),
                            Long.valueOf(array.getString(1)))/1000/60/60/24);

        }
    }

    /****
     * .prepareLoop() method has the responsibility to set the scene for .loop() method
     * Also must wait until scheduled start
     * @throws LoopPrepareException in case of every error , InterruptedException mostly
     */
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

            tmp=classSourcesDT.pollMin();//TODO fix , maybe need update the tmp.date!?
            classSourcesDT.insert(tmp.getDate().getTime()+tmp.getInterval(),tmp);
            this.taskEventsDispacher.submitClassWaitUntillDeadlineEvent(
                    tmp.getClassname(),
                    getDeadline(
                            tmp.getDate().getTime(),
                            System.currentTimeMillis(),
                            tmp.getInterval()));
            try{
                logMessageExporter.sendMessage("will wait "+getWaitTime(tmp)/1000/60+" min(s) "+tmp.getClassname()+" )");

                wait(getWaitTime(tmp));
                this.taskEventsDispacher.submitClassLoadingEvent(tmp.getClassname());
                kl=classLoader.loadClass(tmp.getClassname());
                this.taskEventsDispacher.submitClassInstanceCreatedEvent(tmp.getClassname());
                task=(Runnable) kl.getDeclaredConstructor(ThreadRunnerTaskEventsDispacher.class,AbstractValueFilter.class).newInstance(this.taskEventsDispacher,this.valueFilter);
                taskThread=new Thread(task);
                this.taskEventsDispacher.submitTaskThreadStartedEvent(tmp.getClassname());
                taskThread.start();
                tmpclassname=kl.getName();
                kl=null;
                task=null;
                taskThread=null;
                tmp=null;
                new Thread(()->{
                    AbstractThreadRunner.this.classLoader.removeClass(tmpclassname,true);
                }).start();

            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            catch (ClassNotFoundException|NoSuchMethodException|InvocationTargetException e){
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
     * @implNote we can reset the state of AbstractThreadRunner to NONE exceptionally , the previous state dont matter
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
                || currentState.getId()*-1==state.getId()) && state!=NONE){throw new NoValidStateChangeException(
                        "One of the Implementations of AbstractThreadRunner requested a invalid state change operation.",
                        currentState,state);
        }
        currentState=state;
        stateEventHappened();
    }
    /****
     * This is the main entry point for threadRunner
     *
     */
    synchronized public void run() {

        changeStateTo(NONE);
        changeStateTo(INITIALIZATION);
        changeStateTo(LOAD_CONF);
        startMessageDispachers();
        try{loadConfiguration();changeStateTo(LOAD_CONF_SUCC);}catch (ConfigurationLoaderException e){ changeStateTo(LOAD_CONF_ERR);e.printStackTrace();return; }
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
        return this.taskListeners.add(listener);
    }

    public AbstractThreadRunner(AtlasLoader classLoader,
                                DataProvider configProvider,
                                DataProvider sourceProvider,
                                AbstractValueFilter<Double> valueFilter,
                                Random random) {
        try{
            getClass().getClassLoader().loadClass("java.util.regex.Pattern");
            getClass().getClassLoader().loadClass("java.util.regex.Matcher");
            getClass().getClassLoader().loadClass("com.snowtide.pdf.Document");


        }catch (Exception e){}
        this.stateListeners = new LinkedList<>();
        this.taskListeners=new LinkedList<>();
        this.stateEventsDispacher =new ThreadRunnerStateEventsDispacher(stateListeners);
        this.taskEventsDispacher=new ThreadRunnerTaskEventsDispacher(taskListeners);

        this.classLoader=classLoader;
        this.configProvider=configProvider;
        this.sourceProvider=sourceProvider;
        this.valueFilter=valueFilter;
        this.randomGenerator=random;

        reset();
    }


    private void reset(){
        this.classSourcesDT=new BST_EDF();
    }
    public AbstractThreadRunner startMessageDispachers(){
        if(this.stateEventsDispacher!=null&&!this.stateEventsDispacher.isAlive()) {
            this.stateEventsDispacher.start();
            this.taskEventsDispacher.start();
        }
        return this;
    }
    public ThreadRunnerState getCurrentState() {
        return currentState;
    }

    /***
     * //TODO because of deprecation in after 9+ versions , find a way avoiding the .finalize()
     * Terminate the StateEventDispacher and TaskEventDispacher
     * @throws Throwable in case of any error..
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.stateEventsDispacher.interrupt();
        this.taskEventsDispacher.interrupt();
    }
    public void startMainThread(MessageExporter logExporter , MessageExporter errorExporter){
        this.logMessageExporter=logExporter;
        this.errorMessageExporter=errorExporter;
        logMessageExporter.sendMessage("Starting the main thread...");

        this.mainThread=new Thread(this);
        reset();
        mainThread.start();
    }
    public void stopMainThread(){
        logMessageExporter.sendMessage("Stopping the main thread...");
        if(mainThread==null)return;
        mainThread.interrupt();
        mainThread=null;
        changeStateTo(NONE);
    }
}
