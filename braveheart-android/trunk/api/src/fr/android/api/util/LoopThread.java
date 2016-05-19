package fr.android.api.util;

public class LoopThread implements Runnable {
	static private enum State {
		STOPPED,
		STOPPING,
		RUNNING,
	}

	private State state;
	private long step;
	private Thread thread;
	private Runnable runnable;

	public LoopThread(Runnable runnable) {
		this.runnable = runnable;
		state = State.STOPPED;
	}

	public synchronized void start(int step) {
		if (state != State.STOPPED) {
			throw Logger.t("LoopThread", "Cannot start from this state : " + state.toString());
		}
		this.step = step;
		thread = new Thread(this);
		thread.start();
		state = State.RUNNING;
	}

	public void stop() throws InterruptedException {
		if (state == State.RUNNING) {
			state = State.STOPPING;
			synchronized(this) {
				this.notify();
			}
			thread.join();
			state = State.STOPPED;
		}
	}

	public synchronized boolean isStarted() {
		return state == State.RUNNING;
	}

	@Override
	public void run() {
		long nanoStep = 1000000l * (step - 1);
		while(state != State.STOPPING) {
			long lastRun = System.nanoTime();
			runnable.run();
			try {
				synchronized (this) {
					while(System.nanoTime() - lastRun < nanoStep && state != State.STOPPING) {
						this.wait(1);
					}
				}
			} catch (InterruptedException e) {
				throw Logger.t("LoopThread",e);
			}
		}
	}
}
