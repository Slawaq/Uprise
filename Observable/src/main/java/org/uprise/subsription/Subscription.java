package org.uprise.subsription;

import org.uprise.subscriber.Subscriber;

public class Subscription<T> implements Subscriber<T> {

    private Subscriber<T> wrapped;

    public Subscription(Subscriber<T> subscriber) {
	wrapped = subscriber;
    }

    public void notify(T data) {
	wrapped.notify(data);
    }

}