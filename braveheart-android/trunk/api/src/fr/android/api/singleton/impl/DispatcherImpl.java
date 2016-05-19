package fr.android.api.singleton.impl;

import fr.android.api.annotation.Singleton;
import fr.android.api.singleton.Disposable;
import fr.android.api.util.Logger;
import fr.android.api.util.ManualDispatcher;

@Singleton
public class DispatcherImpl extends ManualDispatcher<Object> implements Runnable, Disposable {
	private Thread thread;

	final Callback END = new Callback(null,null,null);

	public DispatcherImpl() {
		super();
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void dispose() {
		try {
			queue.put(END);
			thread.join();
		} catch (InterruptedException e) {
			// don't care
		}
	}

	@Override
	public void run() {
		try {
			Callback c = queue.take();
			while (c != END) {
				c.processor.event(c.sender, c.message);
				c = queue.take();
			}
		} catch (Exception e) {
			throw Logger.t("Dispatcher", e);
		}
	}
}
