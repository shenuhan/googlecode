package fr.android.api.test;

import android.test.AndroidTestCase;
import fr.android.api.Api;
import fr.android.api.annotation.Listen;
import fr.android.api.annotation.Listen.Priority;
import fr.android.api.annotation.Managed;
import fr.android.api.test.data.EventListener;
import fr.android.api.test.data.EventObject;
import fr.android.api.test.data.impl.EventListenerImpl;

@Managed
public class EventTest extends AndroidTestCase {
	private int i = 0;
	@Listen(senderClass = EventObject.class, event = "event")
	public void classListenerHigh(EventObject sender, Integer Message) {
		i++;
	}

	public void testEvent() throws Throwable {
		Api.start();

		EventListenerImpl listener = (EventListenerImpl) Api.make(EventListener.class);
		EventListenerImpl listener2 = (EventListenerImpl) Api.make(EventListener.class);
		EventObject object = Api.make(EventObject.class);
		EventObject object2 = Api.make(EventObject.class);

		object.event().subscribe(listener);

		assertTrue(listener.nbEvent == 0);
		assertTrue(listener.nbInstanceEvent == 0);
		assertTrue(listener.nbAsyncEvent == 0);
		assertTrue(listener2.nbEvent == 0);
		assertTrue(listener2.nbInstanceEvent == 0);
		assertTrue(listener2.nbAsyncEvent == 0);
		assertTrue(i == 0);
		object2.raiseEvent();
		Thread.sleep(100);
		assertTrue(listener.nbEvent == 1);
		assertTrue(listener.nbInstanceEvent == 0);
		assertTrue(listener.nbAsyncEvent == 1);
		assertTrue(listener2.nbEvent == 1);
		assertTrue(listener2.nbInstanceEvent == 0);
		assertTrue(listener2.nbAsyncEvent == 1);
		assertTrue(listener.priorities.get(0) == Priority.VeryHigh);
		assertTrue(listener.priorities.get(1) == Priority.High);
		assertTrue(listener.priorities.get(2) == Priority.Medium);
		assertTrue(listener.priorities.get(3) == Priority.Low);
		assertTrue(listener.priorities.get(4) == Priority.VeryLow);
		assertTrue(i == 0);
		Api.manage(this);
		object.raiseEvent();
		Thread.sleep(100);
		assertTrue(listener.nbEvent == 2);
		assertTrue(listener.nbInstanceEvent == 1);
		assertTrue(listener.nbAsyncEvent == 2);
		assertTrue(listener2.nbEvent == 2);
		assertTrue(listener2.nbInstanceEvent == 0);
		assertTrue(listener2.nbAsyncEvent == 2);
		assertTrue(i == 1);
		Api.unmanage(this);
		object.event().unsubscribe(listener);
		object.raiseEvent();
		Thread.sleep(100);
		assertTrue(listener.nbEvent == 3);
		assertTrue(listener.nbInstanceEvent == 1);
		assertTrue(listener.nbAsyncEvent == 3);
		assertTrue(listener2.nbEvent == 3);
		assertTrue(listener2.nbInstanceEvent == 0);
		assertTrue(listener2.nbAsyncEvent == 3);
		assertTrue(i == 1);
	}
}

