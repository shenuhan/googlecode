package fr.android.api.display.sprite;


import java.util.Collection;

public interface Sprite<T> {
	public enum Fitting {Fit, FitMax, Scale}

	static public final String DefaultSubtype = "All";

	void setSubType(Object subType);
	Object getSubtype();
	Collection<Object> getSubtypes();

	void flip();

	T show(float x, float y, float scale);
	T show(float x, float y);
	T show(float scale);
	T show();
	T anim(float x, float y, float scale, int step);
	T anim(float x, float y, int step);
	T anim(float ratio, int step);
	T anim(int step);

	T show(float width, float height, Fitting fitting);
}
