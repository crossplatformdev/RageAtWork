package com.elajax.gdx;

import java.util.Random;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by goran on 29/08/2016.
 */
public class EnemyPool implements ApplicationListener {
	public EnemyPool() {
		create();
	}

	private static final Logger log = new Logger(EnemyPool.class.getName(), Logger.DEBUG);

	private static final int MAX_OBJECTS = 5;

	private Array<Enemy> enemys = new Array<Enemy>();
	private float timer;

	private double HARD_PROBABILITY = 0.75;

	private final Pool<Enemy> EnemyPool = new Pool<Enemy>(5) {

		@Override
		protected Enemy newObject() {
			log.debug("newObject");
			Random rnd = new Random();
			boolean rndBool = rnd.nextBoolean();
			if (rndBool) {
				return new EnemyDumb();
			} else {
				return new EnemyDumb();
			}
		}

		@Override
		public void free(Enemy object) {
			log.debug("before free object= " + object + " free= " + getFree());
			super.free(object);
			log.debug("after free object= " + object + " free= " + getFree());
		}

		@Override
		public Enemy obtain() {
			log.debug("before obtain free= " + getFree());
			Enemy ret = super.obtain();
			log.debug("after obtain free= " + getFree());
			return ret;
		}

		@Override
		protected void reset(Enemy object) {
			log.debug("resetting object= " + object);
			super.reset(object);
		}
	};

	private int counter;

	private boolean allDead;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		timer += delta;

		if (enemys.size < MAX_OBJECTS) {
			if (timer > Enemy.ENEMY_SPAWN_TIME) {
				timer = 0;
				Enemy enemy = EnemyPool.obtain();
				enemys.add(enemy);

				log.debug("create alive Enemys= " + enemys.size);
			}
		}
		for (int i = 0; i < enemys.size; i++) {
			Enemy enemy = enemys.get(i);
			enemy.update(delta);

			if (!enemy.alive) {
				enemys.removeIndex(i);
				EnemyPool.free(enemy);
				log.debug("after free alive Enemys= " + enemys.size);
				counter += 1;
			}

			if (counter == MAX_OBJECTS) {
				allDead = true;
				counter = 0;
			}
		}

		if (allDead) {
			if (timer > Enemy.ENEMY_SPAWN_TIME) {
				for(Enemy enemy : getEnemys()){
					enemy.reset();
				}
				log.debug("reset Enemys= " + enemys.size);
			}
		}
	}

	@Override
	public void dispose() {
		EnemyPool.freeAll(enemys);
		EnemyPool.clear();
		enemys.clear();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	public Array<Enemy> getEnemys() {
		return enemys;
	}

	public void setEnemys(Array<Enemy> enemys) {
		this.enemys = enemys;
	}

}
