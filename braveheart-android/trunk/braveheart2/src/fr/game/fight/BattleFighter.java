package fr.game.fight;

import fr.game.basic.FPosition;
import fr.game.basic.Position;
import fr.game.engine.Action;

public class BattleFighter extends Fighter {
	public enum Direction {Left, Right, Up, Down}

	final private Army army;
	private Position position;
	private Action action;
	private Direction direction;
	private int currentHp;
	private int currentMp;
	private float nextTime;


	public BattleFighter(Army army, Fighter fighter, int i, int j, Direction direction) {
		this.army = army;

		setPosition(new Position(i, j));
		setDirection(direction);
		setType(fighter.getType());
		setWeapon(fighter.getWeapon());
		setHp(fighter.getHp());
		setMp(fighter.getMp());
		setCurrentHp(fighter.getHp());
		setCurrentMp(fighter.getMp());
		setSpeed(fighter.getSpeed());
		setPrice(fighter.getPrice());

		setBasicAttack(fighter.getBasicAttack());
		setMagicAttacks(fighter.getMagicAttacks());
		setBasicDefence(fighter.getBasicDefence());
		setMagicDefenses(fighter.getMagicDefenses());
		setName(fighter.getName());

		nextTime = 0;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
	}

	public int getCurrentMp() {
		return currentMp;
	}

	public void setCurrentMp(int currentMp) {
		this.currentMp = currentMp;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
		nextTime += action.duration;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Army getArmy() {
		return army;
	}

	public float getNextTime() {
		return nextTime;
	}

	public FPosition getFPosition() {
		if (action == null || action.destination.equals(action.position))
			return new FPosition(position.i, position.j);

		float percent = (System.currentTimeMillis() - action.startTime)/action.duration/1000f;
		if (percent > 1) percent = 1;
		return new FPosition(action.position.i + (action.destination.i - action.position.i) * percent, action.position.j + (action.destination.j - action.position.j) * percent);
	}
}
