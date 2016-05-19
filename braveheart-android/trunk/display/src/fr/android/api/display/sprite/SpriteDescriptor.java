package fr.android.api.display.sprite;

public class SpriteDescriptor {
	public enum DescriptorType {
		Asset,
		Subasset,
		ComplexAsset
	}

	public DescriptorType typeAsset;

	public String assetPath;
	public Object type;
	public Object subType;
	public int x;
	public int y;
	public int width;
	public int height;
	public int nbColumn;
	public int nbLine;

	public Object parentType;
	public Object parentSubtype;
	public int[] numFrame;

	public int[][] frames;

	public long[] timeFrame = null;

	public Object flipOf = null;

	SpriteDescriptor(String assetPath, Object type, Object subType, int x, int y, int width, int height, int nbColumn, int nbLine) {
		this.typeAsset = DescriptorType.Asset;

		this.assetPath = assetPath;
		this.type = type;
		this.subType = subType;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.nbColumn = nbColumn;
		this.nbLine = nbLine;
	}

	SpriteDescriptor(Object type, Object subType, Object parentType,Object parentSubtype,int...numFrame) {
		this.typeAsset = DescriptorType.Subasset;

		this.type = type;
		this.subType = subType;
		this.parentType = parentType;
		this.parentSubtype = parentSubtype;
		this.numFrame = numFrame;
	}

	SpriteDescriptor(String assetPath, Object type, Object subType, int[][] frames) {
		this.typeAsset = DescriptorType.ComplexAsset;

		this.assetPath = assetPath;
		this.type = type;
		this.subType = subType;

		this.frames = frames;
	}
}