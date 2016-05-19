package fr.android.physics.impl;

import fr.android.api.annotation.Field;
import fr.android.physics.Entity;
import fr.android.physics.Point;
import fr.android.physics.Vector;

public class EntityImpl extends FieldObjectImpl implements Entity {
	private Point position;
	private Vector speed;
	
	@Field
	private boolean isGravity;

	@Field
	private boolean isBumpy;

	@Override
	public void initialize(Point position, Vector speed) {
		this.initialize(position, speed, Gravity.No, Bumpy.Yes);	
	}
	
	@Override
	public void initialize(Point position, Vector speed, Gravity gravity) {
		this.initialize(position, speed, Gravity.No, Bumpy.Yes);	
	}


	@Override
	public void initialize(Point position, Vector speed, Gravity gravity,Bumpy bumpy) {
		super.initialize(position);
		this.speed = speed;
		isGravity = gravity == Gravity.Yes;
		isBumpy = bumpy == Bumpy.Yes;
	}


	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public void setPosition(Point position) {
		this.position = position;
	}

	@Override
	public Vector getSpeed() {
		return this.speed;
	}

	@Override
	public void setSpeed(Vector speed) {
		this.speed = speed;
	}
	
	@Override
	public boolean isGravity() {
		return isGravity;
	}

	@Override
	public boolean isBumpy() {
		return isBumpy;
	}
}
