package org.uprise.observable;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import org.uprise.subscriber.Subscriber;
import org.uprise.subsription.Subscription;

public class Observable<T> {

    private T value;
    private WeakHashMap<Subscriber<T>, Void> subscribers = new WeakHashMap<Subscriber<T>, Void>();

    public Observable(T value) {
	this.value = value;
    }

    public T get() {
	return value;
    }

    public Subscription<T> subscribe(Subscriber<T> subscriber) {
	Subscription<T> subscription = new Subscription<T>(subscriber);
	subscribers.put(subscription, null);
	return subscription;
    }

    public void update(Updater<T> delegate) {
	delegate.update(value);
	notifyAll(value);
    }

    public void update(T value) {
	this.value = value;
	notifyAll(value);
    }

    private void notifyAll(T value) {
	for (Subscriber<T> subscriber : subscribers.keySet())
	    subscriber.notify(value);
    }

    public List<Subscriber<T>> getSubscribers() {
	List<Subscriber<T>> subscribers = new ArrayList<Subscriber<T>>(this.subscribers.size());
	for (Subscriber<T> subscriber : this.subscribers.keySet())
	    subscribers.add(subscriber);
	return subscribers;
    }
}
