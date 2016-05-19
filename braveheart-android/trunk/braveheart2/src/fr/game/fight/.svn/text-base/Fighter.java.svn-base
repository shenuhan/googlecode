package fr.game.fight;

import java.util.List;

import fr.game.fight.BattleFighter.Direction;

public class Fighter {
	static public enum Type {Warrior,Wizzard,Dragon,Healer,Orc}
	static public enum Weapon {Sword}

	private Type type;
	private Weapon weapon;
	private int hp;
	private int mp;
	private int speed;
	private int price;

	private Attack basicAttack;
	private List<Attack> magicAttacks;
	private Defense basicDefence;
	private List<Defense> magicDefenses;

	private String name;

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getMp() {
		return mp;
	}
	public void setMp(int mp) {
		this.mp = mp;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public Attack getBasicAttack() {
		return basicAttack;
	}
	public void setBasicAttack(Attack basicAttack) {
		this.basicAttack = basicAttack;
	}
	public List<Attack> getMagicAttacks() {
		return magicAttacks;
	}
	public void setMagicAttacks(List<Attack> magicAttacks) {
		this.magicAttacks = magicAttacks;
	}
	public Defense getBasicDefence() {
		return basicDefence;
	}
	public void setBasicDefence(Defense basicDefence) {
		this.basicDefence = basicDefence;
	}
	public List<Defense> getMagicDefenses() {
		return magicDefenses;
	}
	public void setMagicDefenses(List<Defense> magicDefenses) {
		this.magicDefenses = magicDefenses;
	}

	public BattleFighter instanciate(Army army, int i, int j, Direction direction) {
		BattleFighter fighter = new BattleFighter(army, this, i,j,direction);
		return fighter;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
