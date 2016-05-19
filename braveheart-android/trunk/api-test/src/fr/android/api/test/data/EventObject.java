package fr.android.api.test.data;

import fr.android.api.annotation.Managed;
import fr.android.api.event.Event;

@Managed
public interface EventObject {
	class TestEvent extends Event<Integer> {}
	
	TestEvent event();
	
	void raiseEvent();
}
