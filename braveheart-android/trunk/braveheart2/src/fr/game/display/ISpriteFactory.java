package fr.game.display;

public interface ISpriteFactory {
	public Animation setSprite(float duration, Object ... keys);
	public Animation getSprite();
}
