package fr.android.api.singleton;

import fr.android.api.annotation.Singleton;

@Singleton
public interface SaverLoader {
	String getSaveString(Object o);
	<T> T loadObject(Class<T> clazz, String savedString);
}
