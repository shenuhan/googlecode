package fr.game.engine;

import java.util.Iterator;
import java.util.Set;

import android.util.Log;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Listen;
import fr.android.api.annotation.Managed;
import fr.android.api.annotation.Singleton;
import fr.game.engine.fighter.Army;
import fr.game.engine.fighter.Fighter;
import fr.game.engine.map.BattleField;
import fr.game.engine.map.Tile;
import fr.game.example.ArmySingleton;

@Managed
public class BattleManager {
	private BattleField battlefield;

	@Singleton
	ArmySingleton army;

	@Initializer
	public void initialize(BattleField battlefield) {
		this.battlefield = battlefield;
	}

	@Listen(senderClass=Fighter.class, event="ready")
	public void ready(Object sender, Fighter fighter) {
		Tile t = battlefield.getTile(fighter);
		{
			if (t == null) {
				Log.e("Problem", "This fighter : " + fighter.toString() + " is dead but ready anyway ...");
			}
		}
		Fighter enemy = findEnemy(fighter);
		if (enemy != null) {
			Tile p = battlefield.getTile(enemy);

			if (battlefield.getDistance(p, battlefield.getTile(fighter)) == 1) {
				battlefield.checkOrientation(fighter, p);
				fighter.attack(enemy);
				return;
			} else {
				Set<Tile> tiles = battlefield.getDistantTiles(battlefield.getTile(fighter).getPosition(), 1);
				Iterator<Tile> it = tiles.iterator();
				int min = Integer.MAX_VALUE;
				Tile tile = null;
				while(it.hasNext()) {
					Tile next = it.next();
					if (next.getFighter() == null) {
						int distance = battlefield.getDistance(p, next);
						if (distance < min) {
							min = distance;
							tile = next;
						}
					}
				}
				if (tile != null) {
					battlefield.move(fighter, tile);
					return;
				}
			}
		}
		fighter.idle(1000);
	}

	private Fighter findEnemy(Fighter fighter) {
		Army enemies;
		if (army.getEvilArmy().isFighterPresent(fighter)) {
			enemies = army.getGoodArmy();
		} else {
			enemies = army.getEvilArmy();
		}
		Tile p = battlefield.getTile(fighter);

		Fighter enemy = null;
		int min = Integer.MAX_VALUE;
		for (Fighter f : enemies.getFighters()) {
			if (battlefield.getTile(f) != null) {
				int distance = battlefield.getDistance(battlefield.getTile(f),p);
				if (distance < min) {
					enemy = f;
					min = distance;
				}
			}
		}
		return enemy;
	}
}
