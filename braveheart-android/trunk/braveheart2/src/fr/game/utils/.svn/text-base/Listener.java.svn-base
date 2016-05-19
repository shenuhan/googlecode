package fr.game.utils;

import java.util.Observable;
import java.util.Observer;

public abstract class Listener<T> implements Observer {
	public abstract void event(Observable arg0, T arg1);
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable arg0, Object arg1) {
		event(arg0, (T) arg1);
	}
}
