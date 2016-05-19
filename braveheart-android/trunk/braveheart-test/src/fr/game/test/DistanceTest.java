package fr.game.test;

import android.test.AndroidTestCase;
import fr.android.api.Api;
import fr.game.engine.basics.Point;
import fr.game.engine.map.BattleField;
import fr.game.engine.map.Tile;

public class DistanceTest extends AndroidTestCase {
	static final double DELTA = 0.0001;

	/**
	 * Distances 3 x 3
	 *   0 1 2 3 4 5 6
	 * 0  4 3 3 3 3 4 5
	 * 1 4 3 2 2 2 3 4
	 * 2  3 2 1 1 2 3 4
	 * 3 3 2 1 0 1 2 3
	 * 4  3 2 1 1 2 3 4
	 * 5 4 3 2 2 2 3 4
	 * 6  4 3 3 3 3 4 5
	 */

	static final int[][] distance3x3 =
		{
		{4,3,3,3,3,4,5},
		{4,3,2,2,2,3,4},
		{3,2,1,1,2,3,4},
		{3,2,1,0,1,2,3},
		{3,2,1,1,2,3,4},
		{4,3,2,2,2,3,4},
		{4,3,3,3,3,4,5}
		};

	/**
	 * Distances 2 x 2
	 *   0 1 2 3 4 5 6
	 * 0  3 2 2 2 3 4 5
	 * 1 3 2 1 1 2 3 4
	 * 2  2 1 0 1 2 3 4
	 * 3 3 2 1 1 2 3 4
	 * 4  3 2 2 2 3 4 5
	 * 5 4 3 3 3 3 4 5
	 * 6  4 4 4 4 4 5 6
	 */

	static final int[][] distance2x2 =
		{
		{3,2,2,2,3,4,5},
		{3,2,1,1,2,3,4},
		{2,1,0,1,2,3,4},
		{3,2,1,1,2,3,4},
		{3,2,2,2,3,4,5},
		{4,3,3,3,3,4,5},
		{4,4,4,4,4,5,6},
		};

	public void test() throws Throwable{
		Api.start();
		BattleField battleField = Api.make(BattleField.class, 7, 7);

		Tile tile2x2 = battleField.getDistantTiles(Api.make(Point.class, 2,2),0).iterator().next();
		Tile tile3x3 = battleField.getDistantTiles(Api.make(Point.class, 3,3),0).iterator().next();

		for (int j = 0 ; j < distance2x2.length ; j++) {
			for (int i = 0 ; i < distance2x2[j].length ; i++) {
				Tile tile = battleField.getDistantTiles(Api.make(Point.class, i,j),0).iterator().next();
				int distance = battleField.getDistance(tile2x2, tile);
				assertEquals("position " + i + "x" + j, distance2x2[j][i], distance);
				distance = battleField.getDistance(tile, tile2x2);
				assertEquals("position " + i + "x" + j, distance2x2[j][i], distance);
			}
		}

		for (int j = 0 ; j < distance2x2.length ; j++) {
			for (int i = 0 ; i < distance2x2[j].length ; i++) {
				Tile tile = battleField.getDistantTiles(Api.make(Point.class, i,j),0).iterator().next();
				int distance = battleField.getDistance(tile3x3, tile);
				assertEquals("position " + i + "x" + j, distance3x3[j][i], distance);
				distance = battleField.getDistance(tile, tile3x3);
				assertEquals("position " + i + "x" + j, distance3x3[j][i], distance);
			}
		}
	}
}
