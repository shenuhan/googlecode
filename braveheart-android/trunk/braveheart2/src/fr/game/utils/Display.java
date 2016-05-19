package fr.game.utils;

import fr.game.basic.FPosition;
import fr.game.basic.Position;
import fr.game.battlefield.Battlefield;
import fr.game.battlefield.Tile;

public class Display {
	final private Battlefield battlefield;
	final int[][] heights;
	final float[][] fightersHeights;

	public Display(Battlefield b) {
		this.battlefield = b;
		heights = new int[battlefield.getWidth()+1][battlefield.getHeight()+1];
		fightersHeights = new float[battlefield.getWidth()][battlefield.getHeight()];

		Tile[][] tiles = battlefield.getTiles();
		for (int i = 0; i < battlefield.getWidth()+1; i++) {
			for (int j = 0; j < battlefield.getHeight()+1; j++) {
				int min = tiles[i][j].getK();
				if (j == battlefield.getHeight()) {
					if (i == battlefield.getWidth())
						heights[i][j] = min;
					else
						heights[i][j] = Math.min(min, tiles[i+1][j].getK());
				} else {
					min = Math.min(min, tiles[i][j+1].getK());
					if (i == battlefield.getWidth())
						heights[i][j] = min;
					else
						heights[i][j] = Math.min(min, Math.min(tiles[i+1][j].getK(), tiles[i+1][j+1].getK()));
				}
			}
		}

		for (int i = 0; i < battlefield.getWidth(); i++) {
			for (int j = 0; j < battlefield.getHeight(); j++) {
				int min = getK(i, j);
				if (tiles[i][j].getK() > min || tiles[i][j+1].getK() > min || tiles[i+1][j].getK() > min || tiles[i+1][j+1].getK() > min) {
					fightersHeights[i][j] = min + 0.75f;
				} else {
					fightersHeights[i][j] = min;
				}
			}
		}
	}

	public int getTileK(int i, int j) {
		return battlefield.getTiles()[i][j].getK();
	}

	public int getK(int i, int j) {
		return heights[i][j];
	}

	public float getFighterK(Position pos) {
		return fightersHeights[pos.i][pos.j];
	}

	public float getFighterK(FPosition pos) {
		int i = (int) pos.x;
		int j = (int) pos.y;
		float xp = pos.x - i;
		float yp = pos.y - j;

		return fightersHeights[i][j]*(1-xp)*(1-yp) + fightersHeights[i+1][j]*xp*(1-yp) + fightersHeights[i][j+1]*(1-xp)*yp + fightersHeights[i+1][j+1]*xp*yp;
	}

	public FPosition fighterPosition(Position pos) {
		return new FPosition(32 * (pos.i + pos.j) + 16, 8 + 16 * ( battlefield.getWidth() - pos.i + pos.j + 2*getFighterK(pos)));
	}

	public FPosition fighterPosition(FPosition pos) {
		return new FPosition(32 * (pos.x + pos.y) + 16, 8 + 16 * ( battlefield.getWidth() - pos.x + pos.y + 2*getFighterK(pos)));
	}

	public FPosition tilePosition(Position pos) {
		return new FPosition(32 * (pos.i + pos.j), 16 * ( battlefield.getWidth() - pos.i + pos.j + 2*getK(pos.i, pos.j)));
	}

	public FPosition fighterMiddlePosition(Position pos, Position dest) {
		if (pos.i == dest.i) {
			if (pos.j == dest.j) {
				return fighterPosition(pos);
			} else if (pos.j < dest.j) {
				Position p = pos;
				pos = dest;
				dest = p;
			}
			int k1 = getTileK(pos.i, pos.j);
			int k2 = getTileK(pos.i + 1, pos.j);
			if (k1 == k2)
				return new FPosition(32 * (pos.i + pos.j), 16 * ( battlefield.getWidth() - pos.i + pos.j + 2*k1));
			return new FPosition(32 * (pos.i + pos.j), 16 * ( battlefield.getWidth() - pos.i  + pos.j + 2*(Math.min(k1, k2) + 0.75f)));
		}
		if (pos.i < dest.i) {
			Position p = pos;
			pos = dest;
			dest = p;
		}
		int k1 = getTileK(pos.i, pos.j);
		int k2 = getTileK(pos.i, pos.j + 1);
		if (k1 == k2)
			return new FPosition(32 * (pos.i + pos.j), 16 + 16 * ( battlefield.getWidth() - pos.i + pos.j + 2*k1));
		return new FPosition(32 * (pos.i + pos.j), 16 + 16 * ( battlefield.getWidth() - pos.i + pos.j + 2*(Math.min(k1, k2) + 0.75f)));
	}
}
