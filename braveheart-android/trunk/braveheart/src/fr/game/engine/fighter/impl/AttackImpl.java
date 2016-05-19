package fr.game.engine.fighter.impl;

import fr.game.engine.fighter.Attack;
import fr.game.engine.fighter.AttackDescriptor;
import fr.game.engine.fighter.Fighter;

public class AttackImpl extends ActionImpl implements Attack {
	private AttackDescriptor attackDescriptor;
	private Fighter target;
	private float strength;
	private float hitDelay;

	@Override
	public AttackDescriptor getAttackDescriptor() {
		return attackDescriptor;
	}

	@Override
	public Fighter getTarget() {
		return target;
	}

	@Override
	public float getStrength() {
		return strength;
	}

	@Override
	public float getHitDelay() {
		return hitDelay;
	}

	@Override
	public void initialize(AttackDescriptor attackdescriptor, Fighter fighter, Fighter target, float strength, float time, float hitDelay) {
		this.fighter = fighter;
		this.time = time;

		this.attackDescriptor = attackdescriptor;
		this.target = target;
		this.strength = strength;
		this.hitDelay = hitDelay;
	}

}
