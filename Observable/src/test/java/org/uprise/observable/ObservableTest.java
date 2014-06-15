package org.uprise.observable;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.uprise.subsription.Subscription;

public class ObservableTest {

    @Test
    public void Should_wrapped_value_in_observable() {
	String value = "vasya";
	Observable<String> observable = new Observable<String>(value);

	Assert.assertEquals(value, observable.get());
    }

    @Test
    public void Should_change_value_when_call_update() {
	Observable<String> observable = new Observable<String>("magic");

	observable.update("vasya");

	Assert.assertEquals("vasya", observable.get());
    }

    @Test
    public void Should_update_through_updaterDelegate() {
	Observable<StringBuilder> observable = new Observable<StringBuilder>(new StringBuilder("magic"));

	observable.update(data -> data.append("-vasya"));

	Assert.assertEquals("magic-vasya", observable.get().toString());
    }

    @Test
    public void Should_call_subscribers_when_value_updated() {
	Observable<String> observable = new Observable<String>("magic");
	@SuppressWarnings("unchecked")
	Consumer<String> spy = Mockito.mock(Consumer.class);
	observable.subscribe(spy);

	observable.update("vasya");

	Mockito.verify(spy).accept("vasya");
    }
    @Test
    public void Should_clear_weak_subscribers() {
	Observable<String> observable = new Observable<String>("lal");
	observable.subscribe(d -> { });

	callGabargeCollector();

	Assert.assertEquals(observable.getSubscribers().count(), 0);
    }

    @Test
    public void Should_save_subscriber_when_subscription_exist() {
	Observable<String> observable = new Observable<String>("lal");
	@SuppressWarnings("unused")
	Subscription<String> subscription = observable.subscribe(d -> { });

	callGabargeCollector();
	
	Assert.assertEquals(observable.getSubscribers().count(), 1);
    }

    private void callGabargeCollector() {
	try {
	    Thread.sleep(100);
	} catch (Exception e) {
	}
	System.gc();
    }
}
