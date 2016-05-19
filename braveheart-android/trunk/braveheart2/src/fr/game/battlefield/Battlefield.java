package fr.game.battlefield;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.game.basic.Position;
import fr.game.fight.BattleFighter;

public class Battlefield {
	private List<BattleFighter> fighters;
	final private Tile[][] tiles;

	final private int width;
	final private int height;

	private Random r = new Random();
	private int rand() {
		float f = r.nextFloat();
		float probup = .01f;
		float probdown = .01f;
		if (f < probdown) {
			return -1;
		}
		if (f > 1f-probup) {
			return 1;
		}
		return 0;
	}

	public Battlefield(int width, int height) {
		this.width = width;
		this.height = height;

		tiles = new Tile[width + 1][height + 1];

		for(int i = 0 ; i < width + 1; i++) {
			for (int j = 0; j < height + 1; j++) {
				if (i == 0) {
					if (j == 0)
						tiles[i][j] = new Tile(i, j, 0);
					else
						tiles[i][j] = new Tile(i, j, tiles[i][j-1].getK() + rand());
					continue;
				}
				int min = tiles[i-1][j].getK();
				int max = tiles[i-1][j].getK();
				if (j > 0) {
					min = min > tiles[i][j - 1].getK() ? tiles[i][j - 1].getK() : min;
					min = min > tiles[i-1][j - 1].getK() ? tiles[i-1][j - 1].getK() : min;
					max = max < tiles[i][j - 1].getK() ? tiles[i][j - 1].getK() : max;
					max = max < tiles[i-1][j - 1].getK() ? tiles[i-1][j - 1].getK() : max;
				}
				if (j < height) {
					min = min > tiles[i-1][j + 1].getK() ? tiles[i-1][j + 1].getK() : min;
					max = max < tiles[i-1][j + 1].getK() ? tiles[i-1][j + 1].getK() : max;
				}
				if (max - min == 2) {
					tiles[i][j] = new Tile(i, j, min + 1);
					continue;
				}
				if (max == min) {
					tiles[i][j] = new Tile(i, j, min + rand());
					continue;
				}
				if (j > 0 && tiles[i][j - 1].getK() == tiles[i - 1][j].getK() && tiles[i - 1][j - 1].getK() != tiles[i - 1][j].getK()) {
					tiles[i][j] = new Tile(i, j, tiles[i - 1][j].getK());
					continue;
				}
				if (i > 2 && tiles[i - 1][j].getK() != tiles[i - 2][j].getK()) {
					tiles[i][j] = new Tile(i, j, tiles[i - 1][j].getK());
					continue;
				}
				if (j > 2 && tiles[i][j-1].getK() != tiles[i][j-2].getK()) {
					tiles[i][j] = new Tile(i, j, tiles[i][j-1].getK());
					continue;
				}
				tiles[i][j] = new Tile(i, j,r.nextBoolean() ? min : max);
			}
		}
		fighters = new ArrayList<BattleFighter>();
	}

	public List<BattleFighter> getFighters() {
		return fighters;
	}

	public void setFighters(List<BattleFighter> fighters) {
		this.fighters = fighters;
	}

	public void addFighter(BattleFighter fighter, int i, int j) {
		tiles[i][j].setFighter(fighter);
		this.fighters.add(fighter);
	}

	public void moveFighter(BattleFighter fighter, Position pos) {
		tiles[pos.i][pos.j].setFighter(fighter);
		tiles[fighter.getPosition().i][fighter.getPosition().j].setFighter(null);
		fighter.setPosition(pos);

	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
}
