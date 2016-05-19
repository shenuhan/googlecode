package fr.game.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import fr.android.api.Api;
import fr.android.api.annotation.Managed;
import fr.android.api.annotation.Singleton;
import fr.android.api.display.SpriteFactory;
import fr.android.api.display.sprite.Sprite.Fitting;
import fr.android.api.display.sprite.drawable.DrawableSprite;
import fr.game.R;
import fr.game.display.config.Descriptors;
import fr.game.display.config.Descriptors.Type;
import fr.game.display.utils.FlingDetector;
import fr.game.display.view.FighterMenuView;
import fr.game.example.ArmySingleton;

@Managed
public class Fighters extends Activity {
	@Singleton
	SpriteFactory spriteFactory;
	@Singleton
	ArmySingleton army;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Api.manage(this);
		spriteFactory.initialize(this, Descriptors.All);

		FrameLayout layout = (FrameLayout) View.inflate(this, R.layout.fightermenu, null);

		final FighterMenuView view = (FighterMenuView) layout.findViewById(R.id.armyMenu);
		view.setOnTouchListener(new FlingDetector());
		view.setFeatureItems(army.getFullArmy(), getWindowManager().getDefaultDisplay().getWidth());

		final DrawableSprite background = spriteFactory.getDrawableSprite(Type.FighterMenuBackGround);
		view.setBackgroundDrawable(background);
		view.post(new Runnable() {
			@Override
			public void run() {
				background.show(view.getWidth(),view.getHeight(), Fitting.Scale);
			}
		});
		setContentView(layout);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
