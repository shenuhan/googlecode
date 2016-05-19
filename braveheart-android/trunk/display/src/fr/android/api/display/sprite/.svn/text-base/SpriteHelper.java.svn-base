package fr.android.api.display.sprite;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.android.api.Api;
import fr.android.api.annotation.Managed;
import fr.android.api.annotation.Singleton;
import fr.android.api.display.SpriteFactory;
import fr.android.api.display.SpriteFactory.Descriptor;
import fr.android.api.util.Logger;

@Managed
public class SpriteHelper {
	@Singleton
	SpriteFactory factory;

	private final Map<Object, Descriptor> descriptors;
	private final Object type;
	private final SubtypeChangeListener listener;

	private boolean isFlipped;
	private Map<Object, Descriptor> flippedDescriptors;

	private Object currentSubtype;

	public interface SubtypeChangeListener {
		void onSubtypeChanged(Object subtype, Descriptor descriptor);
	}

	public SpriteHelper(Object type, SubtypeChangeListener listener) {
		Api.manage(this);

		this.type = type;
		this.descriptors = new HashMap<Object, SpriteFactory.Descriptor>();
		for (Entry<Object, Descriptor> entry : factory.getSpriteDescriptors(type).entrySet()) {
			descriptors.put(entry.getKey(), entry.getValue().clone());
		}
		this.currentSubtype = null;
		this.listener = listener;

		this.isFlipped = false;
		this.flippedDescriptors = null;
	}

	public void setSubType(Object subType) {
		if (currentSubtype != subType) {
			currentSubtype = subType;
			if (! descriptors.containsKey(subType)) {
				throw Logger.t("SpriteHelper","Cannot find subtype " + subType.toString() + " for type " + type.toString());
			}
			Logger.e("Sprite", "Change Sub Type " + type.toString() + "/" + subType.toString());
			listener.onSubtypeChanged(subType, isFlipped ? flippedDescriptors.get(subType) : descriptors.get(subType));
		}
	}

	public boolean isFlipped() {
		return isFlipped;
	}

	public Object getSubtype() {
		return currentSubtype;
	}

	public Object getType() {
		return type;
	}

	public Collection<Object> getSubtypes() {
		return descriptors.keySet();
	}

	public void flip() {
		if (flippedDescriptors == null) {
			this.flippedDescriptors = new HashMap<Object, SpriteFactory.Descriptor>();
			for (Entry<Object, Descriptor> entry : factory.getFlippedSpriteDescriptors(type).entrySet()) {
				flippedDescriptors.put(entry.getKey(), entry.getValue().clone());
			}
		}
		isFlipped = !isFlipped;
		listener.onSubtypeChanged(currentSubtype, isFlipped ? flippedDescriptors.get(currentSubtype) : descriptors.get(currentSubtype));
	}
}
