package fr.android.api.test.data;

import fr.android.api.annotation.Managed;
import fr.android.api.event.EventProcessor;

@Managed
public interface EventListener extends EventProcessor<Integer> {
}
