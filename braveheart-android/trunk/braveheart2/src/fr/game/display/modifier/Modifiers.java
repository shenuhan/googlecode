package fr.game.display.modifier;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.game.basic.FPosition;
import fr.game.basic.Position;
import fr.game.display.Animation;
import fr.game.fight.BattleFighter;
import fr.game.utils.Display;

public class Modifiers {
	static abstract class AModifier implements IModifier {
		protected float remain;
		protected float duration;

		public AModifier(float duration) {
			this.duration = this.remain = duration;
		}
		@Override
		public float next(Sprite s, float delta) {
			float res = 0;
			remain -= delta;
			if (remain < 0) {
				res = -remain;
				remain = 0;
			}
			float percent = remain / duration;
			apply(s, percent);
			return res;
		}
		abstract void apply(Sprite s, float percent);
	}

	public static IModifier movement(final FPosition from,final FPosition to, final float duration) {
		return new AModifier(duration) {
			@Override
			void apply(Sprite s, float percent) {
				s.setPosition(to.x - (to.x - from.x) * percent, to.y - (to.y - from.y) * percent);
			}
		};
	}

	public static IModifier combined(final IModifier...modifiers) {
		return new IModifier() {
			private int current = 0;

			@Override
			public float next(Sprite s, float delta) {
				float remain = delta;
				while(remain > 0 && current < modifiers.length )
					if ((remain = modifiers[current].next(s, remain)) != 0)
						current++;
				return remain;
			}
		};
	}

	public static IModifier fighterMovement(final Display helper, final Position position, final Position destination, final float duration) {
		final FPosition from = helper.fighterPosition(position);
		final FPosition to = helper.fighterPosition(destination);
		final FPosition middle = helper.fighterMiddlePosition(position, destination);
		return Modifiers.combined(Modifiers.movement(from, middle, duration/2), Modifiers.movement(middle, to, duration/2));
	}

	public static IModifier magicMovement(final Display helper, final Position position, final BattleFighter enemy, final int speed) {
		return new IModifier() {
			FPosition from = new FPosition(position.i, position.j);
			@Override
			public float next(Sprite s, float delta) {
				float distance = delta * speed /1000;

				final FPosition to = enemy.getFPosition();

				float dist = (float) Math.sqrt((to.x - from.x)*(to.x - from.y)+(to.y - from.x)*(to.y - from.y));
				if (dist > distance) {
					float percent = distance / dist;
					to.x = from.x + (to.x - from.x)*percent;
					to.y = from.y + (to.y - from.y)*percent;
				}
				from = to;
				FPosition toMap = helper.fighterPosition(to);
				s.setPosition(toMap.x, toMap.y);
				return dist > distance ? 0 : (int) 1;
			}
		};
	}

	public static IModifier weaponModifier(final Animation weapon, final float duration, final SpriteBatch batch) {
		return new AModifier(duration) {
			@Override
			void apply(Sprite s, float percent) {
				weapon.setPosition(s.getX(), s.getY());
				weapon.draw(batch, percent * duration);
			}
		};
	}
}
