package fr.game.engine;

import fr.game.utils.Event;
import fr.game.utils.IEvent;

public class BattleTimer implements Runnable {
	static final long STEP = 10;
	
	private Thread thread;
	private boolean shouldStop;
	private long previousTime;
	
	final private IEvent<Float> step;
	
	public BattleTimer() {
		thread = null;
		shouldStop = false;
		step = new Event<Float>();
	}
	
	public synchronized void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
			previousTime = System.nanoTime();
		}
		shouldStop = false;
	}
	
	public void stop() {
		shouldStop = true;
		try {
			thread.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		while(!shouldStop) {
			try {
				Thread.sleep(1);
				long currentTime = System.nanoTime();
				long mstep = (currentTime - previousTime) / 1000000;
				if (mstep > STEP) {
					float fstep = ((float) mstep)/1000f;
					previousTime += mstep * 1000000;
					BattleTimer.this.step.fireUpdate(fstep);
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public IEvent<Float> getStep() {
		return step;
	}
}
