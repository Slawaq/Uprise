package org.uprise.subscriber;

import org.junit.Assert;
import org.junit.Test;
import org.uprise.observable.Observable;

public class AsyncConsumerTest {

    @Test
    public void Should_call_async_subscriber() {
	StringBuilder total = new StringBuilder();
	Observable<String> observable = new Observable<String>("");
	observable.subscribe(AsyncConsumer.create(value -> total.append(value)));
	
	observable.update("apple");
	wait(100);
	
	Assert.assertEquals("apple", total.toString());
    }

    @Test
    public void Should_call_async_subscriber_in_another_thread() {
	Wrapper<Thread> subscriberThread = new Wrapper<Thread>(Thread.currentThread());
	Observable<String> observable = new Observable<String>("hello");
	observable.subscribe(AsyncConsumer.create(
		value -> subscriberThread.set(Thread.currentThread())
	));
	
	observable.update("apple");
	wait(100);
	
	Assert.assertNotNull(subscriberThread.get());
	Assert.assertNotSame(subscriberThread.get(), Thread.currentThread());
    }

    class Wrapper<T> {
	T data;
	
	Wrapper(T data) {
	    
	}
	
	T get() {
	    return data;
	}
	
	void set(T data){
	    this.data = data;
	}
	
    }

    private void wait(int ms) {
	try {
	    Thread.sleep(ms);
	} catch (Exception e) {

	}
    }
    
}
