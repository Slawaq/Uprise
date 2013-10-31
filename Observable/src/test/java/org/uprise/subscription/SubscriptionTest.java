package org.uprise.subscription;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uprise.subscriber.Subscriber;
import org.uprise.subsription.Subscription;

public class SubscriptionTest {

    TestSubscriber testSubscriber;

    @Before
    public void initTestSubscriber() {
	testSubscriber = new TestSubscriber();
    }

    @Test
    public void Should_create_notifiable_object_from_subscriber() {
	Subscription<String> subscription = new Subscription<String>(testSubscriber);

	subscription.notify("value");

	Assert.assertEquals("value", testSubscriber.lastValue);
    }

}

class TestSubscriber implements Subscriber<String> {

    public String lastValue;

    public void notify(String data) {
	lastValue = data;
    }
}