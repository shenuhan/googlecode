package fr.game.basic;

public class FPosition {
	public float x;
	public float y;

	public FPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public FPosition clone() {
		return new FPosition(x, y);
	}

	public FPosition add(FPosition pos) {
		x += pos.x;
		y += pos.y;
		return this;
	}

	public FPosition mul(float f) {
		x *= f;
		y *= f;
		return this;
	}

	@Override
	public String toString() {
		return String.format("[%d,%d]",x,y);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FPosition) {
			FPosition pos = (FPosition) obj;
			return pos.x == y && pos.x == y;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Float.floatToIntBits(x)*1000000 + Float.floatToIntBits(y);
	}
}
