package fr.android.api.singleton.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fr.android.api.Api;
import fr.android.api.Processor;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;
import fr.android.api.annotation.Singleton;
import fr.android.api.event.Event;
import fr.android.api.singleton.Accessor;
import fr.android.api.singleton.Accessor.Setter;
import fr.android.api.singleton.ClassParser;
import fr.android.api.singleton.Disposable;
import fr.android.api.singleton.Factory;
import fr.android.api.singleton.SingletonManager;
import fr.android.api.util.Logger;


public class FactoryImpl implements Factory {
	private Map<Class<?>, Class<?>> impls = new HashMap<Class<?>, Class<?>>();
	private Map<Class<?>, Class<?>> interfaces = new HashMap<Class<?>, Class<?>>();
	private Map<Class<?>, Map<Integer,Method>> initializers = new HashMap<Class<?>, Map<Integer,Method>>();
	private Map<Class<?>, Collection<Setter>> singletons = new HashMap<Class<?>, Collection<Setter>>();
	private Map<Class<?>, Collection<Object>> recycled = new HashMap<Class<?>, Collection<Object>>();

	private Set<Class<?>> managedType = new HashSet<Class<?>>();

	private Event<Object> madeEvent;
	private Event<Object> recycledEvent;
	private Event<Object> managedEvent;
	private Event<Object> unmanagedEvent;

	public FactoryImpl() {
		Api.singleton(ClassParser.class).parse(Managed.class, new Processor<Class<?>>() {
			@Override
			public void event(Class<?> clazz) {
				if (clazz.isAnnotation()){
					return;
				}
				if (managedType.contains(clazz)) {
					return;
				}
				managedType.add(clazz);
				if (!clazz.isInterface()) {
					recycled.put(clazz, new ArrayList<Object>());

					for (Class<?> i : clazz.getInterfaces()) {
						if(managedType.contains(i)) {
							interfaces.put(clazz,i);
							Class<?> already = impls.get(i);
							if (already != null) {
								if (clazz.isAssignableFrom(already)) {
									return;
								} else if (!already.isAssignableFrom(clazz)) {
									throw Logger.t("Factory", "two different classes for the same interface : " + i.getName() );
								}
							}
							impls.put(i,clazz);
						}
					}
					if (managedType.contains(clazz.getSuperclass())) {
						impls.put(clazz.getSuperclass(),clazz);
					}
					impls.put(clazz, clazz);
				}

				initializers.put(clazz, new HashMap<Integer, Method>());
				Map<Integer, Method> initializer = initializers.get(clazz);

				singletons.put(clazz, new ArrayList<Setter>());
				Collection<Setter> singleton = singletons.get(clazz);

				for (Method method : clazz.getMethods()) {
					if (method.getAnnotation(Initializer.class) != null) {
						initializer.put(method.getParameterTypes().length, method);
					}
					if (method.getAnnotation(Singleton.class) != null) {
						singleton.add(Api.singleton(Accessor.class).getSetter(clazz, method.getName()));
					}
				}
				for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
					if (field.getAnnotation(Singleton.class) != null) {
						singleton.add(Api.singleton(Accessor.class).getSetter(clazz, field.getName()));
					}
				}
				for (Class<?> inter : clazz.getInterfaces()) {
					if (initializers.containsKey(inter)) {
						initializer.putAll(initializers.get(inter));
					}
					if (singletons.containsKey(inter)) {
						singleton.addAll(singletons.get(inter));
					}
				}
				if (initializers.containsKey(clazz.getSuperclass())) {
					initializer.putAll(initializers.get(clazz.getSuperclass()));
				}
				if (singletons.containsKey(clazz.getSuperclass())) {
					singleton.addAll(singletons.get(clazz.getSuperclass()));
				}
			}

		});

