package fr.generate;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class SplitMap {
	// l'intelligence pour savoir si deux cases sont identiques est contenues dans cette classe
	// BufferedImage est la class d'image classique de java
	static class MyImage extends BufferedImage {
		final int hash;
		public MyImage(int width,int height, int i, int j, BufferedImage source) {
			super(width, height, source.getType());
			int hash = 0;
			for(int ii=0;ii < width;ii++)
				for(int jj=0;jj < height;jj++) {
					setRGB(ii, jj, source.getRGB(i+ii, j+jj));
					hash += (ii + 1)*(1000 + jj)* source.getRGB(i+ii, j+jj);
				}
			this.hash = hash;
		}

		@Override
		public int hashCode() {
			return hash;
		}

		@Override
		public boolean equals(Object arg0) {
			if (arg0 instanceof BufferedImage) {
				BufferedImage image = (BufferedImage) arg0;
				for(int i=0;i < getWidth();i++)
					for(int j=0;j < getHeight();j++)
						if (image.getRGB(i, j) != image.getRGB(i, j))
							return false;

				return true;
			}
			return false;
		}
	}

	// la methode main est la methode lancer par toi
	public static void main(String[] args) throws IOException {
		// cette image est le plateau complet (sans les monstre)
		final BufferedImage image = ImageIO.read(SplitMap.class.getResourceAsStream("/zelda-overworld-remake.png"));
		final int tilewidth = 16, tileheight = 16;
		final int width = image.getWidth()/tilewidth, height = image.getHeight()/tileheight;

		final MyImage[][] images = new MyImage[width][height];

		// c est le dictionnaire dans lequel on stock les images
		// le dictionnaire (Map) se sert des fonctions equals et hash de MyImage pour determiner la presence d une image dans le dictionnaire
		// on stocke dans la valeur le nombre d occurence de la case dans le plateau
		final Map<MyImage, Integer> number = new HashMap<SplitMap.MyImage, Integer>();

		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				images[i][j] = new MyImage(tilewidth,tileheight,i*tilewidth, j*tileheight, image);
				if (!number.containsKey(images[i][j])) {
					number.put(images[i][j], 0);
				} else {
					number.put(images[i][j], number.get(images[i][j]) + 1);
				}
			}
		}

		// petite feinte pour classee les cases par nombre de fois ou elle sont presentes :)
		// c est un peu complique a lire le new XXX() {@Override function() {}} mais tellement pratique
		Set<MyImage> order = new TreeSet<SplitMap.MyImage>(new Comparator<MyImage>() {
			@Override
			public int compare(MyImage arg0, MyImage arg1) {
				int i = number.get(arg0).compareTo(number.get(arg1));
				return i==0 ? arg0.hash < arg1.hash ? -1 : 1 : -i;
			}
		});
		order.addAll(number.keySet());
		int id = 1;
		for(MyImage i : order) {
			number.put(i, id++);
		}


		// on ecrit le resultat dans map.txt et toute les cases dans imgs/%num%.png
		File imgdir = new File("target/imgs");
		imgdir.mkdirs();
		File file = new File("target/map.txt");
		BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

		for(int j=0;j<height;j++) {
			for(int i=0;i<width;i++) {
				w.write(number.get(images[i][j]).toString());
				w.write(" ");
			}
			w.newLine();
		}
		for(Entry<MyImage,Integer> entry : number.entrySet()) {
			ImageIO.write(entry.getKey(), "png", new File(imgdir,entry.getValue().toString() + ".png"));
		}
		w.close();


		// pour afficher le resultat sous forme d image
		JFrame frame = new JFrame("Image Maker");
        frame.addWindowListener(new WindowAdapter() {
            @Override
			public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        frame.setBounds(0, 0, 400, 400);
        JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(java.awt.Graphics arg0) {
        		for(int i=0;i<width;i++) {
        			for(int j=0;j<height;j++) {
        				arg0.drawImage(images[i][j],i*tilewidth,j*tileheight,tilewidth,tileheight,null);
        				arg0.drawString(number.get(images[i][j]).toString(), i*tilewidth,j*tileheight);
        			}
        		}
        	}
        };
        frame.add(panel);
        frame.setVisible(true);
	}
}
