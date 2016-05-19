package fr.android.physics;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;

@Managed
public interface Vector {
	@Field
	public float getX();
	public void setX(float x);

	@Field
	public float getY();
	public void setY(float y);
	
	@Initializer
	void init(float x, float y);
	
	Vector clone();

	Vector multiply(float val);
	Vector add(Vector vector);
	Vector sub(Vector vector);
	
	float getSquareLength();
	float getLength();

	float scalar(Vector vector);
	Vector normalize();
	Vector perpendicular();
	Vector rotate(float angle);
}
