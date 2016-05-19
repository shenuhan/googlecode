package fr.android.physics.impl;

import fr.android.api.Api;
import fr.android.physics.Point;
import fr.android.physics.Vector;

public class PointImpl implements Point {
	private float x;
	private float y;

	public PointImpl() {
	}
	
	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public Point move(Vector direction) {
		this.x += direction.getX();
		this.y += direction.getY();
		return this;
	}

	@Override
	public Vector to(Point b) {
		Vector res = Api.make(Vector.class);
		res.setX(b.getX() - x);
		res.setY(b.getY() - y);
		return res;
	}

	@Override
	public Point clone() {
		Point res = Api.make(Point.class);
		res.setX(x);
		res.setY(y);
		return res;
	}

	@Override
	public void init(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
