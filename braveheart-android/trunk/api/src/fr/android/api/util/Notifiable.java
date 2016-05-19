package fr.android.api.util;

public class Notifiable {
	boolean notify = false;
	public void bnotify() {
		synchronized (this) {
			notify = true;
			notify();
		}
	}

	public void bwait() {
		synchronized (this) {
			if (!notify) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw Logger.t("Notifiable",e);
				}
			}
			notify = false;
		}
	}
}
