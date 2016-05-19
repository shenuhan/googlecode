package fr.android.api.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import fr.android.api.Api;
import fr.android.api.annotation.EventAnnotation;
import fr.android.api.annotation.Listen.Priority;
import fr.android.api.util.Logger;


@EventAnnotation
public class Event<MessageType> {
	private Set<EventProcessor<MessageType>> subscribed  = new TreeSet<EventProcessor<MessageType>>(new EventProcessorComparator<MessageType>());

	public void subscribe(EventProcessor<MessageType> processor) {
		synchronized (subscribed) {
			subscribed.add(processor);
		}
	}

	public void asyncSubscribe(final EventProcessor<MessageType> processor) {
		synchronized (subscribed) {
			final EventProcessor<MessageType> asyncProcessor = new EventProcessor<MessageType>() {
				@Override
				public Priority getPriority() {
					return processor.getPriority();
				}
				@Override
				@SuppressWarnings("unchecked")
				public void event(Object sender,MessageType message) {
					try {
						Api.singleton(Dispatcher.class).add(sender, message, processor);
					} catch (Exception e) {
						throw Logger.t("Event", "Sender " + sender + " message " + message + " processor " + processor, e);
					}
				}
			};
			subscribed.add(asyncProcessor);
		}
	}

	public void asyncSubscribe(final Dispatcher<MessageType> dispatcher, final EventProcessor<MessageType> processor) {
		synchronized (subscribed) {
			final EventProcessor<MessageType> asyncProcessor = new EventProcessor<MessageType>() {
				@Override
				public Priority getPriority() {
					return processor.getPriority();
				}
				@Override
				public void event(Object sender,MessageType message) {
					try {
						dispatcher.add(sender, message, processor);
					} catch (Exception e) {
						throw Logger.t("Event", "Sender " + sender + " message " + message + " processor " + processor, e);
					}
				}
			};
			subscribed.add(asyncProcessor);
		}
	}

	public void unsubscribe(EventProcessor<MessageType> processor) {
		if (processor == null) {
			return;
		}
		synchronized (subscribed) {
			if (sending) {
				removed = true;
			}
			subscribed.remove(processor);
		}
	}

	boolean sending = false;
	boolean removed = false;
	public void raise(Object sender, MessageType message) {
		synchronized (subscribed) {
			sending = true;
			Collection<EventProcessor<MessageType>> copy = new ArrayList<EventProcessor<MessageType>>(subscribed);
			for (EventProcessor<MessageType> processor : copy) {
				if (removed && !subscribed.contains(processor)) {
					continue;
				}
				processor.event(sender, message);
			}
			removed = false;
			sending = false;
		}
	}

	public void raise(Object sender) {
		raise(sender,null);
	}

	public void raise() {
		raise(null,null);
	}
}
