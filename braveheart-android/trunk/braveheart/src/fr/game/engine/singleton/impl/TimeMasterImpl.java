package fr.game.engine.singleton.impl;

import android.util.FloatMath;
import fr.android.api.event.Event;
import fr.android.api.singleton.Disposable;
import fr.android.api.util.Logger;
import fr.android.api.util.LoopThread;
import fr.game.engine.singleton.TimeMaster;

public class TimeMasterImpl implements TimeMaster, Runnable, Disposable {
	private long startTime;
	private long lastStep;

	private float slowFactor;

	private LoopThread loop = new LoopThread(this);

	@Override
	public void dispose() {
		try {
			loop.stop();
		} catch (InterruptedException e) {
			// don't care
		}
	}

	@Override
	public synchronized void start(int step) {
		if (loop.isStarted()) {
			return;
		}
		slowFactor = 1f;
		startTime = System.currentTimeMillis();
		lastStep = startTime;
		loop.start(step);
	}

	@Override
	public void stop() {
		if (loop.isStarted()) {
			try {
				loop.stop();
			} catch (InterruptedException e) {
				throw Logger.t("TimeMaster", e);
			}
		}
	}

	@Override
	public void run() {
		long nextStep = System.currentTimeMillis();
		long step = nextStep - lastStep;
		if (slowFactor != 1f) {
			step = (long) FloatMath.floor(step * slowFactor + 0.00001f);
		}
		lastStep = lastStep + step;
		event.raise(this, step);
	}

	@Override
	public long getStartTime() {
		return startTime;
	}

	private Event<Long> event = new Event<Long>();
	@Override
	public Event<Long> time() {
		return event;
	}

	@Override
	public synchronized boolean isStarted() {
		return loop.isStarted();
	}

	@Override
	public void setSlowFactor(float slowFactor) {
		this.slowFactor = slowFactor;
	}
}
