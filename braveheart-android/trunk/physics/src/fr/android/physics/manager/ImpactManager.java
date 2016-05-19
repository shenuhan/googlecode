package fr.android.physics.manager;

import fr.android.api.annotation.Singleton;
import fr.android.physics.Impact;

@Singleton
public interface ImpactManager {
	Impact getNextImpact(float step);
}
