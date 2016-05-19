package fr.game.engine.map;

import java.util.Set;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;
import fr.android.api.event.Event;
import fr.game.engine.basics.Point;
import fr.game.engine.fighter.Army;
import fr.game.engine.fighter.Fighter;
import fr.game.engine.fighter.Movement;

/**
 * @author AT92015
 *	BattleField is like that :
 *	0   X X X X X X X
 *	1  X X X X X X X
 *	2   X X X X X X X
 *	3  X X X X X X X
 */

@Managed
public interface BattleField {
	enum Side {Right, Left}

	@Field
	int getHeight();
	@Field
	int getWidth();

	@Initializer
	void initialize(int width, int length);

	int getDistance(Tile tileA, Tile tileB);
	Set<Tile> getDistantTiles(Point point, int distance);

	void add(Fighter fighter, Point position);
	void remove(Fighter fighter);
	void add(Army army, Side side);

	void move(Fighter fighter, Tile to);
	void checkOrientation(Fighter fighter, Tile to);
	Tile getTile(Fighter fighter);

	Event<Movement> moved();
}