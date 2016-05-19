package fr.android.api.singleton.impl;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;

import fr.android.api.Processor;
import fr.android.api.util.Logger;

// an obsolete Java ClassParser to be review if useful
public class ClassParserJava {
	public void parse(String packageName, Class<? extends Annotation> annotation, Processor<Class<?>> delegate) {
		ClassLoader cld = Thread.currentThread().getContextClassLoader();
		if (cld == null) {
			throw Logger.t("ClassParser", "Can't get class loader.");
		}

		String path = packageName.replace('.', '/');
		URL resource = cld.getResource(path);
		if (resource == null) {
			throw Logger.t("ClassParser", "No resource for " + path);
		}

		File directory = new File(resource.getFile());
		if (directory.exists()) {
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				// we are only interested in .class files
				if (files[i].endsWith(".class")) {
					// removes the .class extension
					try {
						Class<?> clazz = Class.forName(packageName + '.' + files[i].substring(0, files[i].length() - 6));
						if (annotation == null || clazz.isAnnotationPresent(annotation)) {
							delegate.event(clazz);
						}
					} catch (ClassNotFoundException e) {
						throw Logger.t("ClassParser", "Cannot happened");
					}
				} else {
					parse(packageName + '.' + files[i], delegate);
				}
			}
		}
	}

	public void parse(String packageName, Processor<Class<?>> delegate) {
		parse(packageName, null, delegate);
	}

	public void parse(Class<? extends Annotation> annotation, Processor<Class<?>> delegate) {
		parse("/", annotation, delegate);
	}
}
