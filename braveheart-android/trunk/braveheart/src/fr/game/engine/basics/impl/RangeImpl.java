/**
 * 
 */
package fr.game.engine.basics.impl;

import java.util.Random;

import fr.game.engine.basics.Range;

/**
 * @author AT92015
 *
 */
public class RangeImpl implements Range {
	private float min;
	private float max;

	private Random rand = new Random();

	@Override
	public float getMin() {
		return min;
	}

	@Override
	public void setMin(float min) {
		this.min = min;
	}

	@Override
	public float getMax() {
		return max;
	}

	@Override
	public void setMax(float max) {
		this.max = max;
	}

	@Override
	public float getRandomValue() {
		return min + rand.nextFloat() * (max - min);
	}

	@Override
	public void initialize(float min, float max) {
		this.min = min;
		this.max = max;
	}
	
	@Override
	public String toString() {
		return String.format("[%f-->%f]", this.min, this.max); 
	}

}