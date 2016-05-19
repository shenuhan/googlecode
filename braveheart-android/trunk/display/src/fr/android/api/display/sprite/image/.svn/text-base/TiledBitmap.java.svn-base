package fr.android.api.display.sprite.image;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class TiledBitmap {
	final static public float FIT = 1f;

	private Bitmap bitmap;
	private final int x;
	private final int y;
	private final int width;
	private final int height;
	private final int nbColumn;
	private final int nbLine;
	private final Rect[] frames;

	private boolean shouldFlip;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getNbColumn() {
		return nbColumn;
	}

	public int getNbLine() {
		return nbLine;
	}

	public Rect[] getFrames() {
		return frames;
	}

	public TiledBitmap(Bitmap bitmap, int x, int y, int width, int height, int nbColumn, int nbLine) {
		this(bitmap, x, y, width, height, nbColumn, nbLine, false);
	}

	private TiledBitmap(Bitmap bitmap, int x, int y, int width, int height, int nbColumn, int nbLine, boolean shouldFlip) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.nbColumn = nbColumn;
		this.nbLine = nbLine;
		this.frames = null;
		this.shouldFlip = shouldFlip;
	}

	public TiledBitmap(Bitmap bitmap, Rect[] frames) {
		this(bitmap, frames, false);
	}

	private TiledBitmap(Bitmap bitmap, Rect[] frames, boolean shouldFlip) {
		this.bitmap = bitmap;
		this.x = frames[0].left;
		this.y = frames[0].top;
		int width = frames[0].right - frames[0].left;
		int height = frames[0].bottom - frames[0].top;
		for (int i = 1; i < frames.length; i++) {
			if (width < frames[i].right - frames[i].left)
				width = frames[i].right - frames[i].left;
			if (height < frames[i].bottom - frames[i].top)
				height = frames[i].bottom - frames[i].top;
		}
		this.width = width;
		this.height = height;
		this.nbColumn = frames.length;
		this.nbLine = 1;
		this.frames = frames;
		this.shouldFlip = shouldFlip;
	}

	public int getNbPicture() {
		return nbColumn * nbLine;
	}

	public Drawable getDrawable(int index, int x, int y, float ratio) {
		return getDrawable(index, x, y, ratio, ratio);
	}

	public Drawable getDrawable(int index, int x, int y, float ratioX, float ratioY) {
		effectiveFlip();
		Rect src;
		int width,height;
		if (frames == null) {
			final int i = index % nbColumn;
			final int j = index / nbColumn;

			width = (int) (this.width * ratioX);
			height = (int) (this.height * ratioY);

			src = new Rect(this.x + i * this.width , this.y + j * this.height, this.x + i * this.width + this.width, this.y + j * this.height + this.height);
		} else {
			width = (int) (frames[index].width()* ratioX);
			height = (int) (frames[index].height() * ratioY);

			src = frames[index];
		}
		final Rect finalSrc = src;
		final Rect dst = new Rect(x, y, width, height);

		return new Drawable() {
			@Override
			public void draw(Canvas canvas) {
				canvas.drawBitmap(bitmap, finalSrc, dst, null);
			}
			@Override
			public int getOpacity() {
				return 1;
			}
			@Override
			public void setAlpha(int alpha) {
			}
			@Override
			public void setColorFilter(ColorFilter cf) {
			}
		};
	}

	public Drawable getDrawable(int index, int x, int y) {
		return getDrawable(index, x, y, FIT);
	}

	public Drawable getDrawable(int index) {
		return getDrawable(index, 0, 0, FIT);
	}

	@Override
	public TiledBitmap clone() {
		if (frames != null) {
			return new TiledBitmap(bitmap, frames, shouldFlip);
		}
		return new TiledBitmap(bitmap, x, y, width, height, nbColumn, nbLine, shouldFlip);
	}

	public void flip() {
		this.shouldFlip = !this.shouldFlip;
	}

	private void effectiveFlip() {
		if (shouldFlip) {
			shouldFlip = false;
			Matrix matrix = new Matrix();
			matrix.preScale(-1, 1);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), new Matrix(), false);
		}
	}
}
