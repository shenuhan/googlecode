package fr.game.display;

import fr.android.api.annotation.Initializer;
import fr.android.api.display.Display;
import fr.android.api.display.sprite.entity.EntitySprite;
import fr.game.display.config.Constants;
import fr.game.display.config.Descriptors.Type;
import fr.game.display.utils.Coordinates;
import fr.game.engine.map.Tile;

@Display(displayed=Tile.class)
class TileDisplay extends EntitySprite {
	public TileDisplay() {
	}

	@Initializer
	public void initialize(Tile tile) {
		super.initialize(Type.Tile);
		setZIndex(-100);
		setPosition(Coordinates.getX(tile.getPosition()), Coordinates.getY(tile.getPosition()));
		show(Constants.TILEWIDTH * 1.2f,Constants.TILEHEIGHT * 1.4f,Fitting.Scale);
	}
}
