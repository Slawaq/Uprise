package org.uprise.observable;

import org.junit.Assert;
import org.junit.Test;
import org.uprise.subscriber.Subscriber;

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

	observable.update(new Updater<StringBuilder>() {

	    public void update(StringBuilder data) {
		data.append("-vasya");
	    }
	});

	Assert.assertEquals("magic-vasya", observable.get().toString());
    }

    @Test
    public void Should_return_FUCK() {
	Observable<String> observable = new Observable<String>("lal");

	Assert.assertEquals(observable.fuck(), "fuck");
    }

    @Test
    public void Should_call_subscribers_when_value_updated() {
	Observable<String> observable = new Observable<String>("magic");
	final StringBuilder stringWrapper = new StringBuilder();
	observable.subscribe(new Subscriber<String>() {
	    public void notify(String data) {
		stringWrapper.append(data);
	    }
	});

	observable.update("vasya");

	Assert.assertEquals("vasya", stringWrapper.toString());
    }

}
