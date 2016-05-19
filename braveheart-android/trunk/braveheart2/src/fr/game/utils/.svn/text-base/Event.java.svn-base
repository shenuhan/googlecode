package fr.game.utils;

import java.util.Observable;

public class Event<T> extends Observable implements IEvent<T> {
	@Override
	public void fireUpdate(T arg0) {
		setChanged();
		notifyObservers(arg0);
	}
	@Override
	public void fireUpdate() {
		setChanged();
		notifyObservers();
	}
	@Override
	public synchronized void addListener(Listener<T> arg0) {
		super.addObserver(arg0);
	}
	@Override
	public void deleteListener(Listener<T> arg0) {
		super.deleteObserver(arg0);
	}
	@Override
	public void deleteListeners() {
		super.deleteObservers();
	}
}
