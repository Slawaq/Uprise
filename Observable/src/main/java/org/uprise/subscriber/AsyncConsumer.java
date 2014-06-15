package org.uprise.subscriber;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AsyncConsumer<T> implements Consumer<T>, Runnable {

    protected T data;
    protected Consumer<T> consumer;
    protected ExecutorService executorService;
    
    public AsyncConsumer(Consumer<T> consumer) {
	this(consumer, Executors.newSingleThreadExecutor());
    }
    
    public AsyncConsumer(Consumer<T> consumer, ExecutorService executorService) {
	this.consumer = consumer;
	this.executorService = executorService;
    }
        
    public void accept(T data) {
	this.data = data;
	executorService.submit(this);
    }
    
    public void run() {
	consumer.accept(data);
    }

    public static <T> AsyncConsumer<T> create(Consumer<T> consumer){
	return new AsyncConsumer<T>(consumer);
    }
    
}