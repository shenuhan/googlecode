package fr.android.api.singleton;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import fr.android.api.annotation.Singleton;

@Singleton
public interface Accessor {
	public interface Getter {
		Object get(Object o) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
		Class<?> getReturnType();
	}
	
	public interface Setter {
		void set(Object o, Object value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
		Class<?> valueType();
	}
	
	void set(Object o, String field, Object value);
	Object get(Object o, String field);
	Set<String> getFields(Class<?> clazz);
	Class<?> getFieldsClass(Class<?> clazz, String field);
	
	Getter getGetter(Class<?> clazz, String field);
	Setter getSetter(Class<?> clazz, String field);
	
	Set<String> getNativeFields(Class<?> clazz);
	Set<String> getEditableFields(Class<?> clazz);
}
