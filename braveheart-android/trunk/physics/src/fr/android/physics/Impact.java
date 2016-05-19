package fr.android.physics;

public interface Impact {
	public FieldObject getFirst();
	public FieldObject getSecond();
	
	public void applyImpact();
	
	public float getTime();
}
