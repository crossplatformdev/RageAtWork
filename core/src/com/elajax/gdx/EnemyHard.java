package com.elajax.gdx;

import com.badlogic.gdx.math.MathUtils;

public class EnemyHard extends Enemy {	
	protected static int hitcounter = 5;
	
	public EnemyHard() {
		super("enemies/ennemy_streetboy-oneB_spritesheet.png",  (float)450+MathUtils.random(50), (float)-50, hitcounter);
	}	
	public EnemyHard(float x, float y){
		super("enemies/ennemy_streetboy-oneB_spritesheet.png", x, y, hitcounter);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}	
	
	@Override
	public boolean isHit() {
		hitcounter -= 1;
		return super.isHit();
	}
}
