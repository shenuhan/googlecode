package fr.android.api;

import fr.android.api.singleton.Accessor;
import fr.android.api.singleton.Factory;
import fr.android.api.singleton.SaverLoader;
import fr.android.api.singleton.SingletonManager;


public final class Api {
	public synchronized static void start() {
		SingletonManager.getInstance();
	}

	public synchronized static void stop() {
		SingletonManager.stop();
	}

	public static <SingletonType> SingletonType singleton(Class<SingletonType> clazz) {
		return SingletonManager.getInstance().getSingleton(clazz);
	}

	public static <ManagedObjectType> ManagedObjectType make(Class<ManagedObjectType> clazz, Object ... arguments) {
		return singleton(Factory.class).make(clazz, arguments);
	}

	@SuppressWarnings("unchecked")
	public static <ManagedObjectType> Facade<ManagedObjectType> facade(ManagedObjectType object) {
		return Api.make(Facade.class, object);
	}

	public static <ManagedObjectType> void recycle(ManagedObjectType object) {
		singleton(Factory.class).recycle(object);
	}

	public static <ManagedObjectType> void destroy(ManagedObjectType object) {
		singleton(Factory.class).destroy(object);
	}

	public static <ManagedObjectType> String save(ManagedObjectType managedObject) {
		return singleton(SaverLoader.class).getSaveString(managedObject);
	}

	public static <ManagedObjectType> ManagedObjectType load(Class<ManagedObjectType> clazz, String savedString) {
		return singleton(SaverLoader.class).loadObject(clazz, savedString);
	}

	public static Object get(Object o, String fieldName) {
		return singleton(Accessor.class).get(o,fieldName);
	}

	public static void set(Object o, String fieldName, Object value) {
		singleton(Accessor.class).set(o,fieldName,value);
	}

	public static void manage(Object manageable) {
		singleton(Factory.class).manage(manageable);
	}

	public static void unmanage(Object manageable)  {
		singleton(Factory.class).unmanage(manageable);
	}
}
