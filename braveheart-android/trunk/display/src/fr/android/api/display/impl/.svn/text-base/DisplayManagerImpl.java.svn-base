package fr.android.api.display.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.anddev.andengine.entity.IEntity;

import fr.android.api.Api;
import fr.android.api.Processor;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Singleton;
import fr.android.api.display.Display;
import fr.android.api.display.DisplayManager;
import fr.android.api.display.sprite.entity.EntitySprite;
import fr.android.api.singleton.Accessor;
import fr.android.api.singleton.ClassParser;
import fr.android.api.singleton.Disposable;
import fr.android.api.singleton.Factory;
import fr.android.api.util.Logger;

public class DisplayManagerImpl implements DisplayManager {
	@Singleton
	ClassParser parser;
	@Singleton
	Factory factory;
	@Singleton
	Accessor accessor;

	private Map<Class<?>,Map<Class<?>,Class<? extends IEntity>>> displayers = new HashMap<Class<?>, Map<Class<?>,Class<? extends IEntity>>>();
	private Map<Object,IEntity> existingDisplays = new HashMap<Object, IEntity>();
	private Map<Object,Collection<Object>> children = new HashMap<Object, Collection<Object>>();

	// TODO this is not at all thread safe :) do better
	private Object currentParent;
	@Override
	public Object getCurrentParent() {
		return currentParent;
	}

	@Override
	public IEntity getDisplay(Object managedObject, Object...initializerArgs) {
		IEntity res = existingDisplays.get(managedObject);
		if (res != null) {
			return res;
		}
		tryDisplay(managedObject, null, null, initializerArgs);
		res = existingDisplays.get(managedObject);

		if (res == null) {
			throw Logger.t("DisplayManager", "Cannot find displayer for class " + managedObject.getClass());
		}
		return res;
	}

	@Override
	public void removeDisplay(Object managedObject) {
		Collection<Object> children = this.children.get(managedObject);
		if (children != null) {
			for (Object child : children) {
				removeDisplay(child);
			}
			this.children.remove(managedObject);
		}
		IEntity entity = existingDisplays.get(managedObject);
		if (entity != null) {
			entity.clearEntityModifiers();
			entity.clearUpdateHandlers();
			existingDisplays.remove(managedObject);
			if (entity instanceof Disposable) {
				((Disposable) entity).dispose();
			}
			entity.detachChildren();
		}
	}

	private void tryDisplayMany(Collection<? extends Object> managedObjects, Object parent, IEntity displayParent) {
		for (Object o : managedObjects) {
			tryDisplay(o, parent, displayParent);
		}
	}

	private void tryDisplayMany(Object[] managedObjects, Object parent, IEntity displayParent) {
		for (Object o : managedObjects) {
			tryDisplay(o, parent, displayParent);
		}
	}

	private void tryDisplay(Object managedObject, final Object parent, IEntity displayParent, Object...initializerArgs) {
		if (managedObject == null) {
			return;
		}

		Object oldParent = currentParent;
		currentParent = parent;

		if (managedObject instanceof Object[]) {
			tryDisplayMany((Object[])managedObject, parent, displayParent);
		} else if (managedObject instanceof Map<?,?>) {
			tryDisplayMany(((Map<?,?>) managedObject).keySet(), parent, displayParent);
			tryDisplayMany(((Map<?,?>) managedObject).values(), parent, displayParent);
		} else if (managedObject instanceof Collection<?>) {
			tryDisplayMany((Collection<?>)managedObject, parent, displayParent);
		} else {
			Class<? extends IEntity> displayer = getDisplayer(managedObject.getClass(), parent);
			if (displayer != null) {
				Object[] args = new Object[initializerArgs.length + 1];
				args[0] = managedObject;
				for (int i=0; i<initializerArgs.length; i++) {
					args[i+1] = initializerArgs[i];
				}
				IEntity entity = Api.make(displayer, args);
				existingDisplays.put(managedObject, entity);
				if (parent != null) {
					if (!children.containsKey(parent)) {
						children.put(parent, new ArrayList<Object>());
					}
					children.get(parent).add(managedObject);
				}
				for (String field : accessor.getFields(managedObject.getClass())) {
					Object o = accessor.get(managedObject, field);
					tryDisplay(o, managedObject, entity);
				}
				entity.sortChildren();
				if (displayParent != null) {
					displayParent.attachChild(entity);
					if (displayParent instanceof EntitySprite && entity instanceof EntitySprite) {
						if (((EntitySprite)displayParent).isFlipped()) {
							((EntitySprite)entity).flip();
						}
					}
				}
			}
		}

		currentParent = oldParent;
	}

	private Class<? extends IEntity> getDisplayer(Class<?> clazz, Object currentParent) {
		Class<? extends IEntity> displayer = null;
		if (displayers.containsKey(clazz)) {
			for (java.util.Map.Entry<Class<?>,Class<? extends IEntity>> entry : displayers.get(clazz).entrySet()) {
				if (currentParent == null || entry.getKey().isAssignableFrom(currentParent.getClass())) {
					displayer = entry.getValue();
					break;
				}
			}
		}
		if (displayer == null) {
			for (Class<?> inter : clazz.getInterfaces()) {
				if (displayers.containsKey(inter)) {
					for (java.util.Map.Entry<Class<?>,Class<? extends IEntity>> entry : displayers.get(inter).entrySet()) {
						if (currentParent == null || entry.getKey().isAssignableFrom(currentParent.getClass())) {
							displayer = entry.getValue();
							break;
						}
					}
					if (displayer != null) {
						break;
					}
				}
			}
		}
		return displayer;
	}

	@Initializer
	public void initialize() {
		parser.parse(Display.class, new Processor<Class<?>>() {
			@SuppressWarnings("unchecked")
			@Override
			public void event(Class<?> t) {
				if (t.isAnnotation()) {
					return;
				}
				Display d = t.getAnnotation(Display.class);
				if (d != null) {
					if (!displayers.containsKey(d.displayed())) {
						displayers.put(d.displayed(), new HashMap<Class<?>, Class<? extends IEntity>>());
					}
					displayers.get(d.displayed()).put(d.parent(),(Class<? extends IEntity>)t);
				}
			}
		});
	}
}
