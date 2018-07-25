package com.noreasonexception.datanuke.app.threadRunner;

/****
 * This simple interface allows everyone interested in
 * AbstractThreadRunner events , to subscribe a handler.
 *
 */
public interface ThreadRunnerStateObservable {
    public boolean subscribeStateListener(ThreadRunnerStateListener listener);
}
