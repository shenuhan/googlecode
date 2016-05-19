package fr.android.physics;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;

@Managed
public interface Point {
	@Field
	float getX();
	void setX(float x);

	@Field
	float getY();
	void setY(float y);
	
	@Initializer
	void init(float x, float y);
	
	Point move(Vector direction);
	Vector to(Point b);
	Point clone();
}
