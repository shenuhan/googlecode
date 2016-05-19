package fr.game.engine.fighter;

import java.util.Collection;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;
import fr.android.api.event.Event;
import fr.android.api.event.EventProcessor;
import fr.game.engine.basics.Range;
import fr.game.engine.basics.Stats;
import fr.game.engine.fighter.enumeration.FighterType;

@Managed
public interface Fighter extends EventProcessor<Long> {
	enum Orientation {Left, Right}

	@Initializer
	void initialize(FighterType type, Weapon weapon, Stats hp, Range speed);

	@Field
	Stats getHp();

	// tile per second
	@Field
	Range getSpeed();

	@Field
	FighterType getType();

	@Field
	Weapon getWeapon();

	@Field
	Collection<AttackDescriptor> getAttacks();
	void setAttacks(Collection<AttackDescriptor> attacks);

	@Field
	Orientation getOrientation();
	void setOrientation(Orientation  orientation);

	void hit(Attack attack);
	void idle(long time);

	Event<Fighter> died();
	Event<Fighter> ready();
	Event<Orientation> changeOrientation();
	Event<Attack> attacking();
	Event<Attack> hit();

	void attack(Fighter enemy);
}
