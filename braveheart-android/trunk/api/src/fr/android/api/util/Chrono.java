package fr.android.api.util;

public class Chrono {
	public interface Callback {
		void process(long min, long max, long mean);
	}
	
	private int step;
	private long currentTime;
	private long mean;
	private long max;
	private long min;
	private int i;
	private Callback callback;
	
	public Chrono(Callback callback) {
		this(1,callback);
	}
	
	public Chrono(int step, Callback callback) {
		this.step = step;
		this.callback = callback;
		init();
	}
	
	private void init() {
		mean = 0;
		max = 0;
		min = Long.MAX_VALUE;
		i = 0;
		currentTime = System.currentTimeMillis();
	}
	
	public void start() {
		currentTime = System.currentTimeMillis();
	}
	
	public void stop() {
		long time = System.currentTimeMillis() - currentTime;
		mean = mean + time;
		min = time < min ? time : min;
		max = time > max ? time : max;
		i++;
		if (i%step == 0) {
			callback.process(min, max, mean/step);
			i = 0;
			mean = 0;
			max = 0;
			min = Long.MAX_VALUE;
		}
	}
}
