package fr.android.api.singleton;

import android.util.Log;
import fr.android.api.singleton.impl.SingletonManagerImpl;

public abstract class SingletonManager {
	static private SingletonManager instance;
	
	static public synchronized SingletonManager getInstance() {
		if (instance == null) {
			instance = SingletonManagerImpl.getInstance();
			if (instance instanceof SingletonManagerImpl) {
				((SingletonManagerImpl) instance).initialize();
			}
		}
		return instance;
	}

	public static void stop() {
		if (instance instanceof SingletonManagerImpl) {
			try {
				((SingletonManagerImpl) instance).dispose();
			} catch (Throwable e) {
				Log.d("Api", "On stop shit can happened");
			}
		}
		instance = null;
	}
	
	public abstract <T> T getSingleton(Class<T> clazz);
}
