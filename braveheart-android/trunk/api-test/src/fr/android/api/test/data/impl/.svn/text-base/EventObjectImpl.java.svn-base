package fr.android.api.test.data.impl;

import fr.android.api.test.data.EventObject;

public class EventObjectImpl implements EventObject {
	private TestEvent event = new TestEvent();
	
	@Override
	public void raiseEvent() {
		event.raise(this, 1);
	}

	@Override
	public TestEvent event() {
		return event;
	}
}
