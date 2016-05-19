package fr.generate;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import fr.game.Constants;

public class MakeHeightMap {
	static int[][] maps ;
	static float[][] HEIGHTS ;
	static int WIDTH = 0;
	static int HEIGHT = 0;

	public static void main(String[] args) throws IOException {
		MakeHeightMap.WIDTH = 0;
		MakeHeightMap.HEIGHT = 0;
		// on lit une premiere fois map.txt pour savoir la WIDTH et la HEIGHT
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(MakeHeightMap.class.getResourceAsStream("/map.txt")));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] ids = line.split(" ");
				if (ids.length > 0) {
					MakeHeightMap.WIDTH = (short) ids.length;
					MakeHeightMap.HEIGHT++;
				}
			}
			reader.close();
		}

		MakeHeightMap.maps = new int[MakeHeightMap.WIDTH][MakeHeightMap.HEIGHT];
		MakeHeightMap.HEIGHTS = new float[MakeHeightMap.WIDTH][MakeHeightMap.HEIGHT];

		// on lit une deuxieme fois map.txt pour avoir les id des cases
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(MakeHeightMap.class.getResourceAsStream("/map.txt")));
			int j = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				String[] ids = line.split(" ");
				for (int i = 0; i < ids.length; i++) {
					MakeHeightMap.maps[i][j] = Integer.parseInt(ids[i]);
					MakeHeightMap.HEIGHTS[i][j] = Float.MAX_VALUE;
				}
				j++;
			}
			reader.close();
		}

		// on decide que la hauteur de la premiere case flat qu'on trouve est 9 puis on contamine touit ce qu'on peut
		// on contamine ca veut dire que toute les flat a cote on la meme taille et si il y a des stairs alors :
		// si le stairs mene a un flat qui n a pas encore de hauteur alors chaque marche fait 0.5 de haut
		// sinon chaque marche fait la hauteur totale divier par le nombre de marches
		for (int i=0;i<MakeHeightMap.WIDTH;i++) {
			for (int j=0; j< MakeHeightMap.HEIGHT; j++) {
				if (Constants.flatsIds.contains(MakeHeightMap.maps[i][j]) && MakeHeightMap.HEIGHTS[i][j] == Float.MAX_VALUE) {
					List<Location> stairs = MakeHeightMap.contaminate(9,i,j);
					MakeHeightMap.contaminateStairs(stairs);
				}
			}
		}

		// chaque case qui n'a pas encore de hauteur a la hauteur d une case adjacente qui en a une + 1
		// on contnue tant qu il y a des case sans hauteur
		MakeHeightMap.finalizeHeight();

		// on ecrit le resultat dans height.txt
		File file = new File("target/height.txt");
		BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

		for(int j=0;j<MakeHeightMap.HEIGHT;j++) {
			for(int i=0;i<MakeHeightMap.WIDTH;i++) {
				w.write(MakeHeightMap.HEIGHTS[i][j] == Float.MAX_VALUE ? "i   " : new DecimalFormat("00.0").format(MakeHeightMap.HEIGHTS[i][j]).replace(',', '.'));
				w.write(" ");
			}
			w.newLine();
		}
		w.close();
	}

	// une classe qui stock a la fois la position d'une case et la hauteur qu on veut lui affecter
	static private class HeightLocation extends Location {
		final public float height;
		public HeightLocation(int i, int j, float height) {
			super(i,j);
			this.height = height;
		}
	}

	// pour mettre des hauteur sur toutes les cases ni flat ni stairs
	private static void finalizeHeight() {
		boolean shouldContinue = true;
		List<HeightLocation> locations = new ArrayList<MakeHeightMap.HeightLocation>();
		while (shouldContinue) {
			shouldContinue = false;
			// pour toutes les cases qui ont une hauteur (height != Float.MAX_VALUE) cette hauteur + 1 au case adjacente qui sont des arbres
			for (int i=0;i<MakeHeightMap.WIDTH;i++) {
				for (int j=0; j< MakeHeightMap.HEIGHT; j++) {
					if (MakeHeightMap.HEIGHTS[i][j] != Float.MAX_VALUE) {
						float height = MakeHeightMap.HEIGHTS[i][j] + (Constants.treeIds.contains(MakeHeightMap.maps[i][j]) ? 0f : 1f);
						MakeHeightMap.addLocation(locations,i + 1, j, height);
						MakeHeightMap.addLocation(locations,i - 1, j, height);
						MakeHeightMap.addLocation(locations,i, j + 1, height);
						MakeHeightMap.addLocation(locations,i, j - 1, height);
					}
				}
			}
			// si le cases adjacente en question n'ont pas encore de hauteur et sont des arbres on affecte
			for (HeightLocation l : locations) {
				if (MakeHeightMap.HEIGHTS[l.i][l.j] == Float.MAX_VALUE && Constants.treeIds.contains(MakeHeightMap.maps[l.i][l.j])) {
					MakeHeightMap.HEIGHTS[l.i][l.j] = l.height;
					// si on affecte au moins une fois, on refait un cycle
					shouldContinue = true;
				}
			}
			locations.clear();
		}

		shouldContinue = true;
		while (shouldContinue) {
			shouldContinue = false;
			// pour toutes les cases qui ont une hauteur (height != Float.MAX_VALUE) on dit que l'on veut affecter cette hauteur au case adjacente
			for (int i=0;i<MakeHeightMap.WIDTH;i++) {
				for (int j=0; j< MakeHeightMap.HEIGHT; j++) {
					if (MakeHeightMap.HEIGHTS[i][j] != Float.MAX_VALUE) {
						float height = MakeHeightMap.HEIGHTS[i][j] + 1;
						MakeHeightMap.addLocation(locations,i + 1, j, height);
						MakeHeightMap.addLocation(locations,i - 1, j, height);
						MakeHeightMap.addLocation(locations,i, j + 1, height);
						MakeHeightMap.addLocation(locations,i, j - 1, height);
					}
				}
			}
			// si le cases adjacente en question n'ont pas encore de hauteur onn affecte
			for (HeightLocation l : locations) {
				if (MakeHeightMap.HEIGHTS[l.i][l.j] == Float.MAX_VALUE || MakeHeightMap.HEIGHTS[l.i][l.j] < l.height) {
					MakeHeightMap.HEIGHTS[l.i][l.j] = l.height;
					// si on affecte au moins une fois, on refait un cycle
					shouldContinue = true;
				}
			}
			locations.clear();
		}
	}

	private static void addLocation(List<HeightLocation> locations, int i, int j, float height) {
		if (i >= 0 && i < MakeHeightMap.WIDTH && j >= 0 && j < MakeHeightMap.HEIGHT && MakeHeightMap.HEIGHTS[i][j] == Float.MAX_VALUE) {
			locations.add(new HeightLocation(i, j, height));
		}
	}

	private static void contaminateStairs(List<Location> stairs) {
		// contamination des stairs
		for (Location l : stairs) {
			if (MakeHeightMap.HEIGHTS[l.i][l.j] != Float.MAX_VALUE) {
				continue;
			}

			int i = l.i;
			int up = l.j, down = l.j;
			while (up < MakeHeightMap.HEIGHT && Constants.stairsIds.contains(MakeHeightMap.maps[i][up]) && up < MakeHeightMap.HEIGHT) up++;
			while (down > 0 && Constants.stairsIds.contains(MakeHeightMap.maps[i][down]) && down > 0) down--;

			// si le stairs mene a un flat qui n a pas encore de hauteur alors chaque marche fait 0.5 de haut
			if (up == MakeHeightMap.HEIGHT || MakeHeightMap.HEIGHTS[i][up] == Float.MAX_VALUE) {
				float height = MakeHeightMap.HEIGHTS[i][down] - 0.5f;
				down++;
				while(down < up) {
					MakeHeightMap.HEIGHTS[i][down] = height;
					down++;
					height -= 0.5f;
				}
				if (up < MakeHeightMap.HEIGHT) {
					MakeHeightMap.contaminateStairs(MakeHeightMap.contaminate(height, i, up));
				}
			// si le stairs mene a un flat qui n a pas encore de hauteur alors chaque marche fait 0.5 de haut
			} else if (down < 0 || MakeHeightMap.HEIGHTS[i][down] == Float.MAX_VALUE) {
				float height = MakeHeightMap.HEIGHTS[i][up] + 0.5f;
				up--;
				while(down < up) {
					MakeHeightMap.HEIGHTS[i][up] = height;
					up--;
					height += 0.5f;
				}
				if (down > 0) {
					MakeHeightMap.contaminateStairs(MakeHeightMap.contaminate(height, i, down));
				}
			// sinon chaque marche fait la hauteur totale divier par le nombre de marches
			} else {
				float delta = (MakeHeightMap.HEIGHTS[i][down] - MakeHeightMap.HEIGHTS[i][up]) / (up - down + 1);
				up--;
				while(down < up) {
					MakeHeightMap.HEIGHTS[i][up] = MakeHeightMap.HEIGHTS[i][down] - delta * (up-down);
					up--;
				}
			}
		}
	}

	// class qui permet de stoque une position
	static private class Location {
		int i;
		int j;

		public Location(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}

	private static List<Location> contaminate(float height, int i, int j) {
		// on contamine i j avec la hauteur height puis on tente la contamination des adjacents
		List<Location> stairs = new ArrayList<MakeHeightMap.Location>();
		Queue<Location> todo = new LinkedList<MakeHeightMap.Location>();
		todo.add(new Location(i, j));

		while(todo.size() > 0) {
			Location l = todo.poll();

			// si i ou j est hors map ou qu il a deja une hauteur (height != Float.MAX_VALUE) on ne fait rien
			if (l.i<0 || l.j<0 || l.i >= MakeHeightMap.WIDTH || l.j >= MakeHeightMap.HEIGHT || MakeHeightMap.HEIGHTS[l.i][l.j] != Float.MAX_VALUE)
				continue;

			// si i j est un flat on lui donne la hauteur height puis on lance le traitement sur les adjacents
			if (Constants.flatsIds.contains(MakeHeightMap.maps[l.i][l.j])) {
				MakeHeightMap.HEIGHTS[l.i][l.j] = height - (Constants.waterIds.contains(MakeHeightMap.maps[l.i][l.j]) ? 0.4f : 0f);
				todo.add(new Location(l.i + 1, l.j));
				todo.add(new Location(l.i - 1, l.j));
				todo.add(new Location(l.i, l.j + 1));
				todo.add(new Location(l.i, l.j - 1));
			// si i j est un stairs on l'ajoute à la liste des stairs a traiter plus tard !
			} else if (Constants.stairsIds.contains(MakeHeightMap.maps[l.i][l.j])) {
				stairs.add(l);
			}
		}
		return stairs;
	}
}
