package fr.game.engine.fighter;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;

@Managed
public interface Attack extends Action {
	@Field
	AttackDescriptor getAttackDescriptor();
	@Field
	Fighter getTarget();
	@Field
	float getStrength();
	@Field
	float getHitDelay();

	@Initializer
	void initialize(AttackDescriptor attackdescriptor, Fighter fighter, Fighter target, float strength, float time, float hitDelay);
}
