package org.uprise.server;

import java.util.concurrent.TimeUnit;

import org.uprise.observable.Observable;
import org.uprise.subscriber.RunnableSubscriber;

public class Komenci {

    public static void main(String[] args) throws InterruptedException {
	Observable<String> resource = new Observable<String>("hello,");
	resource.subscribe(new ResourceListener());
	new ResourceUpdater(resource);
	TimeUnit.SECONDS.sleep(1);
    }

}

class ResourceUpdater implements Runnable {

    Observable<String> resource;

    public ResourceUpdater(Observable<String> resource) {
	this.resource = resource;
	Thread thread = new Thread(this);
	thread.setDaemon(true);
	thread.start();
    }

    public void run() {
	try {
	    while (true) {
		resource.update(resource.get() + " lalka!");
		TimeUnit.MILLISECONDS.sleep(80);
	    }
	} catch (InterruptedException exp) {
	    System.out.println("EXIT");
	}

    }

}

class ResourceListener extends RunnableSubscriber<String> {

    public void run() {
	System.out.println(data);
    }
}
