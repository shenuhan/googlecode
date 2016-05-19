package fr.android.api.singleton.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.android.api.Processor;
import fr.android.api.annotation.Singleton;
import fr.android.api.event.Dispatcher;
import fr.android.api.singleton.Accessor;
import fr.android.api.singleton.ClassParser;
import fr.android.api.singleton.Disposable;
import fr.android.api.singleton.EventManager;
import fr.android.api.singleton.Factory;
import fr.android.api.singleton.SingletonManager;
import fr.android.api.util.Logger;

public class SingletonManagerImpl extends SingletonManager implements Disposable {

	static private final Object initialisationStarted = new Object();

	private Map<Class<?>,Object> singletons = new HashMap<Class<?>, Object>();
	private Set<Class<?>> interfaces = new HashSet<Class<?>>();

	static private SingletonManager instance;
	static synchronized public SingletonManager getInstance() {
		if (instance == null) {
			instance = new SingletonManagerImpl();
		}
		return instance;
	}

	private SingletonManagerImpl() {
	}

	public void initialize() {
		singletons.put(ClassParser.class, new ClassParserAndroid());
		this.getSingleton(ClassParser.class).parse(Singleton.class, new Processor<Class<?>>() {
			@Override
			public void event(Class<?> inter) {
				if (inter.isAnnotation()) {
					return;
				}
				if (inter.isInterface()) {
					interfaces.add(inter);
				} else {
					for (Class<?> clazz : inter.getInterfaces()) {
						if (clazz.isAnnotationPresent(Singleton.class)) {
							return;
						}
					}
					interfaces.add(inter);
				}
			}
		});
		interfaces = Collections.unmodifiableSet(interfaces);
		initSpecial();
		for (Class<?> inter : interfaces) {
			getSingleton(inter);
		}
	}

	@Override
	public synchronized void dispose() {
		for (Class<?> inter : interfaces) {
			Object o = getSingleton(inter);
			if (o instanceof Disposable) {
				((Disposable)o).dispose();
			}
		}
		instance = null;
	}

	private void initSpecial() {
		if (!singletons.containsKey(Factory.class)) {
			singletons.put(Accessor.class, new AccessorImpl());
			singletons.put(Factory.class,new FactoryImpl());
			singletons.put(Dispatcher.class,new DispatcherImpl());
			singletons.put(EventManager.class,new EventManagerImpl());
		}
	}

	private <T> T addSingleton(Class<T> inter) {
		T impl = getSingleton(Factory.class).make(inter);
		for (Class<?> clazz : impl.getClass().getInterfaces()) {
			if (clazz.isAnnotationPresent(Singleton.class)) {
				Object o = singletons.get(clazz);
				if (o == null || o.equals(initialisationStarted)) {
					singletons.put(clazz, impl);
				} else {
					throw Logger.t("Formatter", "Two implementation for the same Singleton.. Should not happened :)");
				}
			}
		}
		singletons.put(impl.getClass(),impl);
		return impl;
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized <T> T getSingleton(Class<T> clazz) {
		T t = (T) singletons.get(clazz);
		if (t == null) {
			if (!interfaces.contains(clazz)) {
				throw Logger.t("Formatter", "This is not a valid singleton interface " + clazz);
			}
			singletons.put(clazz, initialisationStarted);
			return addSingleton(clazz);
		} else if (t.equals(initialisationStarted)) {
			throw Logger.t("Formatter", "Loop problem in singleton constructors :)");
		}
		return t;
	}
}
