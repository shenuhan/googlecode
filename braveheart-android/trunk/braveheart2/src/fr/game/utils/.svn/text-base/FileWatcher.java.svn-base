package fr.game.utils;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class FileWatcher extends TimerTask {
	private final File[] f;
	private final Timer t;
	long[] lastModified;

	public FileWatcher(File ... f) {
		this.f = f;
		lastModified = new long[f.length];
		int i=0;
		for (File file : f) {
			lastModified[i++] = file.lastModified();
		}
		t = new Timer();
		t.schedule(this, new Date(), 1000);
	}

	@Override
	public void run() {
		int i=0;
		boolean b = false;
		for (File file : f) {
			System.out.println(file.getName() + " " + file.lastModified());
			if (lastModified[i] != file.lastModified()) {
				b = true;
				lastModified[i] = file.lastModified();
			}
			i++;
		}
		if (b) {
			modified();
		}
	}

	public void stop() {
		t.cancel();
	}

	abstract public void modified();
}
