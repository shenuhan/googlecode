package fr.android.api;

public interface Processor<T> {
	public void event(T t);
}
