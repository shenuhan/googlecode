package fr.game.fight;

public class AttackCapacity {
	public enum Type { SLOW, POISON, FREEZE, ARMOR, CRITICAL }

	private Type type;
	private int strength;
	private int probability;
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getProbability() {
		return probability;
	}
	public void setProbability(int probability) {
		this.probability = probability;
	}
}
