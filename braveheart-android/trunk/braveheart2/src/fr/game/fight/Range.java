package fr.game.fight;

public class Range {
	private int min;
	private int max;

	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}

	public Range(int min) {
		this.min = this.max = min;
	}
	public Range(int min, int max) {
		this.min = min;
		this.max =  max;
	}

	public boolean contains(int i) {
		return i >= min && i <= max;
	}
}
