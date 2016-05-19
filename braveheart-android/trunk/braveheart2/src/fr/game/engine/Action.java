package fr.game.engine;

import fr.game.basic.Position;
import fr.game.fight.Attack;
import fr.game.fight.BattleFighter;
import fr.game.fight.BattleFighter.Direction;
import fr.game.fight.Defense;

public class Action {
	public enum Type {Idle, Walk, Attack, MagicAttack, Defend}

	public final long startTime;
	public BattleFighter fighter;
	public Type type;

	public Attack attack;
	public Defense defense;
	public BattleFighter target;
	public Position destination;
	public Position position;
	public float duration;

	public Action() {
		startTime = System.currentTimeMillis();
	}

	public Direction getMovementDirection() {
		if (destination.i > position.i) {
			if (destination.j >= position.j) {
				return Direction.Right;
			}
			return Direction.Down;
		}
		if (destination.j > position.j) {
			return Direction.Up;
		} else {
			return Direction.Left;
		}
	}

	public Direction getAttackDirection() {
		if (target.getFPosition().x > position.i) {
			if (target.getFPosition().y >= position.j) {
				return Direction.Right;
			}
			return Direction.Left;
		}
		if (target.getFPosition().x > position.j) {
			return Direction.Right;
		} else {
			return Direction.Left;
		}
	}
}
