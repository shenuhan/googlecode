package fr.android.physics.manager;

import fr.android.api.Api;
import fr.android.api.annotation.Singleton;
import fr.android.api.event.Event;

@Singleton
public interface TimeManager {
	public static TimeManager instance = Api.singleton(TimeManager.class);
	
	public class StartEvent extends Event<Boolean> {}
	public class StopEvent extends Event<Boolean> {}
	public class StepEvent extends Event<Float> {}
	
	public void start(int step);
	public void stop();
	
	public float getAbsoluteTime();
	public float getStepTime();
}
