package fr.game.display.view;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import fr.android.api.display.sprite.drawable.DrawableSprite;
import fr.game.display.config.Descriptors.FighterAction;
import fr.game.engine.fighter.Fighter;

public class FighterPictureView extends View {
	public FighterPictureView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FighterPictureView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FighterPictureView(Context context) {
		super(context);
	}

	private Fighter fighter;
	private DrawableSprite sprite;
	public void setFighter(Fighter fighter) {
		this.fighter = fighter;
		sprite = new DrawableSprite(this.fighter.getType());
		sprite.setSubType(FighterAction.WalkDown);
		setBackgroundDrawable(sprite);

		this.post(new Runnable() {
			@Override
			public void run() {
				sprite.anim(300);
			}
		});
	}
}