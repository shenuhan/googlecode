package fr.android.api.singleton.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.android.api.Api;
import fr.android.api.Processor;
import fr.android.api.annotation.Listen;
import fr.android.api.annotation.Listen.Priority;
import fr.android.api.annotation.Managed;
import fr.android.api.event.AgregateEventProcessor;
import fr.android.api.event.Event;
import fr.android.api.event.EventProcessor;
import fr.android.api.singleton.ClassParser;
import fr.android.api.singleton.EventManager;
import fr.android.api.singleton.Factory;
import fr.android.api.util.Logger;

public class EventManagerImpl implements EventManager {
	private class Listener<MessageType> {
		public AgregateEventProcessor<MessageType> agregator;

		final public Listen listen;
		final public Method callback;
		final public Method event;

		public Listener(Listen listen, Method callback, Method event) {
			this.listen = listen;
			this.callback = callback;
			this.event = event;
			agregator = new AgregateEventProcessor<MessageType>() {
				@Override
				public Priority getPriority() {
					return Listener.this.listen.priority();
				}
			};
		}

		@SuppressWarnings("unchecked")
		public void addObject(Object managedObject) {
			try {
				agregator.addSource((Event<MessageType>) event.invoke(managedObject));
			} catch (Exception e) {
				throw Logger.t("EventManager", e);
			}
		}

		@SuppressWarnings("unchecked")
		public void removeObject(Object managedObject) {
			try {
				agregator.removeSource((Event<MessageType>) event.invoke(managedObject));
			} catch (Exception e) {
				throw Logger.t("EventManager", e);
			}
		}

		private Map<Object,EventProcessor<MessageType>> subscribed = new HashMap<Object, EventProcessor<MessageType>>();

		public void subscribe(final Object listener) {
			EventProcessor<MessageType> processor = new EventProcessor<MessageType>() {
				@Override
				public Priority getPriority() {
					return Listener.this.listen.priority();
				}

				@Override
				public void event(Object sender, MessageType message) {
					try {
						Listener.this.callback.invoke(listener, sender, message);
					} catch (Exception e) {
						throw Logger.t("EventManager", "Problème callback: " + Listener.this.callback.toString() , e);
					}
				}
			};

			synchronized (subscribed) {
				subscribed.put(listener,processor);
				if (listen.async()) {
					agregator.asyncSubscribe(processor);
				} else {
					agregator.subscribe(processor);
				}
			}
		}

		public void unsubscribe(final Object listener) {
			synchronized (subscribed) {
				agregator.unsubscribe(subscribed.get(listener));
			}
		}
	}

	private Map<Class<?>, Collection<Listener<?>>> listeners = new HashMap<Class<?>, Collection<Listener<?>>>();
	private Map<Class<?>, Collection<Listener<?>>> sources = new HashMap<Class<?>, Collection<Listener<?>>>();
	private Map<Class<?>, Map<String, Method>> events = new HashMap<Class<?>, Map<String,Method>>();

	public EventManagerImpl() {
		init();
		EventProcessor<Object> subscriptor = new EventProcessor<Object>() {
			@Override
			public void event(Object factory, final Object managedObject) {
				Collection<Listener<?>> listeners = EventManagerImpl.this.listeners.get(managedObject.getClass());
				if (listeners != null) {
					for (Listener<?> listener : listeners) {
						listener.subscribe(managedObject);
					}
				}
				Collection<Listener<?>> sources = EventManagerImpl.this.sources.get(managedObject.getClass());
				if (sources != null) {
					for (Listener<?> source : sources) {
						source.addObject(managedObject);
					}
				}
			}
			@Override
			public Priority getPriority() {
				return Priority.Medium;
			}
		};

		EventProcessor<Object> unsubscriptor = new EventProcessor<Object>() {
			@Override
			public void event(Object factory, final Object managedObject) {
				Collection<Listener<?>> listeners = EventManagerImpl.this.listeners.get(managedObject.getClass());
				if (listeners != null) {
					for (Listener<?> listener : listeners) {
						listener.unsubscribe(managedObject);
					}
				}
				Collection<Listener<?>> sources = EventManagerImpl.this.sources.get(managedObject.getClass());
				if (sources != null) {
					for (Listener<?> source : sources) {
						source.removeObject(managedObject);
					}
				}
			}
			@Override
			public Priority getPriority() {
				return Priority.Medium;
			}
		};

		Api.singleton(Factory.class).managed().subscribe(subscriptor);
		Api.singleton(Factory.class).unmanaged().subscribe(unsubscriptor);
	}

	private void init() {
		Api.singleton(ClassParser.class).parse(Managed.class, new Processor<Class<?>>() {
			@Override
			public void event(Class<?> clazz) {
				if (clazz.isAnnotation()){
					return;
				}

				Map<String,Method> map = new HashMap<String, Method>();

				for (Method method : clazz.getMethods()) {
					Class<?> event = method.getReturnType();
					if (Event.class.isAssignableFrom(event)) {
						map.put(method.getName(), method);
					}
				}
				for (Class<?> inter : clazz.getInterfaces()) {
					if (listeners.containsKey(inter)) {
						map.putAll(events.get(inter));
					}
				}
				if (listeners.containsKey(clazz.getSuperclass())) {
					map.putAll(events.get(clazz.getSuperclass()));
				}
				if (map.size() > 0) {
					events.put(clazz, map);
				}
			}
		});

		Api.singleton(ClassParser.class).parse(Managed.class, new Processor<Class<?>>() {
			@Override
			public void event(Class<?> clazz) {
				if (clazz.isAnnotation()){
					return;
				}

				Collection<Listener<?>> listListeners = new ArrayList<Listener<?>>();
				for (Method method : clazz.getMethods()) {
					Listen annotation = method.getAnnotation(Listen.class);
					if (annotation != null) {
						@SuppressWarnings("rawtypes")
						Listener<?> listener = new Listener(annotation,method,events.get(annotation.senderClass()).get(annotation.event()));
						listListeners.add(listener);
						Collection<Listener<?>> listSources = sources.get(annotation.senderClass());
						if (listSources == null) {
							listSources = new ArrayList<EventManagerImpl.Listener<?>>();
							sources.put(annotation.senderClass(), listSources);
						}
						listSources.add(listener);
					}
				}
				for (Class<?> inter : clazz.getInterfaces()) {
					if (listeners.containsKey(inter)) {
						listListeners.addAll(listeners.get(inter));
					}
				}
				if (listeners.containsKey(clazz.getSuperclass())) {
					listListeners.addAll(listeners.get(clazz.getSuperclass()));
				}
				if (listListeners.size() > 0) {
					listeners.put(clazz, listListeners);
				}
			}
		});

		Api.singleton(ClassParser.class).parse(Managed.class, new Processor<Class<?>>() {
			@Override
			public void event(Class<?> clazz) {
				if (clazz.isAnnotation()){
					return;
				}

				Collection<Listener<?>> listSources = new ArrayList<Listener<?>>();
				for (Class<?> inter : clazz.getInterfaces()) {
					if (sources.containsKey(inter)) {
						listSources.addAll(sources.get(inter));
					}
				}
				if (sources.containsKey(clazz.getSuperclass())) {
					listSources.addAll(sources.get(clazz.getSuperclass()));
				}
				if (listSources.size() > 0) {
					sources.put(clazz, listSources);
				}
			}
		});
	}
}

