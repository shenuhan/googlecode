package fr.android.api.util;

public class Synchro {
	private Object lock = new Object();
	boolean waiting = false;
	public void synchro() {
		synchronized (lock) {
			if (waiting) {
				waiting = false;
				lock.notify();
			} else {
				waiting = true;
				try {
					lock.wait();
				} catch (InterruptedException e) {
					throw Logger.t("Synchro",e);
				}
			}
		}
	}
}
