package fr.android.physics.impl;

import fr.android.api.annotation.Field;
import fr.android.physics.Circle;
import fr.android.physics.Point;
import fr.android.physics.Vector;

public class CircleImpl extends EntityImpl implements Circle {
	@Field
	private float radius;
	
	@Override
	public float getRadius() {
		return radius;
	}

	@Override
	public void initialize(Point position, Vector speed, float radius, Gravity gravity, Bumpy bumpy) {
		super.initialize(position,speed,gravity,bumpy);
		this.radius = radius;
	}

	@Override
	public void move(Vector movement) {
		getPosition().move(movement);
	}
}
