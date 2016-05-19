package fr.game.engine.singleton;

import fr.android.api.annotation.Singleton;
import fr.android.api.event.Event;

@Singleton
public interface TimeMaster {
	void start(int stepSize);
	void stop();
	boolean isStarted();

	long getStartTime();
	void setSlowFactor(float slowFactor);

	Event<Long> time();
}
