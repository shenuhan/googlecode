package fr.android.physics;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;

public interface FieldObject {
	@Field
	public Point getPosition();
	public void setPosition(Point position);
	
	@Initializer
	void initialize(Point position);
}
