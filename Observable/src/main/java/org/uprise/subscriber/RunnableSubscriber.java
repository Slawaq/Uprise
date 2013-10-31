package org.uprise.subscriber;

public abstract class RunnableSubscriber<T> implements Subscriber<T>, Runnable {

    protected T data;

    public void notify(T data) {
	this.data = data;
	new Thread(this).start();
    }

}