package fr.android.physics.manager;

import java.util.ArrayList;
import java.util.Collection;

import fr.android.api.Api;
import fr.android.api.annotation.Listener;
import fr.android.api.annotation.Listener.Priority;
import fr.android.physics.Entity;
import fr.android.physics.EntityField;
import fr.android.physics.Vector;

public class GravityManagerImpl implements GravityManager {
	private Vector gravity = Api.make(Vector.class, 0, -100);  
	
	@Override
	public Vector getGravity() {
		return gravity;
	}

	@Override
	public void setGravity(Vector gravity) {
		this.gravity = gravity;
	}
	
	private final Collection<Entity> entities = new ArrayList<Entity>();
	
	@Listener(eventClass = EntityField.AddEntityEvent.class)
	public void newEntity(EntityField sender, Entity entity) {
		synchronized (entities) {
			entities.add(entity);
		}
	}
	
	@Listener(eventClass = TimeManager.StepEvent.class, priority=Priority.Low)
	public void nextStep(TimeManager manager, float step) {
		Vector stepGravity = gravity.clone().multiply(step);
		synchronized (entities) {
			for (Entity entity : entities) {
				if (entity.isGravity()) {
					entity.getSpeed().add(stepGravity);
				}
			}
		}
		Api.recycle(stepGravity);
	}
}
