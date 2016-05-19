package fr.game.display;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import fr.game.display.AnimatedSpriteAtlas.AnimationInfo;
import fr.game.display.modifier.IModifier;

public class Animation extends Sprite {
	float originalOffsetX, originalOffsetY;

	private float time;
	private AtlasRegion[] regions;
	private float[] durations;
	private int currentFrame;
	private List<IModifier> modifiers;
	private TextureAtlas atlas;

	public Animation(TextureAtlas atlas, AnimationInfo info, float duration) {
		this.atlas = atlas;
		update(info, duration);
		setColor(1, 1, 1, 1);
		setOrigin(regions[currentFrame].originalWidth / 2f, regions[currentFrame].originalHeight / 2f);
		int width = Math.abs(regions[currentFrame].getRegionWidth());
		int height = Math.abs(regions[currentFrame].getRegionHeight());
		if (regions[currentFrame].rotate) {
			rotate90(true);
			super.setBounds(regions[currentFrame].offsetX, regions[currentFrame].offsetY, height, width);
		} else
			super.setBounds(regions[currentFrame].offsetX, regions[currentFrame].offsetY, width, height);
	}

	@Override
	public void draw(SpriteBatch batch, float delta) {
		if (regions.length > 1) {
			time += delta;
			while(durations[currentFrame] < time) {
				time -= durations[currentFrame];
				currentFrame = (currentFrame + 1) % durations.length;
			}
			setRegion(regions[currentFrame]);
		}
		if (modifiers != null && modifiers.size() > 0) {
			for (Iterator<IModifier> m = modifiers.iterator(); m.hasNext();) {
				IModifier modifier = m.next();
				float f = modifier.next(this, delta);
				if (f > 0) m.remove();
			}
		}
		super.draw(batch);
	}

	public void reset() {
		time = 0;
		currentFrame = 0;
	}

	public void addModifier(IModifier modifier) {
		if (modifiers == null) {
			modifiers = new ArrayList<IModifier>();
		}
		modifiers.add(modifier);
	}

	public void update(AnimationInfo info, float duration) {
		reset();
		regions = new AtlasRegion[info.keyFrames.length];
		for (int i = 0; i < info.keyFrames.length; i++) {
			regions[i] = new AtlasRegion(atlas.findRegion(info.spriteName, info.keyFrames[i]));
		}
		if (regions.length > 1) {
			this.durations = new float[regions.length];
			if (info.duration == null) {
				for (int i=0;i<this.durations.length;i++) {
					this.durations[i] = duration / this.durations.length;
				}
			} else {
				for (int i=0;i<this.durations.length;i++) {
					this.durations[i] = info.duration[i] * duration;
				}
			}
		}
		originalOffsetX = regions[currentFrame].offsetX;
		originalOffsetY = regions[currentFrame].offsetY;
		setRegion(regions[0]);
	}

	@Override
	public void setPosition (float x, float y) {
		super.setPosition(x + regions[currentFrame].offsetX, y + regions[currentFrame].offsetY);
	}

	@Override
	public void setBounds (float x, float y, float width, float height) {
		float widthRatio = width / regions[currentFrame].originalWidth;
		float heightRatio = height / regions[currentFrame].originalHeight;
		regions[currentFrame].offsetX = originalOffsetX * widthRatio;
		regions[currentFrame].offsetY = originalOffsetY * heightRatio;
		super.setBounds(x + regions[currentFrame].offsetX, y + regions[currentFrame].offsetY, regions[currentFrame].packedWidth * widthRatio, regions[currentFrame].packedHeight * heightRatio);
	}

	@Override
	public void setSize (float width, float height) {
		setBounds(getX(), getY(), width, height);
	}

	@Override
	public void setOrigin (float originX, float originY) {
		super.setOrigin(originX - regions[currentFrame].offsetX, originY - regions[currentFrame].offsetY);
	}

	@Override
	public void flip (boolean x, boolean y) {
		// Flip texture.
//		super.flip(x, y);
//
//		float oldOriginX = getOriginX();
//		float oldOriginY = getOriginY();
//		float oldOffsetX = regions[currentFrame].offsetX;
//		float oldOffsetY = regions[currentFrame].offsetY;
//
//		// Updates x and y offsets.
//		regions[currentFrame].flip(x, y);
//
//		// Update position and origin with new offsets.
//		translate(regions[currentFrame].offsetX - oldOffsetX, regions[currentFrame].offsetY - oldOffsetY);
//		setOrigin(oldOriginX, oldOriginY);

		throw new RuntimeException("Not possible yet...");
	}

	@Override
	public float getX () {
		return super.getX() - regions[currentFrame].offsetX;
	}

	@Override
	public float getY () {
		return super.getY() - regions[currentFrame].offsetY;
	}

	@Override
	public float getOriginX () {
		return super.getOriginX() + regions[currentFrame].offsetX;
	}

	@Override
	public float getOriginY () {
		return super.getOriginY() + regions[currentFrame].offsetY;
	}

	@Override
	public float getWidth () {
		return super.getWidth() / regions[currentFrame].packedWidth * regions[currentFrame].originalWidth;
	}

	@Override
	public float getHeight () {
		return super.getHeight() / regions[currentFrame].packedHeight * regions[currentFrame].originalHeight;
	}
}
