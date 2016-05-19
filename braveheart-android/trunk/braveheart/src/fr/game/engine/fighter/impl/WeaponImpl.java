package fr.game.engine.fighter.impl;

import fr.game.engine.fighter.Weapon;

public class WeaponImpl implements Weapon {
	private WeaponType type;

	@Override
	public WeaponType getType() {
		return type;
	}

	@Override
	public void initialize(WeaponType type) {
		this.type = type;
	}

}
