package fr.game.engine.map.impl;

import android.util.Log;
import fr.android.api.Api;
import fr.android.api.annotation.Singleton;
import fr.android.api.event.Event;
import fr.android.api.singleton.Disposable;
import fr.android.api.singleton.EventManager;
import fr.game.engine.basics.Point;
import fr.game.engine.fighter.Fighter;
import fr.game.engine.map.Tile;

public class TileImpl implements Tile, Disposable {
	private Point position;
	private Fighter fighter;

	@Singleton
	protected EventManager eventManager;

	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public Fighter getFighter() {
		return fighter;
	}

	@Override
	public void setFighter(Fighter fighter) {
		if (fighter == null) {
			removeFighter();
		} else if (fighter != this.fighter) {
			if (this.fighter != null) {
				removeFighter();
			}
			this.fighter = fighter;
		}
	}

	@Override
	public void removeFighter() {
		if (fighter != null) {
			Fighter f = fighter;
			fighter = null;
			eventLeave.raise(this, f);
		} else {
			Log.e("Pierre", this.toString() + " : Remove d'un fighter null !");
		}
	}

	@Override
	public void initialize(Point position) {
		this.position = position;
		fighter = null;
	}

	@Override
	public String toString() {
		return position.toString();
	}

	private Event<Fighter> eventEnter = new Event<Fighter>();
	private Event<Fighter> eventLeave = new Event<Fighter>();

	@Override
	public Event<Fighter> enter() {
		return eventEnter;
	}

	@Override
	public Event<Fighter> leave() {
		return eventLeave;
	}

	@Override
	public void dispose() {
		if (fighter != null) {
			Api.destroy(fighter);
		}
	}
}
