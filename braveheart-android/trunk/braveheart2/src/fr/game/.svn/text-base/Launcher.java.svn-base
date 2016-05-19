package fr.game;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.backends.jogl.JoglApplication;

import fr.game.battlefield.Battlefield;
import fr.game.config.Parser;
import fr.game.engine.BattleManager;
import fr.game.fight.Army;
import fr.game.fight.BattleFighter.Direction;
import fr.game.fight.Fighter;

public class Launcher {
	static public final int WIDTH = 30;
	static public final int HEIGHT = 30;

	public static void main(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Battlefield battlefield = new Battlefield(Launcher.WIDTH, Launcher.HEIGHT);
		Parser parser = new Parser("config/config.properties");

		boolean left = true;
		int width = battlefield.getWidth() / 3;
		int height = battlefield.getHeight() / 3;

		for (Army army : parser.parse()) {
			Random r = new Random();
			Iterator<Fighter> it = army.getFighters().iterator();
			for(int s = 0 ; s < width + height && it.hasNext(); s++) {
				for (int j = 0; j < s && it.hasNext(); j++) {
					if (r.nextBoolean()) {
						Fighter fighter = it.next();
						int i = width + (left ? s-j : width - (s-j) - 1);
						int realj = height + (left ? j : height-j-1);
						battlefield.addFighter(fighter.instanciate(army, i,realj,left ? Direction.Right : Direction.Left), i, realj);
					}
				}
			}
			left = !left;
		}

		BattleManager manager = new BattleManager(battlefield);
		new JoglApplication(new Battle(manager), "BallGame", 480, 320, true);
		// new JoglApplication(new ShowSprite(manager), "BallGame", 480, 320, true);
	}
}
