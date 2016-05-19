package fr.android.api.display.sprite.drawable;


import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import fr.android.api.display.animation.AnimationDescriptor;
import fr.android.api.display.sprite.image.TiledBitmap;


public class LayoutSprite extends AnimationDrawable {
	private final TiledBitmap bitmap;
	private final AnimationDescriptor descriptor;

	public LayoutSprite(TiledBitmap bitmap, AnimationDescriptor descriptor) {
		this.bitmap = bitmap;
		this.descriptor = descriptor;
		this.setOneShot(false);
	}

	public synchronized LayoutSprite anim() {
		return anim(0, 0, TiledBitmap.FIT, 0);
	}

	public synchronized LayoutSprite anim(long step) {
		return anim(0, 0, TiledBitmap.FIT, step);
	}

	public synchronized LayoutSprite anim(int x, int y, long step) {
		return anim(x, y, TiledBitmap.FIT, step);
	}

	public synchronized LayoutSprite anim(float ratio, int step) {
		return anim(0, 0, ratio, step);
	}

	public synchronized LayoutSprite anim(int x, int y, float ratio, long step) {
		return anim(x, y, ratio, ratio, step);
	}

	public synchronized LayoutSprite anim(int x, int y, float ratioX, float ratioY, long step) {
		if (!isRunning()) {
			fill(x,y,ratioX,ratioY,step);
			this.start();
		}
		return this;
	}

	public synchronized LayoutSprite show() {
		return show(0, 0, TiledBitmap.FIT);
	}


	public synchronized LayoutSprite show(float ratio) {
		return show(0, 0, ratio);
	}


	public synchronized LayoutSprite show(int x, int y) {
		return show(x, y, TiledBitmap.FIT);
	}

	public synchronized LayoutSprite show(int x, int y, float ratio) {
		return show(x, y, ratio, ratio);
	}

	public synchronized LayoutSprite show(int x, int y, float ratioX, float ratioY) {
		this.setOneShot(true);
		addFrame(bitmap.getDrawable(0, x, y, ratioX, ratioY), 1);
		this.start();
		return this;
	}

	private void fill(int x, int y, float ratioX, float ratioY, long step) {
		if (descriptor.numFrame == null) {
			if (descriptor.timeFrame == null) {
				for (int numPicture = this.getNumberOfFrames(); numPicture < bitmap.getNbPicture(); numPicture++) {
					addFrame(bitmap.getDrawable(numPicture, x, y, ratioX, ratioY), (int) step);
				}
			} else {
				for (int numPicture = this.getNumberOfFrames(); numPicture < descriptor.timeFrame.length; numPicture++) {
					addFrame(bitmap.getDrawable(numPicture, x, y, ratioX, ratioY), (int) descriptor.timeFrame[numPicture]);
				}
			}
		} else {
			if (descriptor.timeFrame == null) {
				for (int numPicture = this.getNumberOfFrames(); numPicture < descriptor.numFrame.length; numPicture++) {
					addFrame(bitmap.getDrawable(descriptor.numFrame[numPicture], x, y, ratioX, ratioY), (int) step);
				}
			} else {
				int min = descriptor.timeFrame.length < descriptor.numFrame.length ? descriptor.timeFrame.length : descriptor.numFrame.length;
				for (int numPicture = this.getNumberOfFrames(); numPicture < min; numPicture++) {
					addFrame(bitmap.getDrawable(descriptor.numFrame[numPicture], x, y, ratioX, ratioY), (int) descriptor.timeFrame[numPicture]);
				}
			}
		}
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	public int getHeight() {
		return bitmap.getHeight();
	}

	public int getWidth() {
		return bitmap.getWidth();
	}

}
