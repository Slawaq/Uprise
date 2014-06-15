package org.uprise.subscription;

import org.junit.Assert;
import org.junit.Test;
import org.uprise.subsription.Subscription;

public class SubscriptionTest {

    @Test
    public void Should_create_notifiable_object_from_subscriber() {
	StringBuilder total = new StringBuilder();
	Subscription<String> subscription = Subscription.create(s -> total.append(s));

	subscription.accept("apple");

	Assert.assertEquals("apple", total.toString());
    }

}