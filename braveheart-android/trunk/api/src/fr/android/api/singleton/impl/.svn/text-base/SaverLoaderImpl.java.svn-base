package fr.android.api.singleton.impl;

import fr.android.api.annotation.Singleton;
import fr.android.api.singleton.Formatter;
import fr.android.api.singleton.SaverLoader;


public class SaverLoaderImpl implements SaverLoader {
	@Singleton
	protected Formatter formatter;
	
	@Override
	public String getSaveString(Object o) {
		return formatter.format(o); 
	}

	@Override
	public <T> T loadObject(Class<T> clazz, String savedString) {
		return formatter.parse(clazz, savedString);
	}

}
