package org.uprise.subsription;

import java.util.function.Consumer;

public class Subscription<T> implements Consumer<T> {

    private Consumer<T> subscriber;

    public Subscription(Consumer<T> subscriber) {
	this.subscriber = subscriber;
    }

    public void accept(T data) {
	subscriber.accept(data);
    }
    
    public static <T> Subscription<T> create(Consumer<T> subscriber) {
	return new Subscription<T>(subscriber);
    }

}