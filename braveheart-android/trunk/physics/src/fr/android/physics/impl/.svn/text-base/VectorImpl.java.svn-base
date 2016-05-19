package fr.android.physics.impl;

import android.util.FloatMath;
import fr.android.api.Api;
import fr.android.physics.Vector;

public class VectorImpl implements Vector {
	private float x;
	private float y;

	private float length;

	public VectorImpl() {
	}
	
	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
		this.length = -1;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
		this.length = -1;
	}
	
	@Override
	public Vector clone() {
		Vector res = Api.make(Vector.class);
		res.setX(x);
		res.setY(y);
		return res;
	}

	@Override
	public Vector multiply(float val) {
		this.x *= val; 
		this.y *= val;
		this.length = -1;
		return this;
	}

	@Override
	public Vector add(Vector vector) {
		this.x += vector.getX(); 
		this.y += vector.getY();
		this.length = -1;
		return this;
	}

	@Override
	public Vector sub(Vector vector) {
		this.x -= vector.getX(); 
		this.y -= vector.getY(); 
		this.length = -1;
		return this;
	}

	@Override
	public float scalar(Vector vector) {
		return this.x * vector.getX() + this.y * vector.getY();  
	}

	@Override
	public float getSquareLength() {
		return x*x+y*y;
	}
	
	@Override
	public float getLength() {
		if (length < 0) {
			length = FloatMath.sqrt(getSquareLength());
		}
		return length;
	}

	@Override
	public Vector normalize() {
		if (x == 0 && y == 0) {
			x = 0;
			y = 1;
		} else {
			x = x / getLength();
			y = y / getLength();
		}
		length = 1;
		return this;
	}

	@Override
	public Vector perpendicular() {
		float d = this.x;
		this.x = this.y;
		this.y = -d;
		return this;
	}

	@Override
	public Vector rotate(float angle) {
		float d = this.x;
		float cos = FloatMath.cos(angle);
		float sin = FloatMath.sin(angle);
		this.x = cos * this.x - sin * this.y;
		this.y = sin * d + cos * this.y;
		return this;
	}

	@Override
	public void init(float x, float y) {
		this.x = x;
		this.y = y;
		this.length = -1;
	}
}
