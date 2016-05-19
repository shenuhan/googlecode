package fr.game.fight;

import java.util.List;

public class Defense {
	static public enum Type {AimOne, AimMany, Radiate, AimPlace, AimSelf}
	static public enum TargetType { Flying, Ground, Both}

	private Type type;
	private TargetType targetType;

	private Range distance;
	private Range strength;
	private int mp;
	private int splash;
	private int speed;

	private List<DefenseCapacity> capacities;

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public TargetType getTargetType() {
		return targetType;
	}
	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}
	public Range getDistance() {
		return distance;
	}
	public void setDistance(Range distance) {
		this.distance = distance;
	}
	public Range getStrength() {
		return strength;
	}
	public void setStrength(Range strength) {
		this.strength = strength;
	}
	public int getMp() {
		return mp;
	}
	public void setMp(int mp) {
		this.mp = mp;
	}
	public int getSplash() {
		return splash;
	}
	public void setSplash(int splash) {
		this.splash = splash;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public List<DefenseCapacity> getCapacities() {
		return capacities;
	}
	public void setCapacities(List<DefenseCapacity> capacities) {
		this.capacities = capacities;
	}
}
