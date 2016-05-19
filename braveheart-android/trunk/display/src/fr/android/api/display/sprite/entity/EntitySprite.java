package fr.android.api.display.sprite.entity;


import java.util.Collection;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.IEntity;

import fr.android.api.display.SpriteFactory.Descriptor;
import fr.android.api.display.sprite.Sprite;
import fr.android.api.display.sprite.SpriteHelper;
import fr.android.api.display.sprite.SpriteHelper.SubtypeChangeListener;
import fr.android.api.util.Logger;

public class EntitySprite extends Entity implements Sprite<EntitySprite>, SubtypeChangeListener {
	private SpriteHelper helper;

	private GLSprite glSprite;
	protected GLSprite getGlSprite() {
		if (glSprite == null) {
			setSubType(DefaultSubtype);
		}
		return glSprite;
	}

	public EntitySprite(Object type) {
		initialize(type);
	}

	public void initialize(Object type) {
		helper = new SpriteHelper(type, this);
	}

	protected EntitySprite() {
		// need to initialize helper manually later
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
	public void setSubType(Object subtype) {
		helper.setSubType(subtype);
	}

	@Override
	public void onSubtypeChanged(Object subtype, Descriptor descriptor) {
		if (glSprite == null) {
			glSprite = new GLSprite(descriptor);
			attachChild(glSprite);
		} else {
			glSprite.stopAnimation();
			detachChild(glSprite);
			float scalex = glSprite.getScaleX();
			float scaley = glSprite.getScaleY();
			glSprite = new GLSprite(glSprite.getX(), glSprite.getY(), glSprite.getWidth(), glSprite.getHeight(), descriptor);
			glSprite.setScale(scalex,scaley);
			glSprite.setScaleCenter(glSprite.getX(), glSprite.getY());
			attachChild(glSprite);
		}
	}

	@Override
	public EntitySprite show(float x, float y, float ratio) {
		getGlSprite().show(x, y, ratio);
		return this;
	}

	@Override
	public EntitySprite show(float x, float y) {
		getGlSprite().show(x, y);
		return this;
	}

	@Override
	public EntitySprite show(float ratio) {
		getGlSprite().show(ratio);
		return this;
	}

	@Override
	public EntitySprite show() {
		getGlSprite().show();
		return this;
	}

	@Override
	public EntitySprite show(float width, float height, Fitting fitting) {
		float scaleX = width / getGlSprite().getWidth();
		float scaleY = height / getGlSprite().getHeight();

		if (fitting == Fitting.Fit) {
			getGlSprite().show(scaleX > scaleY ? scaleY : scaleX);
		} else if (fitting == Fitting.FitMax) {
			getGlSprite().show(scaleX > scaleY ? scaleX : scaleY);
		} else {
			getGlSprite().show(0,0, scaleX, scaleY);
		}
		return this;
	}


	@Override
	public EntitySprite anim(float x, float y, float ratio, int step) {
		getGlSprite().anim(x, y, ratio, step);
		return this;
	}

	@Override
	public EntitySprite anim(float x, float y, int step) {
		getGlSprite().anim(x, y, step);
		return this;
	}

	@Override
	public EntitySprite anim(float ratio, int step) {
		getGlSprite().anim(ratio, step);
		return this;
	}

	@Override
	public EntitySprite anim(int step) {
		getGlSprite().anim(step);
		return this;
	}

	@Override
	public boolean attachChild(IEntity pEntity, int pIndex)	throws IllegalStateException {
		boolean res = super.attachChild(pEntity, pIndex);
		sortChildren();
		return res;
	}

	@Override
	public void attachChild(IEntity pEntity) throws IllegalStateException {
		super.attachChild(pEntity);
		sortChildren();
	}

	@Override
	public void flip() {
		Logger.d("EntitySprite", "Flipping " + this.toString());
		helper.flip();
		for (int i = 0 ; i < getChildCount(); i++) {
			IEntity entity = getChild(i);
			if (entity instanceof EntitySprite) {
				((EntitySprite) entity).flip();
			}
		}
	}

	public boolean isFlipped() {
		return helper.isFlipped();
	}

	@Override
	public void setColor(float pRed, float pGreen, float pBlue) {
		getGlSprite().setColor(pRed, pGreen, pBlue);
	}

	@Override
	public void setColor(float pRed, float pGreen, float pBlue, float pAlpha) {
		getGlSprite().setColor(pRed, pGreen, pBlue, pAlpha);
	}

	@Override
	public float getRed() {
		return getGlSprite().getRed();
	}

	@Override
	public float getGreen() {
		return getGlSprite().getGreen();
	}

	@Override
	public float getBlue() {
		return getGlSprite().getBlue();
	}

	@Override
	public void setAlpha(float pAlpha) {
		getGlSprite().setAlpha(pAlpha);
	}

	@Override
	public float getAlpha() {
		return getGlSprite().getAlpha();
	}
}
