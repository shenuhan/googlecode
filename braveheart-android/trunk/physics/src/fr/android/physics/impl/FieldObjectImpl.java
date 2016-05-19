package fr.android.physics.impl;

import fr.android.physics.FieldObject;
import fr.android.physics.Point;

public class FieldObjectImpl implements FieldObject {
	private Point position;

	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public void setPosition(Point position) {
		this.position = position;
	}

	@Override
	public void initialize(Point position) {
		setPosition(position);
	}
}
