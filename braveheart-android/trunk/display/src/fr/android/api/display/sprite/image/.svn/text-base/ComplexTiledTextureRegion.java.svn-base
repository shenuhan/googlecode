package fr.android.api.display.sprite.image;


import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.Rect;

public class ComplexTiledTextureRegion extends TiledTextureRegion {
	final private Rect[] frames;

	private final int maxWidth;
	private final int maxHeight;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ComplexTiledTextureRegion(final ITexture pTexture, final Rect[] frames) {
		super(pTexture, 0, 0, 0, 0, frames.length, 1);

		int left = frames[0].left;
		int right = frames[0].right;
		int top = frames[0].top;
		int bottom = frames[0].bottom;
		int maxWidth = frames[0].width();
		int maxHeight = frames[0].height();
		for (int i = 1 ; i < frames.length; i++) {
			if (frames[0].left < left) {
				left = frames[0].left;
			}
			if (frames[0].right > right) {
				left = frames[0].right;
			}
			if (frames[0].top < top) {
				top = frames[0].top;
			}
			if (frames[0].bottom > bottom) {
				bottom = frames[0].bottom;
			}
			if (frames[0].width() > maxWidth) {
				maxWidth = frames[0].width();
			}
			if (frames[0].height() > maxHeight) {
				maxHeight = frames[0].height();
			}
		}

		this.mTexturePositionX = left;
		this.mTexturePositionY = top;
		this.mWidth = right - left;
		this.mHeight = bottom - top;

		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;

		this.frames = frames;

		initTextureBuffer();
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	@Override
	protected void initTextureBuffer() {
		if(frames != null) {
			super.initTextureBuffer();
		}
	}

	//
	//	// ===========================================================
	//	// Getter & Setter
	//	// ===========================================================
	//
	@Override
	public int getTileWidth() {
		return frames[getCurrentTileColumn()].width();
	}

	@Override
	public int getTileHeight() {
		return frames[getCurrentTileColumn()].height();
	}

	@Override
	public int getTexturePositionOfCurrentTileX() {
		return frames[getCurrentTileColumn()].left;
	}

	@Override
	public int getTexturePositionOfCurrentTileY() {
		return frames[getCurrentTileColumn()].top;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public ComplexTiledTextureRegion clone() {
		final ComplexTiledTextureRegion clone = new ComplexTiledTextureRegion(getTexture(), this.frames);
		clone.setCurrentTileIndex(getCurrentTileColumn(), getCurrentTileRow());
		return clone;
	}
}
