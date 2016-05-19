package fr.game;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;

import fr.game.display.AnimatedSpriteAtlas;
import fr.game.display.EnhancedAtlas;
import fr.game.display.ISpriteFactory;
import fr.game.display.SpriteFactory;
import fr.game.engine.BattleManager;
import fr.game.utils.FileWatcher;

public class ShowSprite implements ApplicationListener {
	private SpriteBatch spriteBatch;
	private EnhancedAtlas mapAtlas;
	private AnimatedSpriteAtlas fighterAtlas;
	private AnimatedSpriteAtlas weaponAtlas;
	private AnimatedSpriteAtlas magicAtlas;
	private BitmapFont font;

	private FileWatcher fileWatcher;

	final private Collection<ISpriteFactory> factories;

	public ShowSprite(BattleManager manager) {
		this.factories = new ArrayList<ISpriteFactory>();
	}

	@Override
	public void create() {
		Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);

		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, 480,320));

		mapAtlas = new EnhancedAtlas(Gdx.files.internal("battle/pack"), Gdx.files.internal("battle/"));
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);

		fighterAtlas = new AnimatedSpriteAtlas(new TextureAtlas(Gdx.files.internal("battle/pack-fighter"), Gdx.files.internal("battle/")), Gdx.files.internal("battle/pack-fighter-animation"));
		weaponAtlas = new AnimatedSpriteAtlas(new TextureAtlas(Gdx.files.internal("battle/pack-weapon"), Gdx.files.internal("battle/")), Gdx.files.internal("battle/pack-weapon-animation"));
		magicAtlas = new AnimatedSpriteAtlas(new TextureAtlas(Gdx.files.internal("battle/pack-magic"), Gdx.files.internal("battle/")), Gdx.files.internal("battle/pack-magic-animation"));

		fillFactories();
		System.out.println(new File(".").getAbsolutePath());
		fileWatcher = new FileWatcher(new File("assets/battle/pack-fighter"), new File("assets/battle/pack-fighter-animation"),new File("assets/battle/pack-magic"), new File("assets/battle/pack-magic-animation"),new File("assets/battle/pack-weapon"), new File("assets/battle/pack-weapon-animation")) {
			@Override
			public void modified() {
				fillFactories();
			}
		};
	}

	private void fillFactories() {
		synchronized (factories) {
			this.factories.clear();

			float x = 0, y = 0;
			for (AnimatedSpriteAtlas atlas : new AnimatedSpriteAtlas[]{fighterAtlas, weaponAtlas, magicAtlas}) {
				for (Object type : atlas.getSpritesTypes()) {
					SpriteFactory factory = atlas.getSpriteFactory(type);
					for (List<String> keys : factory.getKeys()) {
						SpriteFactory f = atlas.getSpriteFactory(type);
						f.setSprite(1f, keys.toArray());
						f.getSprite().setPosition(x, y);
						x += 32;
						if (x >= 240) {
							x = 0;
							y += 32;
						}
						factories.add(f);
					}

				}
			}
		}
	}

	@Override
	public void dispose() {
		if (fileWatcher != null)
			fileWatcher.stop();
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		float delta = Gdx.graphics.getDeltaTime();

		synchronized (factories) {
			for (ISpriteFactory factory : this.factories) {
				factory.getSprite().draw(spriteBatch, delta);
			}
		}
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}
}