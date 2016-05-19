package fr.game.engine.map.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import android.util.Log;
import fr.android.api.Api;
import fr.android.api.annotation.Field;
import fr.android.api.annotation.Listen.Priority;
import fr.android.api.annotation.Singleton;
import fr.android.api.event.Event;
import fr.android.api.event.EventProcessor;
import fr.android.api.singleton.Disposable;
import fr.android.api.util.Logger;
import fr.game.engine.basics.Point;
import fr.game.engine.fighter.Army;
import fr.game.engine.fighter.Fighter;
import fr.game.engine.fighter.Fighter.Orientation;
import fr.game.engine.fighter.Movement;
import fr.game.engine.map.BattleField;
import fr.game.engine.map.Tile;
import fr.game.engine.singleton.TimeMaster;

/**
 * @author AT92015
 *	BattleField is like that :
 *	0   X X X X X X X
 *	1  X X X X X X X
 *	2   X X X X X X X
 *	3  X X X X X X X
 */
public class BattleFieldImpl implements BattleField, Disposable {
	@Singleton
	TimeMaster timeMaster;

	private int height;
	private int width;

	@Field
	protected Tile[][] tiles;

	@Field
	protected Map<Fighter,Tile> fighters;

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void initialize(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width][height];
		for (int i = 0 ; i < width; i++) {
			for (int j = 0 ; j < height; j++) {
				tiles[i][j] = Api.make(Tile.class, Api.make(Point.class, i, j));
			}
		}
		fighters = new HashMap<Fighter,Tile>();
	}

	@Override
	public void dispose() {
		if (tiles != null) {
			while(fighters.size() > 0) {
				remove(fighters.keySet().iterator().next());
			}
			for (int i = 0 ; i < width; i++) {
				for (int j = 0 ; j < height; j++) {
					Api.destroy(tiles[i][j]);
				}
			}
		}
	}

	@Override
	public Set<Tile> getDistantTiles(Point point, int distance) {
		Set<Tile> res = new HashSet<Tile>();
		getDistantTiles(res, point, distance);

		if (distance > 0) {
			Set<Tile> sub = new HashSet<Tile>();
			getDistantTiles(sub, point, distance - 1);
			res.removeAll(sub);
		}
		return res;
	}

	private void getDistantTiles(Set<Tile> res, Point point, int distance) {
		if (point.getX() < 0 || point.getX() >= width || point.getY() < 0 || point.getY() >= height) {
			return;
		}

		if (!res.add(tiles[point.getX()][point.getY()])) {
			return;
		}

		if (distance > 0) {
			getDistantTiles(res, point.relativePoint(0, 1), distance - 1);
			getDistantTiles(res, point.relativePoint(0, -1), distance - 1);
			getDistantTiles(res, point.relativePoint(1, 0), distance - 1);
			getDistantTiles(res, point.relativePoint(-1, 0), distance - 1);
			if (0 == (point.getY() % 2)) {
				getDistantTiles(res, point.relativePoint(1, 1), distance - 1);
				getDistantTiles(res, point.relativePoint(1, -1), distance - 1);
			} else {
				getDistantTiles(res, point.relativePoint(-1, -1), distance - 1);
				getDistantTiles(res, point.relativePoint(-1, 1), distance - 1);
			}
		}
	}

	@Override
	public int getDistance(Tile tileA,Tile tileB) {
		int yDistance = Math.abs(tileA.getPosition().getY() - tileB.getPosition().getY());
		int xDistance = (Math.abs( 2 * tileA.getPosition().getX() - tileA.getPosition().getY() % 2 - 2 * tileB.getPosition().getX() + tileB.getPosition().getY() % 2) - yDistance)/2;
		return xDistance < 0 ? yDistance : yDistance + xDistance;
	}

	@Override
	public void move(Fighter fighter, Tile to) {
		Point current = getTile(fighter).getPosition();
		Tile from = tiles[current.getX()][current.getY()];
		from.removeFighter();
		to.setFighter(fighter);

		float speed = fighter.getSpeed().getRandomValue() / 1000f;
		float time = getDistance(from,to) / speed;

		checkOrientation(fighter, to);

		fighter.idle((long)time);
		fighters.put(fighter, to);

		event.raise(this,Api.make(Movement.class, fighter, to, time));
	}

	@Override
	public void checkOrientation(Fighter fighter, Tile to) {
		Point current = getTile(fighter).getPosition();
		Tile from = tiles[current.getX()][current.getY()];

		if (from.getPosition().getY() % 2 == 0 ) {
			if (from.getPosition().getX() >= to.getPosition().getX()) {
				fighter.setOrientation(Fighter.Orientation.Left);
			} else {
				fighter.setOrientation(Fighter.Orientation.Right);
			}
		} else {
			if (from.getPosition().getX() <= to.getPosition().getX()) {
				fighter.setOrientation(Fighter.Orientation.Right);
			} else {
				fighter.setOrientation(Fighter.Orientation.Left);
			}
		}
	}

	private Event<Movement> event = new Event<Movement>();
	@Override
	public Event<Movement> moved() {
		return event;
	}

	private EventProcessor<Fighter> die = new EventProcessor<Fighter>() {
		@Override
		public Priority getPriority() {
			return Priority.Medium;
		}
		@Override
		public void event(Object sender, Fighter message) {
			Log.d("Problem", "This fighter : " + message.toString() + " is dead !!!");
			remove(message);
			message.died().unsubscribe(this);
		}
	};

	@Override
	public void add(final Fighter fighter, Point position) {
		fighters.put(fighter,tiles[position.getX()][position.getY()]);
		tiles[position.getX()][position.getY()].setFighter(fighter);

		fighter.died().subscribe(die);
		timeMaster.time().subscribe(fighter);
	}

	@Override
	public void remove(Fighter fighter) {
		Tile t = fighters.get(fighter);
		if (t == null) {
			return;
		}
		tiles[t.getPosition().getX()][t.getPosition().getY()].setFighter(null);
		timeMaster.time().unsubscribe(fighter);
		fighters.remove(fighter);
	}

	@Override
	public void add(Army army, Side side) {
		if (army.getFighters().size() == 0) {
			return;
		}

		if (army.getFighters().size() > 3*height) {
			throw Logger.t("Battlefield", "Too Big an army for this battlefield");
		}

		Random r = new Random();
		int add = side == Side.Right ? width - 3 : 0;
		for (Fighter fighter : army.getFighters()) {
			int i = r.nextInt(3);
			int j = r.nextInt(height);
			while (tiles[i+add][j].getFighter() != null) {
				j = j + 1;
				if (j == height) {
					j = 0;
					i = i + 1 % 3;
				}
			}
			add(fighter,tiles[i+add][j].getPosition());
			fighter.setOrientation(side == Side.Left ? Orientation.Right : Orientation.Left);
		}
	}

	@Override
	public Tile getTile(Fighter fighter) {
		return fighters.get(fighter);
	}
}
