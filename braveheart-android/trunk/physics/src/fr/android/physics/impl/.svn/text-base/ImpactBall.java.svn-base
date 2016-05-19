package fr.android.physics.impl;

import fr.android.api.Api;
import fr.android.physics.FieldObject;
import fr.android.physics.Impact;
import fr.android.physics.Circle;
import fr.android.physics.Vector;

public class ImpactBall implements Impact {
	final private Circle first;
	final private Circle second;
	final private float time;
	final private Vector firstToSecond;
	
	public ImpactBall(Circle first, Circle second, float time) {
		this.first = first;
		this.second = second;
		this.time = time;
		if (first == null || second == null) {
			firstToSecond = null;
		} else {
			firstToSecond = first.getPosition().to(second.getPosition()).normalize();
		}
	}

	@Override
	public float getTime() {
		return time;
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
		if (firstToSecond == null) {
			return;
		}
		
		Vector firstSpeed = firstToSecond.clone().multiply(firstToSecond.scalar(first.getSpeed())); 
		Vector secondSpeed = firstToSecond.clone().multiply(firstToSecond.scalar(second.getSpeed()));
		
		first.getSpeed().sub(firstSpeed);
		second.getSpeed().sub(secondSpeed);
		
		if (first.isBumpy()) {
			if (second.isBumpy()) {
				first.getSpeed().add(secondSpeed);
				second.getSpeed().add(firstSpeed);
			} else {
				first.getSpeed().sub(firstSpeed);
				second.getSpeed().sub(secondSpeed.multiply(0.01f));
			}
		} else {
			if (second.isBumpy()) {
				first.getSpeed().sub(firstSpeed.multiply(0.01f));
				second.getSpeed().sub(secondSpeed);
			} else {
				first.getSpeed().add(secondSpeed.multiply(0.01f));
				second.getSpeed().add(firstSpeed.multiply(0.01f));
			}
		}
		
		Api.recycle(firstSpeed);
		Api.recycle(secondSpeed);
	}
}
