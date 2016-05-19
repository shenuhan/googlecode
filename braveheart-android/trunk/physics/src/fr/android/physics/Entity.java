package fr.android.physics;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;

public interface Entity extends FieldObject {
	public enum Gravity {Yes,No}
	public enum Bumpy {Yes,No}
	
	@Field
	public Vector getSpeed();
	public void setSpeed(Vector speed);

	boolean isBumpy();
	boolean isGravity();

	@Initializer
	void initialize(Point position, Vector speed);
	@Initializer
	void initialize(Point position, Vector speed, Gravity gravity);
	@Initializer
	void initialize(Point position, Vector speed, Gravity gravity, Bumpy bumpy);
}
