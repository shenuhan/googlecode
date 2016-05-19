package fr.game.engine.fighter.impl;

import fr.android.api.Api;
import fr.game.engine.basics.Range;
import fr.game.engine.fighter.Attack;
import fr.game.engine.fighter.AttackDescriptor;
import fr.game.engine.fighter.Fighter;
import fr.game.engine.fighter.enumeration.AttackType;

public class AttackDescriptorImpl implements AttackDescriptor {
	private AttackType type;

	private Range strength;
	private Range distance;
	private Range splash;
	private Range recoveryTime;
	private Range hitTime;

	@Override
	public void initialize(AttackType type, Range strength, Range distance, Range splash, Range recoveryTime, Range hitTime) {
		this.type = type;
		this.strength = strength;
		this.distance = distance;
		this.splash = splash;
		this.recoveryTime = recoveryTime;
		this.hitTime = hitTime;
	}

	@Override
	public AttackType getType() {
		return type;
	}

	@Override
	public Range getStrength() {
		return strength;
	}

	@Override
	public Range getRecoveryTime() {
		return recoveryTime;
	}

	@Override
	public Range getDistance() {
		return distance;
	}

	@Override
	public Range getSplash() {
		return splash;
	}

	@Override
	public Range getHitTime() {
		return hitTime;
	}

	@Override
	public Attack attack(Fighter fighter, Fighter enemy) {
		return Api.make(Attack.class, this, fighter, enemy, strength.getRandomValue(), recoveryTime.getRandomValue(), hitTime.getRandomValue());
	}
}
