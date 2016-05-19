package fr.game.activity;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.sensor.accelerometer.AccelerometerData;
import org.anddev.andengine.sensor.accelerometer.IAccelerometerListener;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.widget.Toast;
import fr.android.api.Api;
import fr.android.api.annotation.Managed;
import fr.android.api.annotation.Singleton;
import fr.android.api.display.SpriteFactory;
import fr.android.api.display.sprite.Sprite;
import fr.android.api.display.sprite.entity.EntitySprite;
import fr.game.display.config.Descriptors;
import fr.game.engine.fighter.Weapon.WeaponType;

@Managed
public class EngineSandBox extends BaseGameActivity implements IAccelerometerListener, IOnSceneTouchListener {
	@Singleton
	SpriteFactory spriteFactory;

	private Engine engine;
	private Scene scene;

	@Override
	public synchronized Engine onLoadEngine() {
		Api.manage(this);
		spriteFactory.initialize(this, Descriptors.All);

		Toast.makeText(this, "Sand Box", Toast.LENGTH_LONG).show();
		final Camera camera = new Camera(0, 0, getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight());
		camera.setZClippingPlanes(-100, 100);
		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight()), camera);
		engineOptions.getTouchOptions().setRunOnUpdateThread(true);
		engine = new Engine(engineOptions);
		return engine;
	}

	@Override
	public void onLoadResources() {
		getEngine().getTextureManager().loadTextures(spriteFactory.getTextures());
	}

	@Override
	public Scene onLoadScene() {
		getEngine().registerUpdateHandler(new FPSLogger());

		scene = new Scene();
		scene.setOnSceneTouchListener(this);
		scene.setBackground(new ColorBackground(0, 155, 155));

		EntitySprite sword = spriteFactory.getEntitySprite(WeaponType.Sword);
		sword.setSubType(Sprite.DefaultSubtype);
		sword.anim(2000);
		scene.attachChild(sword);

		EntitySprite sword2 = spriteFactory.getEntitySprite(WeaponType.Sword);
		sword2.setSubType(Sprite.DefaultSubtype);
		sword2.flip();
		sword.anim(100,0,2000);
		scene.attachChild(sword2);

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
		super.onDestroy();
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		disableAccelerometerSensor();
	}

	@Override
	public void onLoadComplete() {
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		return true;
	}

}