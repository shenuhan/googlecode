package fr.game.display;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

import android.util.Log;
import fr.android.api.annotation.Initializer;
import fr.android.api.annotation.Singleton;
import fr.android.api.display.Display;
import fr.android.api.display.DisplayManager;
import fr.android.api.display.sprite.entity.EntitySprite;
import fr.android.api.event.EventProcessor;
import fr.android.api.event.MediumProcessor;
import fr.android.api.singleton.Disposable;
import fr.android.api.util.Logger;
import fr.game.display.config.Constants;
import fr.game.display.config.Descriptors.SubType;
import fr.game.engine.fighter.Attack;
import fr.game.engine.fighter.Fighter;
import fr.game.engine.fighter.Weapon;
import fr.game.engine.fighter.enumeration.AttackType;

@Display(displayed=Weapon.class)
public class WeaponDisplay extends EntitySprite implements Disposable{
	@Singleton
	DisplayManager manager;

	private EventProcessor<Attack> attackProcessor;
	Fighter fighter;

	@Initializer
	public void initialize(Weapon weapon) {
		super.initialize(weapon.getType());

		Object parent = manager.getCurrentParent();
		if (!(parent instanceof Fighter)) {
			throw Logger.t("Weapon", "Cannot display a weapon which is not in a fighter currently in " + parent.getClass().getCanonicalName());
		}
		fighter = (Fighter)parent;


		final Fighter finalFighter = fighter;

		setZIndex(50);
		setSubType(SubType.Idle);
		anim(Constants.TILEWIDTH/getGlSprite().getWidth(),500);

		attackProcessor = new MediumProcessor<Attack>() {
			@Override
			public void event(Object sender, final Attack message) {
				if (message.getAttackDescriptor().getType() == AttackType.Basic) {
					Logger.d("Weapon", finalFighter.toString() + " starts attack animation ");

					setSubType(AttackType.Basic);
					getGlSprite().setAnimationListener(new AnimatedSprite.IAnimationListener() {
						@Override
						public void onAnimationEnd(AnimatedSprite pAnimatedSprite) {
							Log.d("Sword","Back to idle :) " + finalFighter.toString());
							getGlSprite().setAnimationListener(null);
							setSubType(SubType.Idle);
							show();
						}
					});
					anim((int)message.getTime());
				}
			}
		};
		fighter.attacking().asyncSubscribe(attackProcessor);
	}

	@Override
	public void dispose() {
		fighter.attacking().unsubscribe(attackProcessor);
	}
}
