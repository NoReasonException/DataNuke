package com.noreasonexception.datanuke.app.threadRunner;

/****
 * This simple interface allows everyone interested in
 * AbstractThreadRunner events , to subscribe a handler.
 *
 */
public interface ThreadRunnerObservable {
    public boolean subscribeListener(ThreadRunnerListener listener);
}
