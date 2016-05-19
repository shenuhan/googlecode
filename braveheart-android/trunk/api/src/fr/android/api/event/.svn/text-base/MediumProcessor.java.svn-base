package fr.android.api.event;

import fr.android.api.annotation.Listen.Priority;

public abstract class MediumProcessor<MessageType> implements EventProcessor<MessageType> {
	@Override
	public Priority getPriority() {
		return Priority.Medium;
	}

	@Override
	public abstract void event(Object sender, MessageType message);
}
