package fr.android.api.display;


import java.util.Collection;
import java.util.Map;

import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;
import fr.android.api.annotation.Singleton;
import fr.android.api.display.animation.AnimationDescriptor;
import fr.android.api.display.sprite.SpriteDescriptors;
import fr.android.api.display.sprite.drawable.DrawableSprite;
import fr.android.api.display.sprite.entity.EntitySprite;
import fr.android.api.display.sprite.image.TiledBitmap;

@Singleton
public interface SpriteFactory {
	class Descriptor {
		final public TiledTextureRegion region;
		final public TiledBitmap bitmap;
		final public AnimationDescriptor animation;
		public boolean isFlipped;

		public Descriptor(TiledTextureRegion region, TiledBitmap bitmap, AnimationDescriptor animation) {
			this(region, bitmap, animation, false);
		}

		private Descriptor(TiledTextureRegion region, TiledBitmap bitmap, AnimationDescriptor animation, boolean isFlipped) {
			this.region = region;
			this.bitmap = bitmap;
			this.animation = animation;
			this.isFlipped = isFlipped;
		}

		@Override
		public Descriptor clone() {
			return clone(isFlipped);
		}

		public Descriptor clone(boolean isFlipped) {
			TiledTextureRegion region = this.region.clone();
			region.setFlippedHorizontal(this.region.isFlippedHorizontal());
			region.setFlippedVertical(this.region.isFlippedVertical());
			return new Descriptor(region, bitmap.clone(), animation, isFlipped);
		}

		public void flip() {
			isFlipped = !isFlipped;
			region.setFlippedHorizontal(!region.isFlippedHorizontal());
			bitmap.flip();
		}
	}

	void initialize(Context context, SpriteDescriptors spriteDescriptors);

	Texture[] getTextures();
	TiledTextureRegion getRegion(Object type, Object subType);

	EntitySprite getEntitySprite(Object type);
	DrawableSprite getDrawableSprite(Object type);

	Map<Object, Descriptor> getSpriteDescriptors(Object type);
	Map<Object, Descriptor> getFlippedSpriteDescriptors(Object type);

	Collection<Object> getAllType();
}
