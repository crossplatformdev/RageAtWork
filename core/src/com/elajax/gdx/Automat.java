package com.elajax.gdx;

public class Automat {

	private int state = -1;

	public Automat() {
		state = 0;
	}

	public void auto(Enemy enemy) {
		switch (enemy.getCurrentAnimation()) {
		case Enemy.ANIM_WALKING:
			enemy.seekAndDestroy();
			break;
		case Enemy.ANIM_PUNCHING:
			if(enemy.isAnimationFinished()){
				enemy.setCurrentAnimation(Enemy.ANIM_WALKING);
			}
			break;
		case Enemy.ANIM_DIE:
			if(enemy.isAnimationFinished()){
				enemy.setCurrentAnimation(Enemy.ANIM_WALKING);
			}
			break;			
		default:
			break;
		}
	}

}
