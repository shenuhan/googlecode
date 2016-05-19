package fr.game.display.utils;

import fr.game.display.config.Constants;
import fr.game.engine.basics.Point;

public class Coordinates {
	static public float getX(Point position) {
		return position.getX() * Constants.TILEWIDTH + Constants.TILEWIDTH/2f * ((position.getY()+1) % 2);
	}

	static public float getY(Point position) {
		return position.getY() * Constants.TILEHEIGHT;
	}
}
