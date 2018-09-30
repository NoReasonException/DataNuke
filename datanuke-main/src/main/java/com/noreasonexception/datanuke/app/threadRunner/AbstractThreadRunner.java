package com.noreasonexception.datanuke.app.threadRunner;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterInconsistentStateException;
import com.noreasonexception.datanuke.app.datastructures.interfaces.ITree;
import static com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerState.*;
import static com.noreasonexception.datanuke.app.threadRunner.Utills.getDeadline;
import static com.noreasonexception.datanuke.app.threadRunner.Utills.getRemainingTime;
import static com.noreasonexception.datanuke.app.threadRunner.Utills.getWaitTime;

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

    private ITree<Long,ClassInfo>                       classSourcesDT=null;    //The Data Structure to implement EDF
    private LinkedList<ThreadRunnerStateListener>       stateListeners = null;  //The observers for state changes inside threadRunner
    private ThreadRunnerStateEventsDispacher            stateEventsDispacher;   //The thread to inform all state - observers for events
    private LinkedList<ThreadRunnerTaskListener>        taskListeners = null;   //The task observers(task changes inside threadRunner)
    private ArrayList<Thread>                           killClassesThreads=null;
    private ThreadRunnerTaskEventsDispacher             taskEventsDispacher;    //The thread to inform all task - observers
    private ThreadRunnerState                           currentState = null;    //Current state of threadRunner subsystem
    private AbstractValueFilter<Double>                 valueFilter=null;       //The value filter subsystem
    private DataProvider                                configProvider = null;  //The Configuration Data Provider
    private DataProvider                                sourceProvider = null;  //The Sources Data Provider
    private boolean                                     terminationFlag=false;  //in order to kill this
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

    public Date getScheduledStart() {
        return scheduledStart;
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
            obj=DataProvider.Utills.dataProviderToJsonObject(configProvider);
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
            obj=DataProvider.Utills.dataProviderToJsonObject(sourceProvider);
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
                            Utills.getDeadlineFromScheduledStart(Long.valueOf(array.getString(0)),Long.valueOf(array.getString(1)),scheduledStart.getTime())-ensureOffset,
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
                    (new Date(j=Utills.getDeadlineFromScheduledStart(Long.valueOf(array.getString(0)),Long.valueOf(array.getString(1)),scheduledStart.getTime())))+" wait ->"+
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
        Thread killClassThread;
        boolean b=false;
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
                if(terminationFlag){
                    terminationFlag=false;//reset the termination flag in case of re-start
                    Thread.currentThread().interrupt();// kill myself.

                }

                this.taskEventsDispacher.submitClassLoadingEvent(tmp.getClassname());
                kl=classLoader.loadClass(tmp.getClassname());
                //kl=classLoader.loadClass("com.noreasonexception.loadable.childs.A7_Statcan_ConsumerPriceIndex_Table1_Line1_5Collumn_CAN");
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
                killClassesThreads.add(killClassThread=new Thread(new Runnable() {
                    private String className=null;
                    @Override
                    public void run() {
                        AbstractThreadRunner.this.classLoader.removeClass("",true);
                    }
                    public Runnable init(String tempClassName){
                        this.className=tempClassName;
                        return this;
                    }
                }.init(tmpclassname)));
                killClassThread.start();

            }catch (InterruptedException e){
                if(Thread.currentThread().isAlive())
                    Thread.currentThread().interrupt();
            }
            catch (ClassNotFoundException|NoSuchMethodException|InvocationTargetException e){
                e.printStackTrace();
            }
            catch (InstantiationException|IllegalAccessException e){
                e.printStackTrace();
            }catch (NullPointerException e){
                e.printStackTrace();
                logMessageExporter.sendMessage("NullPointerException ingnored due to termination proccess");
            }


            System.gc();

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
        this.killClassesThreads=new ArrayList<>();
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
        if(mainThread==null)return;
        logMessageExporter.sendMessage("Stopping the main thread...");
        terminationFlag=true;
        synchronized (mainThread){
            mainThread.notify();

        }

        mainThread=null;
        if(stateEventsDispacher!=null){
            changeStateTo(NONE);
        }
    }
    private void stopKillThreads(){
        for (Thread th:killClassesThreads){
            logMessageExporter.sendMessage("Thread "+th.getName()+": Start interrupt sequence");
            th.interrupt();
        }
        //TODO produces nullPointer on termination...FIX
        logMessageExporter.sendMessage("All KillThreads interrupted");
    }

    /***
     * Termination procedure
     * 1) We terminate (if necessary) the StateEventDispacher Thread
     * 2) We terminate (if necessary) The TaskEventDispacher Thread
     * 3) We stop the main Thread
     * 4 ) We kill all KillThreads (@See KillThreads on top of this file)
     */
    public void dismiss(){
        stopMainThread();
        stopKillThreads();
        ///After version 2.1.1 , and due to many problems , from now on , in OFF signal , the StateEventDispachers will not be interrupted
        //TODO clean all remaining code.
/*
        if(stateEventsDispacher!=null && stateEventsDispacher.isAlive()) {
            stateEventsDispacher.interrupt();
            stateEventsDispacher = null;
        }

        if(taskEventsDispacher!=null && taskEventsDispacher.isAlive()){
            taskEventsDispacher.interrupt();
            taskEventsDispacher=null;
        }
*/

    }
}
