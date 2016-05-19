package fr.game.engine.map;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;
import fr.android.api.event.Event;
import fr.game.engine.basics.Point;
import fr.game.engine.fighter.Fighter;

@Managed
public interface Tile {
	@Field
	Point getPosition();

	@Field
	Fighter getFighter();
	void setFighter(Fighter fighter);

	@Initializer
	void initialize(Point position);

	void removeFighter();

	Event<Fighter> enter();
	Event<Fighter> leave();
}
