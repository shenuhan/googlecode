package fr.game.engine.basics.impl;

import fr.android.api.Api;
import fr.game.engine.basics.Point;

public class PointImpl implements Point {
	private int x;
	private int y;

	public PointImpl() {
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public Point relativePoint(int offsetX, int offsetY) {
		Point res = Api.make(Point.class);
		res.setX(x + offsetX);
		res.setY(y + offsetY);
		return res;
	}

	@Override
	public void initialize(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return String.format("[%d,%d]", this.x, this.y);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
			Point p = (Point) o;
			return x == p.getX() && y == p.getY();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return x + 10000000* y;
	}
}
