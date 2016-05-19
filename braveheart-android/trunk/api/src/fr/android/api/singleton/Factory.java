package fr.android.api.singleton;

import java.util.Set;

import fr.android.api.annotation.Singleton;
import fr.android.api.event.Event;

@Singleton
public interface Factory {
	<T> T make(Class<T> i, Object ... arguments);
	<T> void recycle(T managedObject);
	<T> void destroy(T managedObject);
	<T> void manage(T managedObject);
	<T> void unmanage(T managedObject);

	<T> boolean isManaged(Class<T> i);
	<T> Class<?> getManagedInterface(Class<T> clazz);
	Set<Class<?>> getManagedInterfaces();

	Event<Object> made();
	Event<Object> recycled();
	Event<Object> managed();
	Event<Object> unmanaged();
}
