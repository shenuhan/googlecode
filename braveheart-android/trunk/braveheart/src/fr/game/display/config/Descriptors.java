package fr.game.display.config;

import fr.android.api.display.sprite.Sprite;
import fr.android.api.display.sprite.SpriteDescriptors;
import fr.game.engine.fighter.Weapon.WeaponType;
import fr.game.engine.fighter.enumeration.AttackType;
import fr.game.engine.fighter.enumeration.FighterType;

public class Descriptors {
	public enum FighterAction {
		WalkLeft,
		WalkRight,
		WalkUp,
		WalkDown
	}

	public enum Type {
		BackGround,
		FighterMenuBackGround,
		Tile,
		FighterMenu
	}

	public enum SubType {
		Shadow,
		Top,
		Bottom,
		Idle,
		Action
	}

	static public SpriteDescriptors All = new SpriteDescriptors().
			add("sprite/fighter/fireheart-actorsprites1.png",FighterType.Warrior1,Sprite.DefaultSubtype,0,0,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior2,Sprite.DefaultSubtype,32*3,0,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior3,Sprite.DefaultSubtype,0,32*4,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior4,Sprite.DefaultSubtype,32*3,32*4,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior5,Sprite.DefaultSubtype,32*6,0,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior6,Sprite.DefaultSubtype,32*6,32*4,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior7,Sprite.DefaultSubtype,32*9,0,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior8,Sprite.DefaultSubtype,32*9,32*4,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add("sprite/fighter/fireheart-actorsprites2.png",FighterType.Warrior9,Sprite.DefaultSubtype,0,0,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior10,Sprite.DefaultSubtype,32*3,0,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior11,Sprite.DefaultSubtype,0,32*4,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior12,Sprite.DefaultSubtype,32*3,32*4,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).


			add(FighterType.Baddy1,Sprite.DefaultSubtype,32*6,0,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Baddy2,Sprite.DefaultSubtype,32*6,32*4,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Baddy3,Sprite.DefaultSubtype,32*9,0,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Baddy4,Sprite.DefaultSubtype,32*9,32*4,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add("sprite/fighter/fireheart-actorsprites3.png",FighterType.Warrior13,Sprite.DefaultSubtype,0,0,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior14,Sprite.DefaultSubtype,32*3,0,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior15,Sprite.DefaultSubtype,0,32*4,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior16,Sprite.DefaultSubtype,32*3,32*4,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior17,Sprite.DefaultSubtype,32*6,0,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior18,Sprite.DefaultSubtype,32*6,32*4,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior19,Sprite.DefaultSubtype,32*9,0,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add(FighterType.Warrior20,Sprite.DefaultSubtype,32*9,32*4,32,32,3,4).
			add(FighterAction.WalkDown,new int[] {0,2}).
			add(FighterAction.WalkLeft,new int[] {3,5}).
			add(FighterAction.WalkRight,new int[] {6,8}).setAsFlipOf(FighterAction.WalkLeft).
			add(FighterAction.WalkUp,new int[] {9,11}).

			add("sprite/regularhexagone.png", Type.Tile, Sprite.DefaultSubtype, 0, 0, 100, 115, 1, 1).
			add("sprite/shadowhexagone.png", Type.Tile, SubType.Shadow, 0, 0, 120, 140, 1, 1).
			add("sprite/background.png", Type.BackGround, Sprite.DefaultSubtype, 0, 0, 640, 320, 1, 1).
			add(SubType.Top, 0, 0, 640, 70, 1, 1).
			add(SubType.Bottom, 0, 70, 640, 250, 1, 1).
			add("sprite/fighterMenuBackground.jpg",Type.FighterMenuBackGround, Sprite.DefaultSubtype,0, 0, 780, 1025, 1, 1).
			add("sprite/menu.png",Type.FighterMenu, Sprite.DefaultSubtype,0, 0, 256, 512, 1, 1).
			add("sprite/weapons/sword.png",WeaponType.Sword, Sprite.DefaultSubtype, new int[][]{{0,0,32,32},{32,0,32,32},{64,0,47,32},{111,0,36,32}}).
			add(AttackType.Basic, new int[]{1,2}).
			add(SubType.Idle, new int[]{0}).
			add("sprite/weapons/sword.png",WeaponType.Axe, Sprite.DefaultSubtype, 0, 0, 32,32,2,1).
			add(AttackType.Basic, new int[]{0,1}).
			add(SubType.Idle, new int[]{0});
}
