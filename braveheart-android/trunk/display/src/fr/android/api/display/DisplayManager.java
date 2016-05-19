package fr.android.api.display;

import org.anddev.andengine.entity.IEntity;

import fr.android.api.annotation.Singleton;

@Singleton
public interface DisplayManager {
	IEntity getDisplay(Object managedObject, Object ... initializerArgs);
	void removeDisplay(Object managedObject);
	Object getCurrentParent();
}
