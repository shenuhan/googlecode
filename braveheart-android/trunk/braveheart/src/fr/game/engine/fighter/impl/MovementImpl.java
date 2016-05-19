package fr.game.engine.fighter.impl;

import fr.game.engine.fighter.Fighter;
import fr.game.engine.fighter.Movement;
import fr.game.engine.map.Tile;

public class MovementImpl extends ActionImpl implements Movement {
	protected Tile to;

	@Override
	public Tile getTo() {
		return to;
	}

	@Override
	public void initialize(Fighter fighter, Tile to, float time) {
		this.fighter = fighter;
		this.to = to;
		this.time = time;
	}
}
