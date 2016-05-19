package fr.android.api.display.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import fr.android.api.display.SpriteFactory;
import fr.android.api.display.animation.AnimationDescriptor;
import fr.android.api.display.sprite.SpriteDescriptor;
import fr.android.api.display.sprite.SpriteDescriptor.DescriptorType;
import fr.android.api.display.sprite.SpriteDescriptors;
import fr.android.api.display.sprite.drawable.DrawableSprite;
import fr.android.api.display.sprite.entity.EntitySprite;
import fr.android.api.display.sprite.image.ComplexTiledTextureRegion;
import fr.android.api.display.sprite.image.TiledBitmap;
import fr.android.api.util.Logger;

public class SpriteFactoryImpl implements SpriteFactory {
	private Map<String,Bitmap> bitmaps;
	private Map<String,Texture> textures;

	private Map<Object,Map<Object,Descriptor>> descriptors;
	private Map<Object,Map<Object,Descriptor>> flippedDescriptors;

	@Override
	public synchronized void initialize(Context context, SpriteDescriptors spriteDescriptors) {
		if (descriptors == null) {
			if (BufferObjectManager.getActiveInstance() == null) {
				BufferObjectManager.setActiveInstance(new BufferObjectManager());
			}

			descriptors = new HashMap<Object, Map<Object,Descriptor>>();
			flippedDescriptors = new HashMap<Object, Map<Object,Descriptor>>();

			textures = new HashMap<String, Texture>();
			bitmaps = new HashMap<String, Bitmap>();

			for (SpriteDescriptor desc : spriteDescriptors.descriptors) {
				if (desc.typeAsset == DescriptorType.Subasset) {
					Descriptor typeDescriptors = descriptors.get(desc.type).get(desc.parentSubtype);
					addSubtype(desc.type, desc.subType, typeDescriptors.region, typeDescriptors.bitmap, new AnimationDescriptor(desc.numFrame, desc.timeFrame));
				} else if (desc.typeAsset == DescriptorType.Asset) {
					loadAsset(context, desc.assetPath);

					Bitmap bitmap = bitmaps.get(desc.assetPath);
					Texture texture = textures.get(desc.assetPath);

					addSubtype(desc.type, desc.subType, texture, bitmap, desc.x, desc.y, desc.width, desc.height, desc.nbColumn, desc.nbLine, new AnimationDescriptor(desc.numFrame, desc.timeFrame));
				} else {
					loadAsset(context, desc.assetPath);

					Bitmap bitmap = bitmaps.get(desc.assetPath);
					Texture texture = textures.get(desc.assetPath);

					addSubtype(desc.type, desc.subType, texture, bitmap, desc.frames, new AnimationDescriptor(desc.numFrame, desc.timeFrame));
				}
				if (desc.flipOf != null) {
					Map<Object,Descriptor> flipped = flippedDescriptors.get(desc.type);
					if (flipped == null) {
						flipped = new HashMap<Object, SpriteFactory.Descriptor>();
						flippedDescriptors.put(desc.type, flipped);
					}
					flipped.put(desc.flipOf, descriptors.get(desc.type).get(desc.subType).clone(true));
					flipped.put(desc.subType, descriptors.get(desc.type).get(desc.flipOf).clone(true));
				}
			}
			initializeFlippedSpriteDescriptors();
		}
	}

	private void initializeFlippedSpriteDescriptors() {
		for (Entry<Object,Map<Object, SpriteFactory.Descriptor>> bigentry : descriptors.entrySet()) {
			Map<Object,Descriptor> flipped = flippedDescriptors.get(bigentry.getKey());
			if (flipped == null) {
				flipped = new HashMap<Object, SpriteFactory.Descriptor>();
				flippedDescriptors.put(bigentry.getKey(), flipped);
			}
			for (Entry<Object, Descriptor> entry : bigentry.getValue().entrySet()) {
				if (!flipped.containsValue(entry.getKey())) {
					Descriptor descriptor = entry.getValue().clone();
					descriptor.flip();
					flipped.put(entry.getKey(),descriptor);
				}
			}
		}
	}

	private void addSubtype(Object type, Object subtype, Texture texture, Bitmap bitmap, int x, int y, int width, int height, int nbColumn, int nbLine, AnimationDescriptor animation) {
		addSubtype(type, subtype, new TiledTextureRegion(texture, x, y, nbColumn * width, nbLine * height, nbColumn, nbLine), new TiledBitmap(bitmap, x, y, width, height, nbColumn, nbLine), animation);
	}

	private void addSubtype(Object type, Object subType, Texture texture, Bitmap bitmap, int[][] frames, AnimationDescriptor animation) {
		Rect[] rect = new Rect[frames.length];
		for (int i = 0; i < frames.length ; i++) {
			rect[i] = new Rect(frames[i][0], frames[i][1], frames[i][0]+frames[i][2], frames[i][1]+frames[i][3]);
		}
		addSubtype(type, subType, new ComplexTiledTextureRegion(texture, rect), new TiledBitmap(bitmap, rect), animation);
	}

	private void addSubtype(Object type, Object subtype, TiledTextureRegion region, TiledBitmap bitmap, AnimationDescriptor animation) {
		if (!descriptors.containsKey(type)) {
			descriptors.put(type, new HashMap<Object, Descriptor>());
		}
		descriptors.get(type).put(subtype, new Descriptor(region, bitmap, animation));
	}

	private void loadAsset(Context context, String assetPath) {
		Bitmap bitmap = bitmaps.get(assetPath);
		Texture texture = textures.get(assetPath);
		if (bitmap == null) {
			try {
				bitmap = BitmapFactory.decodeStream(context.getAssets().open(assetPath));
			} catch (IOException e) {
				throw Logger.t("SpriteFactory", "Cannot find asset" + assetPath,e);
			}
			bitmaps.put(assetPath, bitmap);
		}
		if (texture == null) {
			BitmapTextureAtlas atlas = new BitmapTextureAtlas((int) Math.pow(2,Math.floor(Math.log(bitmap.getWidth())/Math.log(2)+1)), (int) Math.pow(2,Math.floor(Math.log(bitmap.getHeight())/Math.log(2)+1)),TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			atlas.addTextureAtlasSource(new AssetBitmapTextureAtlasSource(context, assetPath), 0, 0);
			texture = atlas;
			textures.put(assetPath, texture);
		}
	}

	@Override
	public Texture[] getTextures() {
		return textures.values().toArray(new Texture[textures.size()]);
	}

	@Override
	public TiledTextureRegion getRegion(Object type, Object subType) {
		return descriptors.get(type).get(subType).region;
	}

	@Override
	public Collection<Object> getAllType() {
		return descriptors.keySet();
	}

	@Override
	public Map<Object, Descriptor> getSpriteDescriptors(Object type) {
		return descriptors.get(type);
	}

	@Override
	public Map<Object, Descriptor> getFlippedSpriteDescriptors(Object type) {
		return flippedDescriptors.get(type);
	}

	@Override
	public EntitySprite getEntitySprite(Object type) {
		return new EntitySprite(type);
	}

	@Override
	public DrawableSprite getDrawableSprite(Object type) {
		return new DrawableSprite(type);
	}
}