		madeEvent = new Event<Object>();
		recycledEvent = new Event<Object>();
		managedEvent = new Event<Object>();
		unmanagedEvent = new Event<Object>();
	}

	private <T> Class<T> getImplementationClass(Class<T> inter)  {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) impls.get(inter);

		if (clazz == null) {
			throw Logger.t("Factory", "Cannot find class for interface " + inter.toString());
		}
		return clazz;
	}

	@Override
	public <T> T make(Class<T> inter, Object ... arguments) {
		T res = silentMake(inter, arguments);
		madeEvent.raise(this, res);
		managedEvent.raise(this, res);
		return res;
	}

	@Override
	public <T> void recycle(T managedObject) {
		if (managedObject != null) {
			destroy(managedObject);
			Collection<Object> recycle = recycled.get(managedObject.getClass());
			synchronized (recycle) {
				recycle.add(managedObject);
			}
		}
	}

	@Override
	public <T> void destroy(T managedObject) {
		if (managedObject != null) {
			unmanagedEvent.raise(this,managedObject);
			recycledEvent.raise(this,managedObject);
			if (managedObject instanceof Disposable) {
				((Disposable)managedObject).dispose();
			}
		}
	}

	@Override
	public <T> void manage(T managedObject) {
		if (managedObject != null) {
			try {
				injectSingleton(managedObject);
				managedEvent.raise(this, managedObject);
			} catch (Exception e) {
				throw Logger.t("Factory", "Cannot inject singleton ... Maybe the singleton field is read only");
			}
		}
	}

	@Override
	public <T> void unmanage(T managedObject) {
		if (managedObject != null) {
			unmanagedEvent.raise(this,managedObject);
		}
	}

	private <T> T getRecycled(Class<T> clazz) {
		Collection<Object> recycle = recycled.get(clazz);
		synchronized (recycle) {
			Iterator<Object> it = recycle.iterator();
			if (!it.hasNext()) {
				return null;
			}
			@SuppressWarnings("unchecked")
			T res = (T) it.next();
			it.remove();
			return res;
		}
	}

	private <T> void injectSingleton(T managedObject) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (singletons.containsKey(managedObject.getClass())) {
			for (Setter setter : singletons.get(managedObject.getClass())) {
				setter.set(managedObject, SingletonManager.getInstance().getSingleton(setter.valueType()));
			}
		}
	}

	<T> T silentMake(Class<T> inter, Object ... arguments) {
		try {
			Class<T> implementation = getImplementationClass(inter);
			T res = getRecycled(implementation);
			if (res == null) {
				Constructor<T> constructor = implementation.getConstructor();
				res = constructor.newInstance();
				injectSingleton(res);
			}
			Map<Integer, Method> initializer = initializers.get(res.getClass());
			if (initializer.containsKey(arguments.length)) {
				initializer.get(arguments.length).invoke(res, arguments);
			} else {
				if (arguments.length != 0) {
					throw Logger.t("Factory", "Initializer not found for " + arguments.length + " arguments");
				}
			}
			return res;
		} catch (SecurityException e) {
			throw Logger.t("Factory", "Cannot access constructor " + inter,e);
		} catch (Exception e) {
			throw Logger.t("Factory", "Cannot instanciate class " + inter,e);
		}
	}

	@Override
	public <T> boolean isManaged(Class<T> i) {
		return interfaces.containsKey(i) || impls.containsKey(i);
	}

	@Override
	public <T> Class<?> getManagedInterface(Class<T> clazz) {
		if (! clazz.isInterface()) {
			return clazz;
		}
		Class<?> i = interfaces.get(clazz);
		if (i == null) {
			throw Logger.t("Factory", "Class not managed " + clazz);
		}
		return i;
	}

	@Override
	public Set<Class<?>> getManagedInterfaces() {
		return managedType;
	}

	@Override
	public Event<Object> made() {
		return madeEvent;
	}

	@Override
	public Event<Object> recycled() {
		return recycledEvent;
	}

	@Override
	public Event<Object> managed() {
		return managedEvent;
	}

	@Override
	public Event<Object> unmanaged() {
		return unmanagedEvent;
	}
}
