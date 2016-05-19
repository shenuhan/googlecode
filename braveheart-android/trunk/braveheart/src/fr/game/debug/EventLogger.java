package fr.game.debug;

import android.util.Log;
import fr.android.api.Api;
import fr.android.api.annotation.Listen;
import fr.android.api.annotation.Managed;
import fr.game.engine.fighter.Fighter;
import fr.game.engine.fighter.Movement;
import fr.game.engine.map.BattleField;
import fr.game.engine.map.Tile;

@Managed
public class EventLogger {
	private static EventLogger instance;
	static public synchronized void start() {
		if (instance == null) {
			instance = Api.make(EventLogger.class);
		}
	}

	static public synchronized void stop() {
		if (instance != null) {
			Api.recycle(instance);
			instance = null;
		}
	}

	@Listen(async=true,senderClass=BattleField.class, event="moved")
	public void move(BattleField battleField, Movement movement) {
		Tile tileA = battleField.getTile(movement.getFighter());
		Tile tileB = movement.getTo();

		int yDistance = Math.abs(tileA.getPosition().getY() - tileB.getPosition().getY());
		int xDistance = (Math.abs( 2 * tileA.getPosition().getX() - tileA.getPosition().getY() % 2 - 2 * tileB.getPosition().getX() + tileB.getPosition().getY() % 2) - yDistance)/2;
		int distance = xDistance < 0 ? yDistance : yDistance + xDistance;

		Log.d("Events", String.format("Move %s from %s to %s distance %d", movement.getFighter().toString(), tileA.toString(), tileB.toString(), distance));
	}

	@Listen(async=true,senderClass=Fighter.class, event="changeOrientation")
	public void orientation(Fighter fighter, Fighter.Orientation orientation) {
		Log.d("Events", String.format("Fighter %s oriented %s", fighter.toString(), orientation.toString()));
	}
}
