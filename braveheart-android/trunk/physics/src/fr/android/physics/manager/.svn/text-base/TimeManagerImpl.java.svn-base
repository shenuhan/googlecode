package fr.android.physics.manager;

import java.util.Timer;
import java.util.TimerTask;

import fr.android.api.util.Notifiable;


public class TimeManagerImpl implements TimeManager, Runnable {
	private StartEvent startEvent = new StartEvent();
	private StopEvent stopEvent = new StopEvent();
	private StepEvent stepEvent = new StepEvent();
	private Timer timer = new Timer();
	
	private final Notifiable sync = new Notifiable();

	private Thread thread = null;
	
	private boolean shouldStop;
	private long previousStep;
	private long startTime = -1;
	
	@Override
	public synchronized void start(int step) {
		if (thread != null) {
			return;
		}
		this.shouldStop = false;
		this.previousStep = System.currentTimeMillis();
		this.startTime = previousStep; 
		thread = new Thread(this);
		thread.start();
		thread.setPriority(Thread.MAX_PRIORITY);

		timer.schedule(new TimerTask() {
			private long previousTime;
			private long step; 
			@Override
			public void run() {
				long current = System.nanoTime(); 
				if (current - this.previousTime > step) {
					this.previousTime = current;
					sync.bnotify();
				}
			}
			
			TimerTask init(long step) {
				this.step = step * 1000000;
				previousTime = System.nanoTime();
				return this;
			}
		}.init(step), 1, 1);
	}
	
	@Override
	public synchronized void stop() {
		if (thread == null) {
			return;
		}
		shouldStop = true;
		sync.bnotify();
		try {
			thread.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		thread = null;
	}

	@Override
	public void run() {
		startEvent.raise(this);

		while(!shouldStop) {
			sync.bwait();
			if (!shouldStop) {
				long time = System.currentTimeMillis();
				float d = (time - previousStep)/1000.0f;
				previousStep = time;
				stepEvent.raise(this,d);
			}
		}
		
		stopEvent.raise(this);
	}

	@Override
	public float getAbsoluteTime() {
		return (System.currentTimeMillis() - startTime)/1000;
	}
	
	@Override
	public float getStepTime() {
		if (startTime < 0) {
			return 0;
		}
		return (previousStep - startTime)/1000;
	}
}
