package fr.game.display.modifier;

import com.badlogic.gdx.graphics.g2d.Sprite;


public interface IModifier {
	float next(Sprite s, float delta);
}
