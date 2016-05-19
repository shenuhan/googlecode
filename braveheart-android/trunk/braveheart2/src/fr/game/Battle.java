package fr.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;

import fr.game.basic.FPosition;
import fr.game.basic.Position;
import fr.game.battlefield.Battlefield;
import fr.game.display.AnimatedSpriteAtlas;
import fr.game.display.EnhancedAtlas;
import fr.game.display.ISpriteFactory;
import fr.game.display.modifier.IModifier;
import fr.game.display.modifier.Modifiers;
import fr.game.engine.Action;
import fr.game.engine.Action.Type;
import fr.game.engine.BattleManager;
import fr.game.fight.BattleFighter;
import fr.game.fight.Fighter;
import fr.game.utils.Constants;
import fr.game.utils.Display;
import fr.game.utils.Listener;

public class Battle implements ApplicationListener {
	final private BattleManager manager;
	final private Battlefield battlefield;
	final private Display helper;

	private SpriteBatch spriteBatch;
	private Sprite[][] sprites;
	private EnhancedAtlas mapAtlas;
	private AnimatedSpriteAtlas fighterAtlas;
	private AnimatedSpriteAtlas weaponAtlas;
	private AnimatedSpriteAtlas magicAtlas;
	private BitmapFont font;

	private class Magics {
		public Collection<ISpriteFactory> magics = new ArrayList<ISpriteFactory>();
		public Collection<ISpriteFactory> oldMagics = new ArrayList<ISpriteFactory>();
	}

	private class FighterFactories {
		public ISpriteFactory fighter;
		public ISpriteFactory weapon;
		public Magics magics;
	}

	final private Map<Fighter, FighterFactories> factories;

	public Battle(BattleManager manager) {
		this.manager = manager;
		this.battlefield = manager.getBattlefield();
		this.helper = new Display(battlefield);
		this.sprites = new Sprite[battlefield.getWidth()][battlefield.getHeight()];
		this.factories = new HashMap<Fighter, FighterFactories>();
	}

	@Override
	public void create() {
		Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);

		spriteBatch = new SpriteBatch();
		int width = battlefield.getWidth()/3, height = battlefield.getHeight()/3;
		spriteBatch.setProjectionMatrix(new Matrix4().setToOrtho2D((width + height - 1) * 32, (width + height - 1) * 16, (width+height + 3) * 32, (width+height + 2)*16));

		mapAtlas = new EnhancedAtlas(Gdx.files.internal("battle/pack"), Gdx.files.internal("battle/"));
		for (int i = 0; i < battlefield.getWidth(); i++) {
			for (int j = 0 ; j < battlefield.getHeight(); j++) {
				sprites[i][j] = mapAtlas.createTileSprite(helper,i,j);
				FPosition p = helper.tilePosition(new Position(i, j));
				sprites[i][j].setPosition(p.x,p.y);
			}
		}

		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);

		fighterAtlas = new AnimatedSpriteAtlas(new TextureAtlas(Gdx.files.internal("battle/pack-fighter"), Gdx.files.internal("battle/")), Gdx.files.internal("battle/pack-fighter-animation"));
		weaponAtlas = new AnimatedSpriteAtlas(new TextureAtlas(Gdx.files.internal("battle/pack-weapon"), Gdx.files.internal("battle/")), Gdx.files.internal("battle/pack-weapon-animation"));
		magicAtlas = new AnimatedSpriteAtlas(new TextureAtlas(Gdx.files.internal("battle/pack-magic"), Gdx.files.internal("battle/")), Gdx.files.internal("battle/pack-magic-animation"));
		manager.getAction().addListener(new Listener<Action>() {
			@Override
			public void event(Observable arg0, Action action) {
				synchronized (factories) {
					Battle.this.newAction(action);
				}
			}
		});
		manager.start();
	}

	protected void newAction(Action action) {
		FighterFactories factories = Battle.this.factories.get(action.fighter);
		if (factories == null) {
			factories = new FighterFactories();
			Battle.this.factories.put(action.fighter,factories);
			FPosition position = helper.fighterPosition(action.position);
			factories.fighter = fighterAtlas.getSpriteFactory(action.fighter.getType());
			factories.weapon = weaponAtlas.getSpriteFactory(action.fighter.getWeapon());
			factories.fighter.setSprite(action.duration, action.type, getDirection(action));
			factories.weapon.setSprite(action.duration, action.type, getDirection(action));
			factories.fighter.getSprite().setPosition(position.x,position.y);
			factories.weapon.getSprite().setPosition(position.x,position.y);
		} else {
			factories.fighter.setSprite(action.duration, action.type, getDirection(action));
			factories.weapon.setSprite(action.duration, action.type, getDirection(action));
		}
		if (! action.destination.equals(action.position)) {
			factories.fighter.getSprite().addModifier(Modifiers.fighterMovement(helper, action.position, action.destination, action.duration));
			factories.weapon.getSprite().addModifier(Modifiers.fighterMovement(helper, action.position, action.destination, action.duration));
		}
		if (action.type == Type.MagicAttack) {
			if (factories.magics == null)
				factories.magics = new Magics();
			final ISpriteFactory magic = magicAtlas.getSpriteFactory(action.attack.getMagicType());
			magic.setSprite(action.duration, getDirection(action));
			final Magics magics = factories.magics;
			magic.getSprite().addModifier(
					Modifiers.combined(
						Modifiers.magicMovement(helper, action.position, action.target, action.attack.getSpeed()),
						new IModifier() {
							@Override
							public float next(Sprite s, float delta) {
								magics.oldMagics.add(magic);
								return 1;
							}
						}
					));
			factories.magics.magics.add(magic);
		}
	}

	private BattleFighter.Direction getDirection(Action action) {
		switch(action.type) {
		case Idle :
			return action.fighter.getDirection();
		case Walk:
			return action.getMovementDirection();
		case Attack:
		case MagicAttack:
			return action.getAttackDirection();
		default:
			throw new RuntimeException("Not yet implemented");
		}

	}
	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
		manager.stop();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		float delta = Gdx.graphics.getDeltaTime();
		synchronized (factories) {
			for (int i = 0; i < battlefield.getWidth(); i++) {
				for (int j = battlefield.getHeight() - 1; j >= 0 ; j--) {
					sprites[i][j].draw(spriteBatch);
				}
			}
		}
		if (Constants.DEBUG)
			for (int i = 0; i < battlefield.getWidth() + 1; i++) {
				for (int j = 0; j < battlefield.getHeight() + 1; j++) {
					FPosition p = helper.tilePosition(new Position(i, j));
					font.draw(spriteBatch, "" + battlefield.getTiles()[i][j].getK(), p.x,p.y + 16);
				}
			}
		synchronized (factories) {
			for (FighterFactories factories : this.factories.values()) {
				factories.fighter.getSprite().draw(spriteBatch, delta);
				factories.weapon.getSprite().draw(spriteBatch, delta);
				if (factories.magics != null) {
					for (ISpriteFactory factory : factories.magics.magics)
						factory.getSprite().draw(spriteBatch, delta);
					for (ISpriteFactory factory : factories.magics.oldMagics)
						factories.magics.magics.remove(factory);
					factories.magics.oldMagics.clear();
				}
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