package fr.android.api.singleton;

import java.lang.annotation.Annotation;

import fr.android.api.Processor;
import fr.android.api.annotation.Singleton;

@Singleton
public interface ClassParser {
	public void parse(String packageName, Class<? extends Annotation> annotation, Processor<Class<?>> delegate);
	public void parse(String packageName, Processor<Class<?>> delegate);
	public void parse(Class<? extends Annotation> annotation, Processor<Class<?>> delegate);
}
