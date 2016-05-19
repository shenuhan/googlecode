package fr.game.engine.fighter.impl;

import java.util.ArrayList;
import java.util.Collection;

import fr.android.api.Api;
import fr.android.api.annotation.Field;
import fr.android.api.annotation.Listen.Priority;
import fr.android.api.event.Event;
import fr.android.api.util.Logger;
import fr.game.engine.basics.Range;
import fr.game.engine.basics.Stats;
import fr.game.engine.fighter.Attack;
import fr.game.engine.fighter.AttackDescriptor;
import fr.game.engine.fighter.Fighter;
import fr.game.engine.fighter.Weapon;
import fr.game.engine.fighter.enumeration.AttackType;
import fr.game.engine.fighter.enumeration.FighterType;

public class FighterImpl implements Fighter {
	protected  Stats hp;
	protected  Range speed;
	protected  FighterType type;
	protected  Weapon weapon;
	protected  Orientation orientation;

	private Collection<AttackDescriptor> attacks;

	private Event<Fighter> dieEvent = new Event<Fighter>();
	private Event<Fighter> readyEvent = new Event<Fighter>();
	private Event<Orientation> orientationEvent = new Event<Orientation>();
	private Event<Attack> hitEvent = new Event<Attack>();
	private Event<Attack> attackEvent = new Event<Attack>();

	@Field
	private long idleRemaining;

	@Override
	public void initialize(FighterType type, Weapon weapon, Stats hp, Range speed) {
		this.type = type;
		this.hp = hp;
		this.speed = speed;
		this.weapon = weapon;

		attacks = new ArrayList<AttackDescriptor>();
		attacks.add(Api.make(AttackDescriptor.class,
				AttackType.Basic,
				Api.make(Range.class,10f,10f),        //strength
				Api.make(Range.class,0.5f,1.5f),      //distance
				Api.make(Range.class,0f,0f),          //splash
				Api.make(Range.class,2000f,2000f),	  //recoveryTime
				Api.make(Range.class,900f,900f)));  //hitTime
	}

	@Override
	public Stats getHp() {
		return hp;
	}

	@Override
	public Range getSpeed() {
		return speed;
	}

	@Override
	public FighterType getType() {
		return type;
	}

	@Override
	public Weapon getWeapon() {
		return weapon;
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public void setOrientation(Orientation orientation) {
		if (this.orientation != orientation) {
			this.orientation = orientation;
			orientationEvent.raise(this, this.orientation);
		}
	}

	@Override
	public Collection<AttackDescriptor> getAttacks() {
		return attacks;
	}

	@Override
	public void setAttacks(Collection<AttackDescriptor> attacks) {
		this.attacks = attacks;
	}

	@Override
	public void hit(Attack attack) {
		if (getHp().sub(attack.getStrength()) < 0) {
			dieEvent.raise(this,this);
		} else {
			hitEvent.raise(this, attack);
		}
	}

	@Override
	public void idle(long time) {
		idleRemaining = idleRemaining > time ? idleRemaining : time;
	}

	@Override
	public String toString() {
		return this.getType().toString() + String.format("[%s]", super.toString().substring(super.toString().length()-4));
	}

	@Override
	public Event<Fighter> died() {
		return dieEvent;
	}

	@Override
	public Event<Fighter> ready() {
		return readyEvent;
	}

	@Override
	public Event<Orientation> changeOrientation() {
		return orientationEvent;
	}

	@Override
	public Event<Attack> attacking() {
		return attackEvent;
	}

	@Override
	public Event<Attack> hit() {
		return hitEvent;
	}

	@Override
	public Priority getPriority() {
		return Priority.Medium;
	}

	@Override
	public void event(Object sender, Long time) {
		idleRemaining -= time;
		if (nextAttack != null) {
			idleAttack -= time;
			if (idleAttack < 0) {
				nextAttack.getTarget().hit(nextAttack);
				nextAttack = null;
			}
		}
		if (idleRemaining < 0) {
			readyEvent.raise(this,this);
		}
	}

	private Attack nextAttack = null;
	private float idleAttack;

	@Override
	public void attack(Fighter enemy) {
		Attack attack = attacks.iterator().next().attack(this, enemy);

		nextAttack = attack;
		idleAttack = attack.getHitDelay();
		idle((int)attack.getTime());

		Logger.d("Fighter", this.toString() + " attacks " + enemy.toString());

		attackEvent.raise(this, attack);
	}
}
