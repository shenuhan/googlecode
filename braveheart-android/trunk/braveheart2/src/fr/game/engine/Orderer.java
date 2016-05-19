package fr.game.engine;

import java.util.Comparator;
import java.util.Observable;
import java.util.PriorityQueue;
import java.util.Stack;

import fr.game.utils.Listener;

public class Orderer {
	private static class Timed {
		public float time;
		public Runnable runnable;
	}

	private Stack<Timed> pool;
	private float currentTime = 0f;

	final private PriorityQueue<Timed> triggers;

	public Orderer(BattleTimer timer) {
		pool = new Stack<Orderer.Timed>();

		triggers = new PriorityQueue<Timed>(50,new Comparator<Timed>() {
			@Override
			public int compare(Timed arg0, Timed arg1) {
				if (arg0.time < arg1.time)
					return -1;
				return 1;
			}
		});
		timer.getStep().addListener(new Listener<Float>() {
			@Override
			public void event(Observable arg0, Float step) {
				currentTime += step;
				while(triggers.size() > 0 && currentTime > triggers.peek().time) {
					Timed timed = triggers.poll();
					Runnable r = timed.runnable;
					pool.push(timed);
					r.run();
				}
			}
		});
	}

	public void addTimedEvent(float time,Runnable runnable) {
		Timed timed;
		if (pool.size() > 0) {
			timed = pool.pop();
		} else {
			timed = new Timed();
		}
		timed.time = time;
		timed.runnable = runnable;
		triggers.add(timed);
	}

	public void addDelayedEvent(float time,Runnable runnable) {
		Timed timed;
		if (pool.size() > 0) {
			timed = pool.pop();
		} else {
			timed = new Timed();
		}
		timed.time = time + currentTime;
		timed.runnable = runnable;
		triggers.add(timed);
	}

	public float getTime() {
		return currentTime;
	}
}
