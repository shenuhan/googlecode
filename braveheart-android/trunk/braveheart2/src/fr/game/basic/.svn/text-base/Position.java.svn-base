package fr.game.basic;

public class Position {
	public int i;
	public int j;

	public Position(int i, int j) {
		this.i = i;
		this.j = j;
	}

	@Override
	public String toString() {
		return String.format("[%d,%d]",i,j);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Position) {
			Position pos = (Position) obj;
			return pos.i == i && pos.j == j;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return i*1000000 + j;
	}

	public int getDistance(Position p) {
		return Math.abs(this.i - p.i) + Math.abs(this.j - p.j);

	}
}
