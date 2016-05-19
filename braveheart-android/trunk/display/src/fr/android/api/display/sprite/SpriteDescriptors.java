package fr.android.api.display.sprite;


import java.util.ArrayList;
import java.util.Collection;

public class SpriteDescriptors {
	public Collection<SpriteDescriptor> descriptors;

	private String assetPath;
	private Object type;
	private Object subType;

	private SpriteDescriptor currentDescriptor;

	public SpriteDescriptors() {
		descriptors = new ArrayList<SpriteDescriptor>();
	}

	public SpriteDescriptors add(String assetPath, Object type, Object subType, int x, int y, int width, int height, int nbRow, int nbLine) {
		descriptors.add(new SpriteDescriptor(assetPath, type, subType, x, y, width, height, nbRow, nbLine));
		this.assetPath = assetPath;
		this.type = type;
		this.subType = subType;
		return this;
	}

	public SpriteDescriptors add(Object type, Object subType, int x, int y, int width, int height, int nbRow, int nbLine) {
		return add(assetPath, type, subType, x, y, width, height, nbRow, nbLine);
	}

	public SpriteDescriptors add(Object subType, int x, int y, int width, int height, int nbRow, int nbLine) {
		return add(assetPath, type, subType, x, y, width, height, nbRow, nbLine);
	}

	public SpriteDescriptors add(int x, int y, int width, int height, int nbRow, int nbLine) {
		return add(assetPath, type, subType, x, y, width, height, nbRow, nbLine);
	}

	public SpriteDescriptors add(Object newType, Object newSubType, Object type, Object subType, int [] numFrame) {
		currentDescriptor = new SpriteDescriptor(newType, newSubType, type, subType, numFrame);
		descriptors.add(currentDescriptor);
		return this;
	}

	public SpriteDescriptors add(Object newType, Object newSubType, int [] numFrame) {
		return add(newType, newSubType, type, subType, numFrame);
	}

	public SpriteDescriptors add(Object newSubType, int [] numFrame) {
		return add(type, newSubType, type, subType, numFrame);
	}

	public SpriteDescriptors add(String assetPath, Object type, Object subType, int[][] frames) {
		currentDescriptor = new SpriteDescriptor(assetPath, type, subType, frames);
		descriptors.add(currentDescriptor);
		this.assetPath = assetPath;
		this.type = type;
		this.subType = subType;
		return this;
	}

	public SpriteDescriptors addTimeFrame(long[] timeFrame) {
		currentDescriptor.timeFrame = timeFrame;
		return this;
	}

	public SpriteDescriptors setAsFlipOf(Object subType) {
		currentDescriptor.flipOf = subType;
		return this;
	}
}
