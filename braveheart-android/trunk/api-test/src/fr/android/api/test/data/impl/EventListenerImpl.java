package fr.android.api.test.data.impl;

import java.util.ArrayList;
import java.util.List;

import fr.android.api.annotation.Listen;
import fr.android.api.annotation.Listen.Priority;
import fr.android.api.event.EventProcessor;
import fr.android.api.test.data.EventListener;
import fr.android.api.test.data.EventObject;

public class EventListenerImpl implements EventProcessor<Integer>, EventListener {
	public int nbEvent = 0;	
	public int nbAsyncEvent = 0;	
	public int nbInstanceEvent = 0;
	
	public List<Priority> priorities = new ArrayList<Priority>();
	

	@Listen(senderClass = EventObject.class, event = "event", priority = Priority.High)
	public void classListenerHigh(EventObject sender, Integer Message) {
		priorities.add(Priority.High);
	}
	
	@Listen(senderClass = EventObject.class, event = "event", priority = Priority.VeryLow)
	public void classListenerVeryLow(EventObject sender, Integer Message) {
		priorities.add(Priority.VeryLow);
	}

	@Listen(senderClass = EventObject.class, event = "event")
	public void classListenerMedium(EventObject sender, Integer Message) {
		priorities.add(Priority.Medium);
		nbEvent++;
	}

	@Listen(senderClass = EventObject.class, event = "event", priority = Priority.Low)
	public void classListenerLow(EventObject sender, Integer Message) {
		priorities.add(Priority.Low);
	}

	@Listen(senderClass = EventObject.class, event = "event", priority = Priority.VeryHigh)
	public void classListenerVeryHigh(EventObject sender, Integer Message) {
		priorities.add(Priority.VeryHigh);
	}

	@Listen(senderClass = EventObject.class, event = "event", priority = Priority.VeryHigh, async=true)
	public void classAsyncListener(EventObject sender, Integer Message) {
		nbAsyncEvent++;
	}

	@Override
	public void event(Object sender, Integer message) {
		nbInstanceEvent++;
	}

	@Override
	public Priority getPriority() {
		return Priority.Medium;
	}
}
