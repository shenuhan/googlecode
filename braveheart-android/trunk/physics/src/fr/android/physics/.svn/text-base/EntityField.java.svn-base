package fr.android.physics;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Singleton;
import fr.android.api.event.Event;

@Singleton
public interface EntityField extends FieldObject {
	@Field
	float getLength();
	void setLength(float length);

	@Field
	float getHeight();
	void setHeight(float height);

	@Initializer
	void initilialize();
	
	void addEntity(Entity entity);
	class AddEntityEvent extends Event<Entity> {};
}
