package fr.game.display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import fr.game.display.AnimatedSpriteAtlas.AnimationInfo;

public class SpriteFactory implements ISpriteFactory {
	static private class Storage {
		public AnimationInfo info;
		public Map<Object, Storage> map;
	}

	final private Map<Object, Storage> storages;
	final private String name;
	final private TextureAtlas atlas;

	private Animation animation;

	public SpriteFactory(String name, TextureAtlas atlas) {
		this.name = name;
		this.atlas = atlas;
		this.storages = new HashMap<Object, SpriteFactory.Storage>();
		this.animation = null;
	}

	private SpriteFactory(SpriteFactory factory) {
		this.name = factory.name;
		this.atlas = factory.atlas;
		this.storages = factory.storages;
		this.animation = null;
	}

	@Override
	public SpriteFactory clone() {
		return new SpriteFactory(this);
	}

	public void add(List<String> keys, AnimationInfo info) {
		Map<Object, Storage> current = storages;
		Storage storage = null;

		for (String key : keys) {
			if (current == null && storage != null) {
				storage.map = new HashMap<Object, SpriteFactory.Storage>();
				current = storage.map;
			}
			storage = current.get(key);
			if (storage == null) {
				storage = new Storage();
				current.put(key, storage);
			}
			current = storage.map;
		}
		storage.info = info;
	}

	public void duplicate(List<String> keys, String identical) {
		Map<Object, Storage> current = storages;

		int length = keys.size() - 1;
		for (String key : keys) {
			if (length == 0) {
				Storage s = current.get(identical);
				if (s == null) throw new RuntimeException("Cannot find key : " + identical + " to put in " + key);
				storages.put(key, s);
			} else {
				current = current.get(key).map;
				length--;
			}
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public Animation setSprite(float duration, Object... keys) {
		Map<Object, Storage> current = storages;
		Storage storage = null;
		for (Object key : keys) {
			storage = current.get(key);
			if (storage == null) {
				storage = current.get(key.toString());
				if (storage == null) {
					if (key instanceof Enum<?>) {
						String name = ((Enum<?>) key).name();
						storage = current.get(name);
					}
					if (storage == null) throw new RuntimeException("Cannot find key : " + key);
				}
				current.put(key, storage);
			}

			current = storage.map;
		}
		if (animation == null)
			animation = new Animation(atlas, storage.info, duration);
		else
			animation.update(storage.info, duration);
		return new Animation(atlas, storage.info, duration);
	}

	public List<List<String>> getKeys() {
		return getKeys(storages);
	}

	private List<List<String>> getKeys(Map<Object, Storage> current) {
		List<List<String>> res = new ArrayList<List<String>>();
		for (Object oKey : current.keySet()) {
			if (oKey instanceof String) {
				String key = (String) oKey;
				Storage s = current.get(key);
				if (s.info != null) {
					List<String> r = new ArrayList<String>();
					r.add(key);
					res.add(r);
				}
				if (s.map != null) {
					for (List<String> r : getKeys(s.map)) {
						r.add(0, key);
						res.add(r);
					}
				}
			}
		}
		return res;
	}

	@Override
	public Animation getSprite() {
		return animation;
	}
}
