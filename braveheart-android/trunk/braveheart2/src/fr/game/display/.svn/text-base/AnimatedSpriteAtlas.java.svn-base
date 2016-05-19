package fr.game.display;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AnimatedSpriteAtlas {
	public class AnimationInfo {
		public String spriteName;
		public int[] keyFrames;
		public float[] duration;
	}

	public AnimatedSpriteAtlas(TextureAtlas atlas, FileHandle packFile) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(packFile.read()), 64);
		map = new HashMap<Object, SpriteFactory>();
		String line;
		List<String> keys = new ArrayList<String>();

		SpriteFactory spriteFactory = null;
		try {
			line = reader.readLine();
			while(line != null) {
				int pos = line.indexOf("-->");
				if (pos > 0) {
					String key = line.substring(pos + 3).trim();
					spriteFactory.duplicate(keys, key);
					line = reader.readLine();
					continue;
				}
				pos = line.indexOf(':');
				if (pos < 0) {
					int depth = 0;
					while(line.startsWith("\t")) {
						line = line.substring(1);
						depth++;
					}
					if (depth == 0) {
						spriteFactory = new SpriteFactory(line.trim(), atlas);
						map.put(spriteFactory.getName(), spriteFactory);
					} else {
						while(depth <= keys.size()) {
							keys.remove(keys.size()-1);
						}
						keys.add(line.trim());
					}
					line = reader.readLine();
				} else {
					AnimationInfo info = new AnimationInfo();
					while(pos > 0) {
						String key = line.substring(0,pos).trim();
						String value = line.substring(pos+1).trim();

						if (key.equals("keyFrames")) {
							String[] split = value.split(",");
							info.keyFrames = new int [split.length];
							for (int i=0;i<split.length;i++)
								info.keyFrames[i] = Integer.parseInt(split[i].trim());
						} else if (key.equals("sprite")) {
							info.spriteName = value;
						} else if (key.equals("duration")) {
							String[] split = value.split(",");
							info.duration = new float[split.length];
							float sum = 0f;
							for (int i = 0; i < split.length; i++) {
								info.duration[i] = Float.parseFloat(split[i].trim());
								sum = info.duration[i] + sum;
							}
							for (int i = 0; i < split.length; i++)
								info.duration[i] /= sum;
						} else {
							throw new RuntimeException("Unknown type of value " + key);
						}
						line = reader.readLine();
						pos = line == null ? -1 : line.indexOf(':');
					}
					spriteFactory.add(keys, info);
				}
			} while(line != null);
		} catch (IOException e) {
			throw new RuntimeException("Cannot read file " + packFile.name());
		}
	}

	public Collection<Object> getSpritesTypes() {
		return map.keySet();
	}

	final private HashMap<Object, SpriteFactory> map;
	public SpriteFactory getSpriteFactory(Object type) {
		SpriteFactory factory = map.get(type);
		if (factory == null) {
			factory = map.get(type.toString());
			if (factory == null) {
				if (type instanceof Enum<?>) {
					String name = ((Enum<?>) type).name();
					factory = map.get(name);
				}
				if (factory == null) throw new RuntimeException("Cannot find key : " + type);
			}
			map.put(type, factory);
		}

		return factory.clone();
	}
}
