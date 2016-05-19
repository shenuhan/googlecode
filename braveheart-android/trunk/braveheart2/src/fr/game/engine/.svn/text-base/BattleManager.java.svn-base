package fr.game.engine;

import java.util.HashSet;
import java.util.Set;

import fr.game.basic.Position;
import fr.game.battlefield.Battlefield;
import fr.game.battlefield.Tile;
import fr.game.engine.Action.Type;
import fr.game.fight.Attack;
import fr.game.fight.BattleFighter;
import fr.game.utils.Event;
import fr.game.utils.IEvent;

public class BattleManager {
	final private Set<BattleFighter> fighters;
	final private Orderer orderer;
	final private BattleTimer timer;
	final private Battlefield battlefield;

	final private IEvent<Action> action;

	public BattleManager(Battlefield battlefield) {
		timer = new BattleTimer();
		orderer = new Orderer(timer);
		fighters = new HashSet<BattleFighter>();
		this.battlefield = battlefield;
		action = new Event<Action>();

		Tile[][] t = battlefield.getTiles();

		for (int i = 0; i < battlefield.getWidth(); i++) {
			for (int j = 0; j < battlefield.getHeight(); j++) {
				if (t[i][j].getFighter() != null) {
					fighters.add(t[i][j].getFighter());
					final BattleFighter fighter = t[i][j].getFighter();
					orderer.addDelayedEvent(0,new Runnable() {
						@Override
						public void run() {
							fighterReady(fighter);
						}
					});
				}
			}
		}
	}

	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	public Battlefield getBattlefield() {
		return battlefield;
	}

	public IEvent<Action> getAction() {
		return action;
	}

	private void fighterReady(final BattleFighter fighter) {
		Action action = new Action();
		action.fighter = fighter;

		BattleFighter enemy = findEnemy(fighter);

		if (enemy == null) {
			action.type = Type.Idle;
			action.position = fighter.getPosition();
			action.destination = fighter.getPosition();
			action.duration = 1000f/fighter.getSpeed();
		} else {
			Attack possible = null;
			int distance = enemy.getPosition().getDistance(fighter.getPosition());
			for (Attack attack : fighter.getMagicAttacks())
				if (attack.getDistance().contains(distance) && fighter.getMp() >= attack.getMp())
					possible = attack;

			if (possible == null)
				if (fighter.getBasicAttack().getDistance().contains(distance))
					possible = fighter.getBasicAttack();

			if (possible == null) {
				Position dest = getDestination(fighter, enemy);
				if (dest == null) {
					action.type = Type.Idle;
					action.position = fighter.getPosition();
					action.destination = fighter.getPosition();
					action.duration = 300f/fighter.getSpeed();
				} else {
					action.type = Type.Walk;
					action.position = fighter.getPosition();
					action.destination = dest;
					System.out.println(String.format("fighter %s move from %s to %s", fighter.getName(),action.position,action.destination));
					battlefield.moveFighter(fighter,action.destination);
					action.duration = (action.position.i != action.destination.i && action.position.j != action.destination.j ? 1414f:1000f) /fighter.getSpeed();
				}
			} else {
				action.type = possible == fighter.getBasicAttack() ? Type.Attack : Type.MagicAttack;
				action.position = fighter.getPosition();
				action.destination = fighter.getPosition();
				action.target = enemy;
				action.attack = possible;
				System.out.println(String.format("fighter %s attack fighter %s", fighter.getName(),enemy.getName()));
				action.duration = 1000f/possible.getSpeed();
			}
		}
		fighter.setAction(action);
		getAction().fireUpdate(action);
		orderer.addDelayedEvent(action.duration,new Runnable() {
			@Override
			public void run() {
				fighterReady(fighter);
			}
		});
	}

	private Position getDestination(BattleFighter fighter, BattleFighter enemy) {
		Position p = fighter.getPosition();
		Position e = enemy.getPosition();

		int x = p.i - e.i;
		int y = p.j - e.j;
		if (Math.abs(x) > Math.abs(y)) {
			Position pos = new Position(p.i - Integer.signum(x), p.j - (Integer.signum(x) == 0 ? Integer.signum(y):0));
			if (battlefield.getTiles()[pos.i][pos.j].getFighter() == null) return pos;
		}
		Position pos = new Position(p.i, p.j - Integer.signum(y));
		if (battlefield.getTiles()[pos.i][pos.j].getFighter() == null) return pos;
		return null;
	}

	private BattleFighter findEnemy(BattleFighter fighter) {
		int min = Integer.MAX_VALUE;
		BattleFighter closeEnemy = null;
		for (BattleFighter enemy : fighters) {
			if (fighter.getArmy() != enemy.getArmy()) {
				int dist = fighter.getPosition().i - enemy.getPosition().i^2 + fighter.getPosition().j - enemy.getPosition().j^2;
				if (dist < min) {
					min = dist;
					closeEnemy = enemy;
				}
			}
		}
		return closeEnemy;
	}
}
