package fr.android.api.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import fr.android.api.event.Dispatcher;
import fr.android.api.event.EventProcessor;

public class ManualDispatcher<MessageType> implements Dispatcher<MessageType> {
	protected class Callback {
		public final EventProcessor<MessageType> processor;
		public final Object sender;
		public final MessageType message;

		public Callback (EventProcessor<MessageType> processor, Object sender, MessageType message) {
			this.processor = processor;
			this.sender = sender;
			this.message = message;
		}
	}

	protected BlockingQueue<Callback> queue;

	@Override
	public void add(Object sender, MessageType message, EventProcessor<MessageType> processor) {
		queue.add(new Callback(processor, sender, message));
	}

	public ManualDispatcher() {
		queue = new LinkedBlockingQueue<Callback>();
	}

	public void doDispatch() {
		try {
			int i = queue.size();
			while (i > 0) {
				Callback c = queue.poll();
				c.processor.event(c.sender, c.message);
				i--;
			}
		} catch (Exception e) {
			throw Logger.t("ManualDispatcher",e);
		}
	}
}
