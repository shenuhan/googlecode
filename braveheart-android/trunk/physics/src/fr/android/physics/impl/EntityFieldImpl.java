package fr.android.physics.impl;

import java.util.ArrayList;
import java.util.Collection;

import fr.android.api.Api;
import fr.android.api.annotation.Field;
import fr.android.api.annotation.Listener;
import fr.android.api.annotation.Listener.Priority;
import fr.android.physics.Entity;
import fr.android.physics.EntityField;
import fr.android.physics.Impact;
import fr.android.physics.Point;
import fr.android.physics.Vector;
import fr.android.physics.manager.ImpactManager;
import fr.android.physics.manager.TimeManager;

public class EntityFieldImpl extends FieldObjectImpl implements EntityField {
	private float length;
	private float height;
	
	private ImpactManager impactManager;
	
	@Field
	private Collection<Entity> entities = new ArrayList<Entity>();

	public EntityFieldImpl() {
	}
	
	@Override
	public void initilialize() {
		impactManager = Api.singleton(ImpactManager.class);
		this.setPosition(Api.make(Point.class));
	}
	
	
	@Listener(eventClass = TimeManager.StepEvent.class, priority=Priority.Medium)
	public void nextStep(TimeManager manager, float step) {
		float absoluteTime = manager.getAbsoluteTime() - step;
		synchronized (entities) {
			Impact impact = impactManager.getNextImpact(step);
			while(impact != null && impact.getTime() < step) {
				moveAll(absoluteTime, impact.getTime());
				step=step-impact.getTime();
				absoluteTime += impact.getTime(); 
				impact.applyImpact();
				impact = impactManager.getNextImpact(step);
			}
			moveAll(absoluteTime, step);
		}
	}
	
	private void moveAll(float absoluteTime, float step) {
		for (Entity entity : entities) {
			Vector movement = entity.getSpeed().clone().multiply(step);
			entity.getPosition().move(movement);
			Api.recycle(movement);
		}		
	}
	
	@Override
	public float getLength() {
		return length;
	}

	@Override
	public void setLength(float length) {
		this.length = length;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void setHeight(float height) {
		this.height = height;
	}

	private AddEntityEvent event = new AddEntityEvent();
	@Override
	public void addEntity(Entity entity) {
		synchronized (entities) {
			this.entities.add(entity);
		}
		event.raise(this, entity);
	}
}
