package fr.game.engine.fighter;

import fr.android.api.annotation.Field;

public interface Action {
	@Field
	Fighter getFighter();
	@Field
	float getTime();
}
