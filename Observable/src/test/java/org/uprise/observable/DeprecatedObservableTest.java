package org.uprise.observable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uprise.subscriber.RunnableSubscriber;
import org.uprise.subscriber.Subscriber;
import org.uprise.subsription.Subscription;

public class DeprecatedObservableTest {
    TestData data;
    final static String DATA_NAME = "doesnt care";
    final static String UPDATED_DATA_NAME = "something else";
    Thread closureThreadVariable;
    String closureVariable;

    @Before
    public void createTestData() {
	data = new TestData(DATA_NAME);
	closureThreadVariable = null;
	closureVariable = null;
    }

    @Test
    public void testOnClearingSubscribers() {
	Observable<TestData> observable = new Observable<TestData>(data);
	observable.subscribe(createSubscriber());
	callGabargeCollector();
	Assert.assertEquals(observable.getSubscribers().size(), 0);
    }

    @Test
    public void testOnSavingSubscribers() {
	Observable<TestData> observable = new Observable<TestData>(data);

	@SuppressWarnings("unused")
	Subscription<TestData> subscription = observable.subscribe(createSubscriber());

	callGabargeCollector();
	Assert.assertEquals(observable.getSubscribers().size(), 1);
    }

    // @Test
    // public void testOnRunnableSubscriber() {
    // Observable<TestData> observable = new Observable<TestData>(data);
    // observable.subscribe(createRunnableSubscriber());
    // updateName(observable);
    // wait(100);
    // Assert.assertEquals(closureVariable, UPDATED_DATA_NAME);
    // }

    @Test
    public void testOnCallingRunnableSubscriberInAnotherThread() {
	Observable<TestData> observable = new Observable<TestData>(data);
	observable.subscribe(createRunnableSubscriberWhichSaveInnerThreadInClosure());
	updateName(observable);
	wait(100);
	Assert.assertNotSame(closureThreadVariable, Thread.currentThread());
    }

    private Subscriber<TestData> createRunnableSubscriberWhichSaveInnerThreadInClosure() {
	return new RunnableSubscriber<TestData>() {
	    public void run() {
		closureThreadVariable = Thread.currentThread();
	    }
	};
    }

    private Subscriber<TestData> createRunnableSubscriber() {
	return new RunnableSubscriber<TestData>() {
	    public void run() {
		closureVariable = this.data.name;
	    }
	};
    }

    private void wait(int ms) {
	try {
	    Thread.sleep(ms);
	} catch (Exception e) {

	}
    }

    private void callGabargeCollector() {
	try {
	    Thread.sleep(100);
	} catch (Exception e) {
	}
	System.gc();
    }

    private Subscriber<TestData> createSubscriber() {
	return new Subscriber<TestData>() {
	    public void notify(TestData data) {
		closureVariable = data.name;

	    }
	};
    }

    private void updateName(Observable<TestData> observable) {
	observable.update(new Updater<TestData>() {
	    public void update(TestData data) {
		data.setName(UPDATED_DATA_NAME);
	    }
	});
    }
}

class TestData {

    String name;

    TestData(String name) {
	this.name = name;
    }

    public void setName(String name) {
	this.name = name;
    }
}
