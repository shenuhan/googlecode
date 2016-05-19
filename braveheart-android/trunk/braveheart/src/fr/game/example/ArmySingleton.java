package fr.game.example;

import fr.android.api.Api;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Singleton;
import fr.game.engine.basics.Range;
import fr.game.engine.basics.Stats;
import fr.game.engine.fighter.Army;
import fr.game.engine.fighter.Fighter;
import fr.game.engine.fighter.Weapon;
import fr.game.engine.fighter.Weapon.WeaponType;
import fr.game.engine.fighter.enumeration.FighterType;

@Singleton
public class ArmySingleton {
	private Army army;
	private Army good;
	private Army evil;

	@Initializer
	public void initialize() {
		army = Api.make(Army.class);
		for(FighterType type : FighterType.values()) {
			army.addFigther(Api.make(Fighter.class, type, Api.make(Weapon.class, WeaponType.Sword), Api.make(Stats.class, 100f), Api.make(Range.class,1f,1f)));
		}
		good = Api.make(Army.class);
		//		for(FighterType type : new FighterType[] {FighterType.Warrior10,FighterType.Warrior11,FighterType.Warrior1}) {
		for(FighterType type : new FighterType[] {FighterType.Warrior10}) {
			good.addFigther(Api.make(Fighter.class, type, Api.make(Weapon.class, WeaponType.Sword), Api.make(Stats.class, 100f), Api.make(Range.class,1f,1f)));
		}
		evil = Api.make(Army.class);
		for(FighterType type : new FighterType[] {FighterType.Baddy1}) {
			evil.addFigther(Api.make(Fighter.class, type, Api.make(Weapon.class, WeaponType.Sword), Api.make(Stats.class, 100f), Api.make(Range.class,1f,1f)));
		}
	}

	public Army getFullArmy() {
		return army;
	}

	public Army getGoodArmy() {
		return good;
	}

	public Army getEvilArmy() {
		return evil;
	}
}
