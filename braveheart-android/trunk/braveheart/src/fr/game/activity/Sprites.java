package fr.game.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import fr.android.api.Api;
import fr.android.api.annotation.Managed;
import fr.android.api.annotation.Singleton;
import fr.android.api.display.SpriteFactory;
import fr.android.api.display.sprite.drawable.DrawableSprite;
import fr.game.display.config.Descriptors;

@Managed
public class Sprites extends Activity {
	@Singleton
	SpriteFactory spriteFactory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Api.manage(this);
		spriteFactory.initialize(this, Descriptors.All);

		ScrollView scroll = new ScrollView(this);
		scroll.setBackgroundColor(Color.DKGRAY);
		scroll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		LinearLayout layout = new LinearLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		layout.setOrientation(LinearLayout.VERTICAL);
		for (Object type : spriteFactory.getAllType()) {
			for (Object subtype : spriteFactory.getSpriteDescriptors(type).keySet()) {
				final DrawableSprite sprite = spriteFactory.getDrawableSprite(type);
				TextView text = new TextView(this);
				text.setText(type.toString() + ", " + subtype.toString());
				layout.addView(text);

				final ImageView image = new ImageView(this);
				image.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 32));
				sprite.setSubType(subtype);
				image.setImageDrawable(sprite);
				image.post(new Runnable() {
					@Override
					public void run() { sprite.anim(500);}
				});
				layout.addView(image);
			}
		}
		scroll.addView(layout);
		setContentView(scroll);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
