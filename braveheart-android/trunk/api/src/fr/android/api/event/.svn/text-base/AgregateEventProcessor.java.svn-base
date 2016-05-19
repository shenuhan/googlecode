package fr.android.api.event;

import fr.android.api.annotation.Listen.Priority;

public class AgregateEventProcessor<MessageType> extends Event<MessageType> implements EventProcessor<MessageType> {
	@Override
	public void event(Object sender, MessageType message) {
		super.raise(sender,message);
	}

	public void addSource(Event<MessageType> event) {
		event.subscribe(this);
	}

	public void removeSource(Event<MessageType> event) {
		event.unsubscribe(this);
	}

	@Override
	public Priority getPriority() {
		return Priority.Medium;
	}
}
