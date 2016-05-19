package fr.game.engine.fighter;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;
import fr.game.engine.map.Tile;

@Managed
public interface Movement extends Action {
	@Field
	Tile getTo();

	@Initializer
	void initialize(Fighter fighter, Tile to, float time);
}
