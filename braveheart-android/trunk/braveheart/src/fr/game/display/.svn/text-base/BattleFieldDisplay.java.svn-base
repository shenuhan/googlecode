package fr.game.display;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.IEntity;

import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Singleton;
import fr.android.api.display.Display;
import fr.android.api.display.DisplayManager;
import fr.android.api.display.SpriteFactory;
import fr.android.api.display.sprite.Sprite;
import fr.android.api.event.EventProcessor;
import fr.android.api.event.MediumProcessor;
import fr.android.api.singleton.Disposable;
import fr.android.api.util.ManualDispatcher;
import fr.game.display.config.Constants;
import fr.game.display.config.Descriptors.SubType;
import fr.game.display.config.Descriptors.Type;
import fr.game.engine.fighter.Movement;
import fr.game.engine.map.BattleField;

@Display(displayed=BattleField.class)
public class BattleFieldDisplay extends Entity implements Disposable {
	@Singleton
	SpriteFactory factory;
	@Singleton
	DisplayManager manager;

	private BattleField battlefield;

	@Initializer
	public void initialize(BattleField battlefield, int screenWidth, int screenHeight) {
		this.battlefield = battlefield;
		this.battlefield.moved().asyncSubscribe(dispatcher, movementProcessor);

		float width = this.battlefield.getWidth() * Constants.TILEWIDTH + Constants.TILEWIDTH/2f;
		float height = this.battlefield.getHeight() * Constants.TILEHEIGHT + Constants.TILEHEIGHT/2f;


		float scaleX = screenWidth / width;
		float scaleY = screenHeight * ((float) factory.getRegion(Type.BackGround,SubType.Bottom).getHeight()/(float)factory.getRegion(Type.BackGround,Sprite.DefaultSubtype).getHeight()) / height;

		float scale = scaleX < scaleY ? scaleX : scaleY;

		setScale(scale);
		setPosition(0, screenHeight - scale * height);
	}

	private ManualDispatcher<Movement> dispatcher = new ManualDispatcher<Movement>();

	private EventProcessor<Movement> movementProcessor = new MediumProcessor<Movement>() {
		@Override
		public void event(Object sender, Movement movement) {
			IEntity entity = manager.getDisplay(movement.getFighter());
			if (entity instanceof FighterDisplay) {
				FighterDisplay display = (FighterDisplay) entity;
				int z = display.getZIndex();
				display.move(battlefield.getTile(movement.getFighter()), movement.getTo(), movement.getTime());
				if (z != display.getZIndex()) {
					sortChildren();
				}
			}
		}
	};

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		dispatcher.doDispatch();
	}

	@Override
	public void dispose() {
		dispatcher.doDispatch();
	}
}
