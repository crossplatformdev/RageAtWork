package com.elajax.gdx;

import com.badlogic.gdx.math.MathUtils;

public class EnemyDumb extends Enemy {
	protected static int hitcounter = 2;

	
	public EnemyDumb(){
		super("enemies/ennemy_streetboy-one_spritesheet.png", (float)450+MathUtils.random(50), (float)-50, hitcounter);
	}	
	public EnemyDumb(float x, float y){
		super("enemies/ennemy_streetboy-one_spritesheet.png", x, y, hitcounter);
	}	
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}

}
