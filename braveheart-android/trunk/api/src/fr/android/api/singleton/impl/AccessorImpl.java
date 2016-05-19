package fr.android.api.singleton.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.android.api.Api;
import fr.android.api.Processor;
import fr.android.api.annotation.Field;
import fr.android.api.annotation.Managed;
import fr.android.api.annotation.Singleton;
import fr.android.api.singleton.Accessor;
import fr.android.api.singleton.ClassParser;
import fr.android.api.singleton.Factory;
import fr.android.api.util.Logger;


public class AccessorImpl implements Accessor {
	private class InnerField {
		public Class<?> clazz;
		public String name;

		public Field field;
		@SuppressWarnings("unused")
		public Singleton singleton;

		public Getter getter;
		public Setter setter;

		public InnerField(String name, Getter getter, Setter setter) {
			clazz = getter.getReturnType();
			this.name = name;
			this.getter = getter;
			this.setter = setter;
		}
	}
	private Map<Class<?>, Map<String,InnerField>> fields = new HashMap<Class<?>, Map<String,InnerField>>();

	private Map<Class<?>, Set<String>> allNativeFields = new HashMap<Class<?>, Set<String>>();
	private Map<Class<?>, Set<String>> allEditableFields = new HashMap<Class<?>, Set<String>>();

