package fr.android.api.event;

import fr.android.api.annotation.Listen.Priority;

public interface EventProcessor<MessageType> {
	Priority getPriority();
	void event(Object sender, MessageType message);
}
