package fr.game.engine.fighter;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;

@Managed
public interface Weapon {
	public enum WeaponType {
		Sword,
		Axe,
		Spear,
		Bow,
		Staff
	}

	@Field
	WeaponType getType();

	@Initializer
	void initialize(WeaponType type);
}
