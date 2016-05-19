/**
 * 
 */
package fr.game.engine.basics;

import fr.android.api.annotation.Field;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Managed;

/**
 * @author AT92015
 *
 */
@Managed
public interface Range {
	@Field
	float getMin();
	void setMin(float min);

	@Field
	float getMax();
	void setMax(float max);

	float getRandomValue();

	@Initializer
	void initialize(float min, float max);
}
