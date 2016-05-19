package fr.game.engine.fighter.impl;

import fr.game.engine.fighter.Action;
import fr.game.engine.fighter.Fighter;
import fr.game.engine.map.Tile;

abstract public class ActionImpl implements Action {
	protected Fighter fighter;
	protected Tile position;
	protected float time;

	@Override
	public Fighter getFighter() {
		return fighter;
	}

	@Override
	public float getTime() {
		return time;
	}

}
