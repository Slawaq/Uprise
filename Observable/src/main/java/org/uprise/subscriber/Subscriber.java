package org.uprise.subscriber;

public interface Subscriber<T> {
    public void notify(T data);
}