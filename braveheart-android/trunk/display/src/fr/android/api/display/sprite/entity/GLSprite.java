package fr.android.api.display.sprite.entity;


import java.util.Arrays;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

import fr.android.api.display.SpriteFactory.Descriptor;
import fr.android.api.display.animation.AnimationDescriptor;

public class GLSprite extends AnimatedSprite {
	final Descriptor descriptor;
	private long step;

	public GLSprite(final float pX, final float pY, final float pTileWidth, final float pTileHeight, final Descriptor descriptor) {
		super(pX, pY, pTileWidth, pTileHeight, descriptor.region);
		this.descriptor = descriptor;
	}

	public GLSprite(final Descriptor descriptor) {
		this(0, 0, descriptor.region.getTileWidth(), descriptor.region.getTileHeight(), descriptor);
	}

	IAnimationListener listener = null;
	public void setAnimationListener(AnimatedSprite.IAnimationListener listener) {
		this.listener = listener;
	}

	public GLSprite anim(long step) {
		AnimationDescriptor animation = descriptor.animation;
		if (this.getTextureRegion().getTileCount() > 1) {
			if (animation.numFrame != null) {
				if (animation.timeFrame != null) {
					animate(animation.timeFrame,animation.numFrame,animation.loopCount,listener);
				} else {
					long[] timeFrame = new long[animation.numFrame.length];
					Arrays.fill(timeFrame, step/animation.numFrame.length);
					animate(timeFrame,animation.numFrame,animation.loopCount,listener);
				}
			} else {
				if (animation.timeFrame != null) {
					animate(animation.timeFrame,animation.loopCount,listener);
				} else {
					animate(step, animation.loopCount,listener);
				}
			}
		}
		this.step = step;
		return this;
	}

	public GLSprite anim(float ratio, long step) {
		this.setScale(getScaleX()*ratio,getScaleY()*ratio);
		this.setScaleCenter(0, 0);
		return anim(step);
	}

	public GLSprite anim(float x, float y, long step) {
		this.setPosition(x, y);
		return anim(step);
	}

	public GLSprite anim(float x, float y, float ratio, long step) {
		this.setPosition(x, y);
		return anim(ratio, step);
	}

	public GLSprite show() {
		return this;
	}

	public GLSprite show(float ratio) {
		this.setScale(getScaleX() * ratio,getScaleY() * ratio);
		this.setScaleCenter(0, 0);
		return show();
	}

	public GLSprite show(float x, float y) {
		this.setPosition(x, y);
		return show();
	}

	public GLSprite show(float x, float y, float ratio) {
		this.setScale(getScaleX() * ratio,getScaleY() * ratio);
		this.setScaleCenter(0, 0);
		this.setPosition(x, y);
		return show();
	}

	public GLSprite show(float x, float y, float ratioX, float ratioY) {
		this.setScale(getScaleX() * ratioX,getScaleY() * ratioY);
		this.setScaleCenter(0, 0);
		this.setPosition(x, y);
		return show();
	}

	public long getStep() {
		return step;
	}

	@Override
	public void setCurrentTileIndex(int pTileIndex) {
		super.setCurrentTileIndex(pTileIndex);
		if (this.getWidth() != this.getTextureRegion().getTileWidth() || this.getHeight() != this.getTextureRegion().getTileHeight()) {
			if (descriptor.isFlipped) {
				this.setPosition(this.getX() - this.getTextureRegion().getTileWidth() + this.getWidth(), this.getY());
			}
			this.setSize(this.getTextureRegion().getTileWidth(), this.getTextureRegion().getTileHeight());
		}
	}

	@Override
	public void setCurrentTileIndex(final int pTileColumn, final int pTileRow) {
		super.setCurrentTileIndex(pTileColumn,pTileRow);
		if (this.getWidth() != this.getTextureRegion().getTileWidth() || this.getHeight() != this.getTextureRegion().getTileHeight()) {
			if (descriptor.isFlipped) {
				this.setPosition(this.getX() - this.getTextureRegion().getTileWidth() + this.getWidth(), this.getY());
			}
			this.setSize(this.getTextureRegion().getTileWidth(), this.getTextureRegion().getTileHeight());
		}
	}
}