	public AccessorImpl() {
		Api.singleton(ClassParser.class).parse(Managed.class, new Processor<Class<?>>() {
			@Override
			public void event(Class<?> clazz) {
				if(clazz.isAnnotation()) {
					return;
				}
				Map<String,InnerField> localFields = new HashMap<String, InnerField>();
				fields.put(clazz,localFields);

				if (! clazz.isInterface()) {
					for (Class<?> i : clazz.getInterfaces()) {
						if (fields.containsKey(i)) {
							localFields.putAll(fields.get(i));
						}
					}
					if (fields.containsKey(clazz.getSuperclass())) {
						localFields.putAll(fields.get(clazz.getSuperclass()));
					}
				}
				for (Entry<String,InnerField> entry : localFields.entrySet()) {
					String fieldName = entry.getKey();
					InnerField innerField = entry.getValue();

					Getter getter = findGetter(clazz,fieldName);
					Setter setter = findSetter(clazz,fieldName);

					if (getter != null) {
						innerField.getter = getter;
					}
					if (setter != null) {
						innerField.setter = setter;
					}
				}

				for (Method method : clazz.getMethods()) {
					if (method.isAnnotationPresent(Field.class)) {
						String fieldName = strip(method.getName());
						if (localFields.containsKey(fieldName)) {
							throw Logger.t("Accessor", "Field annotation double for field : " + fieldName + " in class " + clazz);
						}
						InnerField innerField = new InnerField(fieldName,findGetter(clazz,fieldName), findSetter(clazz,fieldName));
						innerField.field = method.getAnnotation(Field.class);
						innerField.singleton = method.getAnnotation(Singleton.class);
						localFields.put(fieldName, innerField);
					}
				}
				for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
					if (field.isAnnotationPresent(Field.class) || field.isAnnotationPresent(Singleton.class)) {
						String fieldName = field.getName();
						if (localFields.containsKey(fieldName)) {
							throw Logger.t("Accessor", "Field annotation double for field : " + fieldName + " in class " + clazz);
						}
						InnerField innerField = new InnerField(fieldName,findGetter(clazz,fieldName), findSetter(clazz,fieldName));
						innerField.field = field.getAnnotation(Field.class);
						innerField.singleton = field.getAnnotation(Singleton.class);
						localFields.put(fieldName, innerField);
					}
				}
			}

			private Setter findSetter(Class<?> clazz, String fieldName) {
				String upField = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
				Set<String> possible = new HashSet<String>(Arrays.asList(new String[]{"set" + upField , fieldName}));
				for (final Method method : clazz.getMethods()) {
					if (possible.contains(method.getName()) && method.getParameterTypes().length == 1) {
						method.setAccessible(true);
						return new Setter() {
							@Override
							public void set(Object o, Object value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
								method.invoke(o, value);
							}
							@Override
							public Class<?> valueType() {
								return method.getParameterTypes()[0];
							}
						};
					}
				}
				for (final java.lang.reflect.Field field : clazz.getDeclaredFields()) {
					if (field.getName().equals(fieldName)) {
						field.setAccessible(true);
						return new Setter() {
							@Override
							public void set(Object o, Object value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
								field.set(o, value);
							}
							@Override
							public Class<?> valueType() {
								return field.getType();
							}
						};
					}
				}
				return null;
			}

			private Getter findGetter(Class<?> clazz, String fieldName) {
				String upField = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
				Set<String> possible = new HashSet<String>(Arrays.asList(new String[]{"get" + upField , fieldName, "is" + upField}));
				for (final Method method : clazz.getMethods()) {
					if (possible.contains(method.getName()) && method.getParameterTypes().length == 0) {
						method.setAccessible(true);
						return new Getter() {
							@Override
							public Object get(Object o) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
								return method.invoke(o);
							}
							@Override
							public Class<?> getReturnType() {
								return method.getReturnType();
							}
						};
					}
				}
				for (final java.lang.reflect.Field field : clazz.getDeclaredFields()) {
					if (field.getName().equals(fieldName)) {
						field.setAccessible(true);
						return new Getter() {
							@Override
							public Object get(Object o) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
								return field.get(o);
							}
							@Override
							public Class<?> getReturnType() {
								return field.getType();
							}
						};
					}
				}
				return null;
			}
		});

		for(Class<?> clazz : fields.keySet()) {
			initNative(clazz);
		}
	}

	private void initNative(Class<?> clazz) {
		if (allNativeFields.containsKey(clazz)) {
			return;
		}
		Set<String> natives = new HashSet<String>();
		allNativeFields.put(clazz,natives);
		Set<String> editable = new HashSet<String>();
		allEditableFields.put(clazz, editable);
		for (Entry<String,InnerField> entry : fields.get(clazz).entrySet()) {
			if (entry.getValue().field == null) {
				continue;
			}
			if (fields.containsKey(entry.getValue().clazz)) {
				initNative(entry.getValue().clazz);
				for (String s : allNativeFields.get(entry.getValue().clazz)) {
					natives.add(entry.getValue().name + "." + s);
				}
				if (entry.getValue().field.editable()) {
					for (String s : allEditableFields.get(entry.getValue().clazz)) {
						editable.add(entry.getValue().name + "." + s);
					}
				}
			} else {
				natives.add(entry.getValue().name);
				if (entry.getValue().field.editable()) {
					editable.add(entry.getValue().name);
				}
			}
		}
	}

	private String strip(String name) {
		String stripped = name;
		if (name.startsWith("get") || name.startsWith("set")) {
			stripped = stripped.substring(3);
		} else if (name.startsWith("is")) {
			stripped = stripped.substring(2);
		}
		stripped = stripped.substring(0,1).toLowerCase() + stripped.substring(1);
		return stripped;
	}

	@Override
	public Object get(Object o, String field) {
		try {
			return get(o, new ArrayList<String>(Arrays.asList(field.split("\\."))));
		} catch (Exception e) {
			throw Logger.t("Accessor", "Cannot invoke getter " + field + "on " + o.getClass());
		}
	}

	private Object get(Object o, List<String> fieldsList) {
		if (fieldsList.size() == 0) {
			return o;
		}
		Map<String, InnerField> localFields = fields.get(o.getClass());
		Getter getter = localFields.get(fieldsList.get(0)).getter;
		fieldsList.remove(0);
		try {
			return get(getter.get(o), fieldsList);
		} catch (Exception e) {
			throw Logger.t("Accessor", "Cannot invoke getter on " + o.getClass());
		}
	}

	@Override
	public void set(Object o, String field, Object value) {
		try {
			set(o, new ArrayList<String>(Arrays.asList(field.split("\\."))), value);
		} catch (Exception e) {
			throw Logger.t("Accessor", "Cannot invoke setter " + field + " on " + o.getClass() + " with argument " + value.getClass(),e);
		}
	}

	public void set(Object o, List<String> fieldsList, Object value) {
		if (fieldsList.size() == 0) {
			throw Logger.t("Accessor", "Cannot invoke setter directly on field");
		}
		String field = fieldsList.get(fieldsList.size() - 1);
		fieldsList.remove(fieldsList.size() - 1);
		Object lastObject = get(o,fieldsList);

		Map<String, InnerField> localFields = fields.get(lastObject.getClass());
		Setter setter = localFields.get(field).setter;
		try {
			setter.set(lastObject, value);
		} catch (Exception e) {
			throw Logger.t("Accessor", "Cannot invoke setter " + field + " on " + o.getClass() + " with argument " + value.getClass(),e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getFields(Class<?> clazz) {
		if (Api.singleton(Factory.class).isManaged(clazz)) {
			return fields.get(clazz).keySet();
		}
		return Collections.EMPTY_SET;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getNativeFields(Class<?> clazz) {
		if (Api.singleton(Factory.class).isManaged(clazz)) {
			return allNativeFields.get(clazz);
		}
		return Collections.EMPTY_SET;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getEditableFields(Class<?> clazz) {
		if (Api.singleton(Factory.class).isManaged(clazz)) {
			return allEditableFields.get(clazz);
		}
		return Collections.EMPTY_SET;
	}

	@Override
	public Class<?> getFieldsClass(Class<?> clazz, String field) {
		return getField(clazz, field).clazz;
	}

	@Override
	public Getter getGetter(Class<?> clazz, String field) {
		return getField(clazz, field).getter;
	}

	@Override
	public Setter getSetter(Class<?> clazz, String field) {
		return getField(clazz, field).setter;

	}

	private InnerField getField(Class<?> clazz, String field) {
		InnerField innerField = null;
		for(String s : field.split("\\.")) {
			innerField = fields.get(innerField == null ? clazz : innerField.clazz).get(s);
		}
		return innerField;
	}
}
