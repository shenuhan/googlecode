package fr.game.display;

import java.util.List;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import fr.game.utils.Display;

public class EnhancedAtlas extends TextureAtlas {

	/** Creates an empty atlas to which regions can be added. */
	public EnhancedAtlas () {
	}

	/** Loads the specified pack file using {@link FileType#Internal}, using the parent directory of the pack file to find the page
	 * images. */
	public EnhancedAtlas (String internalPackFile) {
		super(internalPackFile);
	}

	/** Loads the specified pack file, using the parent directory of the pack file to find the page images. */
	public EnhancedAtlas (FileHandle packFile) {
		super(packFile);
	}

	/** @param flip If true, all regions loaded will be flipped for use with a perspective where 0,0 is the upper left corner.
	 * @see #TextureAtlas(FileHandle) */
	public EnhancedAtlas (FileHandle packFile, boolean flip) {
		super(packFile, flip);
	}

	public EnhancedAtlas (FileHandle packFile, FileHandle imagesDir) {
		super(packFile, imagesDir);
	}

	/** @param flip If true, all regions loaded will be flipped for use with a perspective where 0,0 is the upper left corner. */
	public EnhancedAtlas (FileHandle packFile, FileHandle imagesDir, boolean flip) {
		super(packFile, imagesDir, flip);
	}

	public EnhancedAtlas (TextureAtlasData data) {
		super(data);
	}

	public Sprite createTileSprite(Display helper, int i, int j) {
		int k = helper.getK(i, j);
		String grass = "grass" + (helper.getTileK(i,j)-k) + (helper.getTileK(i+1,j)-k) + (helper.getTileK(i+1,j+1)-k) + (helper.getTileK(i,j+1)-k);
		List<AtlasRegion> regions = getRegions();
		if (grass.equals("grass0000"))
			grass = "grass";
		for (AtlasRegion region : regions)
			if (region.name.equals(grass))
				return new Sprite(region);
		return null;
	}
}
