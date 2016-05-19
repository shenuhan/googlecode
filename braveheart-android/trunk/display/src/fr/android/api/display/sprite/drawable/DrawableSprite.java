package fr.android.api.display.sprite.drawable;


import java.util.Collection;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import fr.android.api.display.SpriteFactory.Descriptor;
import fr.android.api.display.sprite.Sprite;
import fr.android.api.display.sprite.SpriteHelper;
import fr.android.api.display.sprite.SpriteHelper.SubtypeChangeListener;

public class DrawableSprite extends Drawable implements Sprite<DrawableSprite>, SubtypeChangeListener, Drawable.Callback {
	protected SpriteHelper helper;
	protected LayoutSprite layoutSprite;

	protected LayoutSprite getLayoutSprite() {
		if (layoutSprite == null) {
			setSubType(DefaultSubtype);
		}
		return layoutSprite;
	}

	public DrawableSprite(Object type) {
		helper = new SpriteHelper(type, this);
	}

	protected DrawableSprite() {
		// need to initialize helper manually later
	}

	@Override
	public void setSubType(Object subType) {
		helper.setSubType(subType);
	}

	@Override
	public Object getSubtype() {
		return helper.getSubtype();
	}

	@Override
	public Collection<Object> getSubtypes() {
		return helper.getSubtypes();
	}

	@Override
	public void onSubtypeChanged(Object subtype, Descriptor descriptor) {
		layoutSprite = new LayoutSprite(descriptor.bitmap, descriptor.animation);
		layoutSprite.setCallback(this);
	}

	@Override
	public DrawableSprite anim(float x, float y, float ratio, int step) {
		getLayoutSprite().anim((int)x, (int)y, ratio, step);
		return this;
	}

	@Override
	public DrawableSprite anim(float x, float y, int step) {
		getLayoutSprite().anim((int)x, (int)y, step);
		return this;
	}


	@Override
	public DrawableSprite anim(float ratio, int step) {
		getLayoutSprite().anim(ratio, step);
		return this;
	}

	@Override
	public DrawableSprite anim(int step) {
		getLayoutSprite().anim(step);
		return this;
	}

	@Override
	public DrawableSprite show() {
		getLayoutSprite().show();
		return this;
	}

	@Override
	public DrawableSprite show(float scale) {
		getLayoutSprite().show(scale);
		return this;
	}

	@Override
	public DrawableSprite show(float x, float y) {
		getLayoutSprite().show((int)x, (int)y);
		return this;
	}

	@Override
	public DrawableSprite show(float x, float y, float scale) {
		getLayoutSprite().show((int)x, (int)y, scale);
		return this;
	}

	@Override
	public DrawableSprite show(float width, float height, Fitting fitting) {
		float scaleX = width / getLayoutSprite().getWidth();
		float scaleY = height / getLayoutSprite().getHeight();

		if (fitting == Fitting.Fit) {
			getLayoutSprite().show(0, 0, scaleX > scaleY ? scaleY : scaleX);
		} else if (fitting == Fitting.FitMax) {
			getLayoutSprite().show(0, 0, scaleX > scaleY ? scaleX : scaleY);
		} else {
			getLayoutSprite().show(0, 0, scaleX, scaleY);
		}
		return this;
	}

	@Override
	public void draw(Canvas canvas) {
		getLayoutSprite().draw(canvas);
	}

	@Override
	public int getOpacity() {
		return getLayoutSprite().getOpacity();
	}

	@Override
	public void setAlpha(int alpha) {
		getLayoutSprite().setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		getLayoutSprite().setColorFilter(cf);
	}

	@Override
	public void invalidateDrawable(Drawable who) {
		if (who == layoutSprite) {
			invalidateSelf();
		}
	}

	@Override
	public void scheduleDrawable(Drawable who, Runnable what, long when) {
		if (who == layoutSprite) {
			scheduleSelf(what, when);
		}
	}

	@Override
	public void unscheduleDrawable(Drawable who, Runnable what) {
		if (who == layoutSprite) {
			unscheduleSelf(what);
		}
	}

	@Override
	public void flip() {
		throw new NoSuchMethodError();
	}
}
