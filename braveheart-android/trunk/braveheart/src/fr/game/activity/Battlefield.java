package fr.game.activity;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.EntityBackground;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.sensor.accelerometer.AccelerometerData;
import org.anddev.andengine.sensor.accelerometer.IAccelerometerListener;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.widget.Toast;
import fr.android.api.Api;
import fr.android.api.annotation.Managed;
import fr.android.api.annotation.Singleton;
import fr.android.api.display.DisplayManager;
import fr.android.api.display.SpriteFactory;
import fr.android.api.display.sprite.Sprite.Fitting;
import fr.android.api.display.sprite.entity.EntitySprite;
import fr.game.debug.EventLogger;
import fr.game.display.config.Descriptors;
import fr.game.display.config.Descriptors.Type;
import fr.game.engine.BattleManager;
import fr.game.engine.map.BattleField;
import fr.game.engine.map.BattleField.Side;
import fr.game.engine.singleton.TimeMaster;
import fr.game.example.ArmySingleton;

@Managed
public class Battlefield extends BaseGameActivity implements IAccelerometerListener, IOnSceneTouchListener {
	private BattleField battlefield;
	private BattleManager battlemanager;
	private Scene scene;

	private int width;
	private int height;

	private final int BATTLEFIELDHEIGHT = 15;
	private final int BATTLEFIELDWIDTH = 10;

	@Singleton
	private SpriteFactory spriteFactory;
	@Singleton
	private TimeMaster timeMaster;
	@Singleton
	private DisplayManager displayManager;
	@Singleton
	private ArmySingleton army;

	private Engine engine = null;

	@Override
	public synchronized Engine onLoadEngine() {
		Api.manage(this);
		EventLogger.start();

		if (getWindowManager().getDefaultDisplay().getWidth() > getWindowManager().getDefaultDisplay().getHeight()) {
			width = getWindowManager().getDefaultDisplay().getWidth();
			height = getWindowManager().getDefaultDisplay().getHeight();
		} else {
			height = getWindowManager().getDefaultDisplay().getWidth();
			width = getWindowManager().getDefaultDisplay().getHeight();
		}

		battlefield = Api.make(BattleField.class, BATTLEFIELDWIDTH, BATTLEFIELDHEIGHT);
		battlemanager = Api.make(BattleManager.class, battlefield);

		battlefield.add(army.getGoodArmy(),Side.Left);
		battlefield.add(army.getEvilArmy(),Side.Right);

		Toast.makeText(this, "This is the battlefield", Toast.LENGTH_LONG).show();
		final Camera camera = new Camera(0, 0, width, height);
		camera.setZClippingPlanes(-100, 100);
		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(width, height), camera);
		engineOptions.getTouchOptions().setRunOnUpdateThread(true);
		engine = new Engine(engineOptions);
		return engine;
	}

	@Override
	public void onLoadResources() {
		spriteFactory.initialize(this, Descriptors.All);
		getEngine().getTextureManager().loadTextures(spriteFactory.getTextures());
	}

	@Override
	public Scene onLoadScene() {
		getEngine().registerUpdateHandler(new FPSLogger());

		scene = new Scene();
		scene.setBackground(new EntityBackground(new EntitySprite(Type.BackGround).show(width,height,Fitting.Scale)));
		scene.setOnSceneTouchListener(this);

		IEntity battlefieldDisplay = displayManager.getDisplay(battlefield, width, height);
		scene.attachChild(battlefieldDisplay);

		return scene;
	}

	@Override
	public void onAccelerometerChanged(final AccelerometerData pAccelerometerData) {
	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();

		this.enableAccelerometerSensor(this);
	}

	@Override
	protected void onDestroy() {
		displayManager.removeDisplay(battlefield);
		Api.destroy(battlefield);
		Api.destroy(battlemanager);
		EventLogger.stop();
		super.onDestroy();
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		if (timeMaster.isStarted()) {
			timeMaster.stop();
		}
		disableAccelerometerSensor();
	}

	@Override
	public void onLoadComplete() {
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.isActionDown()) {
			if (timeMaster.isStarted()) {
				timeMaster.stop();
			} else {
				timeMaster.start(10);
			}
		}
		return true;
	}

}