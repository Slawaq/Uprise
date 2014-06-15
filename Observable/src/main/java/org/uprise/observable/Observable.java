package org.uprise.observable;

import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.uprise.subsription.Subscription;

public class Observable<T> {

    private T value;
    private WeakHashMap<Consumer<T>, Void> subscribers = new WeakHashMap<Consumer<T>, Void>();

    public Observable(T value) {
	this.value = value;
    }

    public T get() {
	return value;
    }

    public Subscription<T> subscribe(Consumer<T> subscriber) {
	Subscription<T> subscription = Subscription.create(subscriber);
	subscribers.put(subscription, null);
	return subscription;
    }

    public void unsubscribe(Subscription<T> subscription) {
	subscribers.remove(subscription);
    }
    
    public void update(Consumer<T> updater) {
	updater.accept(value);
	notifyAll(value);
    }

    public void update(T value) {
	this.value = value;
	notifyAll(value);
    }

    private void notifyAll(T value) {
	subscribers
		.keySet()
		.forEach(s -> s.accept(value));
    }

    public Stream<Consumer<T>> getSubscribers() {
	return subscribers
		.keySet()
		.stream();
    }

}
