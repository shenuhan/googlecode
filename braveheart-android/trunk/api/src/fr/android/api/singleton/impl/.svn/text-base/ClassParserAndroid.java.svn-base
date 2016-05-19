package fr.android.api.singleton.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import fr.android.api.Processor;
import fr.android.api.singleton.ClassParser;
import fr.android.api.util.Logger;


public class ClassParserAndroid implements ClassParser {
	private static Field dexField;

	public ClassParserAndroid() {
		try {
			dexField = PathClassLoader.class.getDeclaredField("mDexs");
		} catch (SecurityException e) {
			throw Logger.t("ClassParser", "Mdex field not accessible",e);
		} catch (NoSuchFieldException e) {
			throw Logger.t("ClassParser", "Mdex field not existing",e);
		}
		dexField.setAccessible(true);
	}


	@Override
	public void parse(String packageName, Class<? extends Annotation> annotation, Processor<Class<?>> delegate) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Map<Class<?>,Boolean> doneClass = new HashMap<Class<?>,Boolean>();

		DexFile[] dexs;
		try {
			dexs = (DexFile[]) dexField.get(classLoader);
		} catch (IllegalArgumentException e) {
			throw Logger.t("ClassParser", "Mdex field bad argument",e);
		} catch (IllegalAccessException e) {
			throw Logger.t("ClassParser", "Mdex field not accessible",e);
		}

		for (DexFile dex : dexs) {
			Enumeration<String> entries = dex.entries();
			while (entries.hasMoreElements()) {
				String entry = entries.nextElement();
				if (packageName == null || entry.startsWith(packageName)) {
					Class<?> entryClass = dex.loadClass(entry, classLoader);
					if (entryClass != null) {
						processClass(entryClass,annotation,delegate,doneClass);
					}
				}
			}
		}
	}

	private boolean processClass(Class<?> clazz, Class<? extends Annotation> annotation, Processor<Class<?>> delegate, Map<Class<?>, Boolean> doneClass) {
		if (clazz == null) {
			return false;
		}
		if (clazz.equals(annotation)) {
			return true;
		}
		Boolean isAnnotated = doneClass.get(clazz);
		if (isAnnotated != null) {
			return isAnnotated;
		}
		doneClass.put(clazz, false);
		boolean res = false;

		for (Annotation a : clazz.getAnnotations()) {
			res |= processClass(a.annotationType(), annotation, delegate, doneClass);
		}
		if (!clazz.isAnnotation()) {
			for (Class<?> i : clazz.getInterfaces()) {
				res |= processClass(i, annotation, delegate, doneClass);
			}
			res |= processClass(clazz.getSuperclass(), annotation, delegate, doneClass);
		}

		res |= (annotation == null);

		if (res) {
			delegate.event(clazz);
		}
		doneClass.put(clazz, res);
		return res;
	}

	@Override
	public void parse(String packageName, Processor<Class<?>> delegate) {
		parse(packageName, null, delegate);
	}

	@Override
	public void parse(Class<? extends Annotation> annotation, Processor<Class<?>> delegate) {
		parse(null, annotation, delegate);
	}
}
