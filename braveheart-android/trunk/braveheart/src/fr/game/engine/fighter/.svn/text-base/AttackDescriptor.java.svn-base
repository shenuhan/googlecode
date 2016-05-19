package fr.game.engine.fighter;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;
import fr.game.engine.basics.Range;
import fr.game.engine.fighter.enumeration.AttackType;

@Managed
public interface AttackDescriptor {
	@Field
	AttackType getType();
	@Field
	Range getStrength();
	@Field
	Range getDistance();
	@Field
	Range getSplash();
	@Field
	Range getRecoveryTime();
	@Field
	Range getHitTime();

	@Initializer
	void initialize(AttackType type, Range strength, Range distance, Range splash, Range recoveryTime, Range hitTime );

	Attack attack(Fighter fighter, Fighter enemy);
}
