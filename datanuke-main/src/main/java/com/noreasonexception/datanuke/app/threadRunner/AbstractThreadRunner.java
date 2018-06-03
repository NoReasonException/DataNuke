package com.noreasonexception.datanuke.app.threadRunner;

import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.threadRunner.error.ConfigurationLoaderException;
import com.noreasonexception.datanuke.app.threadRunner.error.NoValidStateChangeException;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerState.*;

public class AbstractThreadRunner implements Runnable , ThreadRunnerObservable {
    private ThreadRunnerState currentState = null;
    private ClassLoader classLoader = null;
    private DataProvider configProvider = null;
    private DataProvider sourceProvider = null;
    private HashMap<Date, String> classSources = null;
    private LinkedList<ThreadRunnerListener> listeners = null;
    private final ThreadRunnerDispacher             eventDispacher;

    private void loadConfiguration() throws ConfigurationLoaderException{
        //throw new ConfigurationLoaderException("test",new JsonException("Test"));
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
    public void run() {
        changeStateTo(INITIALIZATION);
        changeStateTo(LOAD_CONF);
        try{
            loadConfiguration();
            changeStateTo(LOAD_CONF_SUCC);
        }catch (ConfigurationLoaderException e){

            changeStateTo(LOAD_CONF_ERR);
            return;
        }
        //changeStateTo(LOAD_CONF_SUCC);

    }

    public boolean subscribeListener(ThreadRunnerListener listener) {
        return this.listeners.add(listener);
    }

    public AbstractThreadRunner(ClassLoader classLoader,DataProvider configProvider,DataProvider sourceProvider) {
        this.listeners = new LinkedList<>();
        this.classLoader=classLoader;
        this.configProvider=configProvider;
        this.sourceProvider=sourceProvider;
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
