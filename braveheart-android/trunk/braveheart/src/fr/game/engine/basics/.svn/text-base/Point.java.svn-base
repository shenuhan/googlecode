/**
 * 
 */
package fr.game.engine.basics;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;

/**
 * @author jean
 *
 */
@Managed
public interface Point {
	@Field
	public int getX();
	public void setX(int x);
	@Field
	public int getY();
	public void setY(int y);

	@Initializer
	void initialize(int x, int y);

	public Point relativePoint(int offsetX,int offsetY);
}
