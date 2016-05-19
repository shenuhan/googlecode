package fr.android.physics.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.util.FloatMath;
import fr.android.api.Api;
import fr.android.api.annotation.Listener;
import fr.android.api.annotation.Singleton;
import fr.android.physics.Circle;
import fr.android.physics.Entity;
import fr.android.physics.EntityField;
import fr.android.physics.Impact;
import fr.android.physics.Vector;
import fr.android.physics.impl.ImpactBall;
import fr.android.physics.impl.ImpactWall;

@Singleton
public class ImpactManagerImpl implements ImpactManager{
	private EntityField entityField;
	private Collection<Entity> entities;
	
	public ImpactManagerImpl() {
		entities = new ArrayList<Entity>();
	}
	
	@Listener(eventClass = EntityField.AddEntityEvent.class)
	public void newBall(EntityField sender, Entity entity) {
		entityField = sender;
		synchronized (entities) {
			entities.add(entity);
		}
	}
	
	public void findImpact(EntityField ballfield, Circle c, ImpactWall impact) {
		if (c.getSpeed().getX() > 0) {
			float t = (ballfield.getLength() - c.getPosition().getX() - c.getRadius())/c.getSpeed().getX();
			if (t < impact.getTime()) {
				impact.setTime(t <= 0 ? 0.001f : t);
				Vector hitWall = Api.make(Vector.class,-1.0f,0.0f);
				impact.setHitWall(hitWall);
			}
		} else if (c.getSpeed().getX() < 0) {
			float t = (-c.getPosition().getX() + c.getRadius())/c.getSpeed().getX();
			if (t < impact.getTime()) {
				impact.setTime(t <= 0 ? 0.001f : t);
				Vector hitWall = Api.make(Vector.class,1.0f,0.0f);
				impact.setHitWall(hitWall);
			}		
		}
		if (c.getSpeed().getY() > 0) {
			float t = (ballfield.getHeight() - c.getPosition().getY() - c.getRadius())/c.getSpeed().getY();
			if (t < impact.getTime()) {
				impact.setTime(t <= 0 ? 0.001f : t);
				Vector hitWall = Api.make(Vector.class);
				hitWall.setX(0);
				hitWall.setY(-1);
				impact.setHitWall(hitWall);
			}
		} else if (c.getSpeed().getY() < 0) {
			float t = (-c.getPosition().getY() + c.getRadius())/c.getSpeed().getY();
			if (t < impact.getTime()) {
				impact.setTime(t <= 0 ? 0.001f : t);
				Vector hitWall = Api.make(Vector.class);
				hitWall.setX(0);
				hitWall.setY(1);
				impact.setHitWall(hitWall);
			}
		}
	}
	
	public float findImpactTime(Circle c1, Circle c2, float step) {
		Vector movement = c1.getSpeed().clone().sub(c2.getSpeed()).multiply(step);
		Vector distance = c1.getPosition().to(c2.getPosition());
		
		float s = movement.scalar(distance);
		if (s <= 0) {
			Api.recycle(movement);
			Api.recycle(distance);
			return Float.MAX_VALUE;
		}
		
		float D = FloatMath.sqrt(distance.getSquareLength());
		float R = c1.getRadius() + c2.getRadius();
		
		float distanceWithoutRadius = D-R; 
		
		if (distanceWithoutRadius*distanceWithoutRadius > movement.getSquareLength()) {
			Api.recycle(movement);
			Api.recycle(distance);
			return Float.MAX_VALUE;
		}
	
		float radical = s*s - movement.getSquareLength()*(distance.getSquareLength() - R*R);
		
		if (radical <= 0) {
			Api.recycle(movement);
			Api.recycle(distance);
			return Float.MAX_VALUE;
		}
		
		float radicalRoot = FloatMath.sqrt(radical);
		
		float t1 = (s - radicalRoot) / (movement.getSquareLength());
		float t2 = (s + radicalRoot) / (movement.getSquareLength());
		
		float t = t1 < 0 ? t2 : t2 < 0 ? t1 : t1 < t2 ? t1 : t2;
		
		if (t <= 0 || t >= 1) {
			Api.recycle(movement);
			return Float.MAX_VALUE;
		}
		
		return t * step;
	}

	@Override
	public Impact getNextImpact(float step) {
		Impact impact = new ImpactBall(null, null, step);
		synchronized (entities) {
			List<Entity> currents = new ArrayList<Entity>(entities.size());
			for (Entity entity : entities) {
				{
					if (entity instanceof Circle) {
						ImpactWall wall = new ImpactWall(entityField, (Circle)entity);
						findImpact(entityField, (Circle)entity, wall);
						if (wall.getTime() < impact.getTime()) {
							impact = wall;
						}
					}
				}
			}
			for (Entity entity : entities) {
				boolean modified = false;
				for (int i=0 ; i < currents.size(); i++) {
					Entity c = currents.get(i);
					if (c instanceof Circle && entity instanceof Circle) {
						float t = findImpactTime((Circle)c, (Circle)entity, impact.getTime());
						if (t < impact.getTime()) {
							impact = new ImpactBall((Circle)c, (Circle)entity, t);
							currents.remove(i);
							currents.add(0,c);
							modified = true;
						}
					}
				}
				if (modified) {
					currents.add(0, entity);
				} else {
					currents.add(entity);
				}
			}
			entities = currents;
		}
		return impact;
	}
}
