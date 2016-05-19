package fr.android.api.event;

import java.util.Comparator;

import fr.android.api.util.Logger;

public class EventProcessorComparator<MessageType> implements Comparator<EventProcessor<MessageType>> {
	@Override
	public int compare(EventProcessor<MessageType> one, EventProcessor<MessageType> another) {
		if (one == null || another == null ) {
			throw Logger.t("EventProcessor", "Can only compare EventProcessor , not null Object");
		}
		if (one.getPriority().getValue() < another.getPriority().getValue()) {
			return -1;
		}
		if (one.getPriority().getValue() > another.getPriority().getValue()) {
			return 1;
		}

		if (one.hashCode() < another.hashCode()) {
			return -1;
		}
		if (one.hashCode() > another.hashCode()) {
			return 1;
		}
		return 0;
	}
}
