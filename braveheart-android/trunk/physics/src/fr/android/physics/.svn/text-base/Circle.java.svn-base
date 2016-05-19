package fr.android.physics;

import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;

@Managed
public interface Circle extends Entity {
	float getRadius();
	
	@Initializer
	void initialize(Point position, Vector speed, float radius, Gravity gravity, Bumpy bumpy);

	void move(Vector movement);
}
