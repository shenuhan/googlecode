package fr.android.physics.impl;

import fr.android.api.Api;
import fr.android.physics.EntityField;
import fr.android.physics.FieldObject;
import fr.android.physics.Impact;
import fr.android.physics.Circle;
import fr.android.physics.Vector;

public class ImpactWall implements Impact {
	final private EntityField first;
	final private Circle second;
	private float time;
	private Vector hitWall;
	
	public ImpactWall(EntityField first, Circle second) {
		this.first = first;
		this.second = second;
		this.time = Float.MAX_VALUE;
	}

	@Override
	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public void setHitWall(Vector wall) {
		Api.recycle(hitWall);
		this.hitWall = wall.normalize();
	}

	@Override
	public FieldObject getFirst() {
		return first;
	}

	@Override
	public FieldObject getSecond() {
		return second;
	}

	@Override
	public void applyImpact() {
		float scalar = second.getSpeed().scalar(hitWall);
		Vector hit = hitWall.clone().multiply(scalar).multiply(1.001f);
		second.getSpeed().sub(hit);
		if (second.isBumpy()) {
			second.getSpeed().sub(hit);
		}
		Api.recycle(hit);
	}
}