package fr.game.engine.fighter.impl;

import java.util.HashSet;
import java.util.Set;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.game.engine.fighter.Army;
import fr.game.engine.fighter.Fighter;

public class ArmyImpl implements Army {
	@Field
	protected Set<Fighter> fighters;

	@Initializer
	public void initialize() {
		fighters = new HashSet<Fighter>();
	}

	@Override
	public Set<Fighter> getFighters() {
		return fighters;
	}

	@Override
	public void addFigther(Fighter fighter) {
		fighters.add(fighter);
	}

	@Override
	public void removeFigther(Fighter fighter) {
		fighters.remove(fighter);
	}

	@Override
	public boolean isFighterPresent(Fighter fighter) {
		return fighters.contains(fighter);
	}

}
