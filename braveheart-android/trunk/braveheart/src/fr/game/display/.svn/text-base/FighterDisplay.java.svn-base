package fr.game.display;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.ColorModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.MoveYModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.util.modifier.IModifier;

import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Singleton;
import fr.android.api.display.Display;
import fr.android.api.display.DisplayManager;
import fr.android.api.display.SpriteFactory.Descriptor;
import fr.android.api.display.sprite.entity.EntitySprite;
import fr.android.api.event.EventProcessor;
import fr.android.api.event.MediumProcessor;
import fr.android.api.singleton.Disposable;
import fr.android.api.util.Logger;
import fr.game.display.config.Constants;
import fr.game.display.config.Descriptors.FighterAction;
import fr.game.display.utils.Coordinates;
import fr.game.engine.basics.Point;
import fr.game.engine.fighter.Attack;
import fr.game.engine.fighter.Fighter;
import fr.game.engine.fighter.Fighter.Orientation;
import fr.game.engine.map.BattleField;
import fr.game.engine.map.Tile;

@Display(displayed=Fighter.class,parent=BattleField.class)
public class FighterDisplay extends EntitySprite implements Disposable {
	@Singleton
	DisplayManager manager;

	Fighter fighter;

	final private EventProcessor<Fighter.Orientation> orientationProcessor = new MediumProcessor<Fighter.Orientation>() {
		@Override
		public void event(Object sender, Orientation message) {
			flip();
		}
	};

	final private EventProcessor<Fighter> deathProcessor = new MediumProcessor<Fighter>() {
		@Override
		public void event(Object sender, Fighter message) {
			manager.getDisplay(fighter.getWeapon()).setVisible(false);
			registerEntityModifier(
					new ParallelEntityModifier(
							new AlphaModifier(1f, getAlpha(), 0f),
							new MoveYModifier(1f, getY(), getY() - 3 * getGlSprite().getHeight(),new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}
								@Override
								public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {setVisible(false);}
							})));
		}
	};

	final private EventProcessor<Attack> hitProcessor = new MediumProcessor<Attack>() {
		@Override
		public void event(Object sender, Attack message) {
			registerEntityModifier(
					new SequenceEntityModifier(
							new ColorModifier(0.2f, getRed(), getRed(), getGreen(), 0f, getBlue(), 0f),
							new ColorModifier(0.2f, getRed(), getRed(), 0f, getGreen(), 0f, getBlue())));
		}
	};

	@Initializer
	public void initialize(final Fighter fighter) {
		this.fighter = fighter;
		super.initialize(fighter.getType());

		Object parent = manager.getCurrentParent();
		Logger.d("Fighter", fighter.toString() + " init");
		if (parent instanceof BattleField) {
			Logger.d("Fighter", fighter.toString() + " real init");
			BattleField battleField = (BattleField) parent;
			Point position = battleField.getTile(fighter).getPosition();

			setZIndex(position.getY());
			setPosition(Coordinates.getX(position), Coordinates.getY(position) - Constants.TILEHEIGHT/3f + Constants.TILEHEIGHT - Constants.TILEWIDTH);
			setSubType(FighterAction.WalkRight); //natural orientation for everything !
			if (fighter.getOrientation() == Fighter.Orientation.Left) {
				flip();
			}
		}

		this.fighter.changeOrientation().asyncSubscribe(orientationProcessor);
		this.fighter.died().asyncSubscribe(deathProcessor);
		this.fighter.hit().asyncSubscribe(hitProcessor);
	}

	public void move(Tile from, Tile to, float time) {
		Point position = to.getPosition();
		clearEntityModifiers();
		if (to.getPosition().getY() != from.getPosition().getY()) {
			setZIndex(to.getPosition().getY());
		}
		float y = Coordinates.getY(position) - Constants.TILEHEIGHT/3f + Constants.TILEHEIGHT - getGlSprite().getHeightScaled();
		registerEntityModifier(new MoveModifier(time/1000f, getX(), Coordinates.getX(position) , getY(), y));
	}

	@Override
	public void onSubtypeChanged(Object subtype, Descriptor descriptor) {
		super.onSubtypeChanged(subtype, descriptor);
		anim(Constants.TILEWIDTH/getGlSprite().getWidth(), 500);
	}

	@Override
	public String toString() {
		return fighter == null ? super.toString() : "Display : " + fighter.toString();
	}

	@Override
	public void dispose() {
		this.fighter.changeOrientation().unsubscribe(orientationProcessor);
		this.fighter.died().unsubscribe(deathProcessor);
		this.fighter.hit().unsubscribe(hitProcessor);
	}
}
