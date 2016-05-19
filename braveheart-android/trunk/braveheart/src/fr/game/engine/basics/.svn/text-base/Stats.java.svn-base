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
public interface Stats {
	@Field
	float getValue();
	void setValue(float value);

	@Field
	float getMax();
	void setMax(float max);

	double add(float offset);
	double sub(float offset);

	@Initializer
	void initialize(float max);

	@Initializer
	void initialize(float max, float value);
}
