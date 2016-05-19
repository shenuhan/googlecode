/**
 * 
 */
package fr.game.engine.basics.impl;

import fr.game.engine.basics.Stats;

/**
 * @author AT92015
 *
 */
public class StatsImpl implements Stats {
	private float value;
	private float max;

	@Override
	public float getValue() {
		return value;
	}

	@Override
	public void setValue(float value) {
		this.value = max > value ? value : max;
	}

	@Override
	public double add(float offset) {
		setValue(value + offset);
		return getValue();
	}

	@Override
	public double sub(float offset) {
		setValue(value - offset);
		return getValue();
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
	public void initialize(float max) {
		initialize(max, max);
	}

	@Override
	public void initialize(float max, float value) {
		this.value = value;
		this.max = max;
	}
	
	
	@Override
	public String toString() {
		return String.format("[%f/%f]", this.value, this.max); 
	}
}
