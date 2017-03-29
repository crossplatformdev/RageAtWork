package com.elajax.gdx;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Enemy implements ApplicationListener, Poolable {

	public static final float ENEMY_SPAWN_TIME = 5f;

	boolean alive = true;
	float timer;

	protected int hitcounter;

	public static final int ANIM_WALKING = 0;
	public static final int ANIM_PUNCHING = 1;
	public static final int ANIM_DIE = 2;

	protected Automat automat;
	protected Graphic graphic;
	protected TextureRegion[] regions;
	protected ArrayList<TextureRegion[]> frames;
	protected ArrayList<Animation<TextureRegion>> animations;
	private float stateTime;
	private int currentAnimation;
	private boolean hit = false;

	public Enemy(String filename, float x, float y, int hits) {
		hitcounter = hits;
		graphic = new Graphic(filename, x, y, 50, 50, GdxGame.display.viewport);
		automat = new Automat();
		regions = new TextureRegion[3];
		// Normon's Animations
		regions[ANIM_WALKING] = new TextureRegion(new Texture(filename), 0, 0, 200, 50);
		regions[ANIM_PUNCHING] = new TextureRegion(new Texture(filename), 0, 50, 150, 100);
		regions[ANIM_DIE] = new TextureRegion(new Texture(filename), 0, 100, 200, 150);

		frames = new ArrayList<TextureRegion[]>();
		for (TextureRegion region : regions) {
			TextureRegion[] frame = region.split(50, 50)[0];
			frames.add(frame);
		}

		animations = new ArrayList<Animation<TextureRegion>>();
		animations.add(new Animation<TextureRegion>(regions[ANIM_WALKING].split(50, 50).length * 0.075f,
				new Array<TextureRegion>(frames.get(ANIM_WALKING)), Animation.PlayMode.LOOP_PINGPONG));
		animations.add(new Animation<TextureRegion>(regions[ANIM_PUNCHING].split(50, 50).length * 0.075f,
				new Array<TextureRegion>(frames.get(ANIM_PUNCHING)), Animation.PlayMode.NORMAL));
		animations.add(new Animation<TextureRegion>(regions[ANIM_DIE].split(50, 50).length * 0.05f,
				new Array<TextureRegion>(frames.get(ANIM_DIE)), Animation.PlayMode.NORMAL));
		graphic.setTexture(regions[0].getTexture());
	}

	@Override
	public void create() {

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void render() {
		automat.auto(this);
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed
													// animation time
		getGraphic().act(stateTime);
		TextureRegion currentFrame = (TextureRegion) animations.get(currentAnimation).getKeyFrame(stateTime, false);
		graphic.setSprite(new Sprite(currentFrame));
		Scene.getPrincipal().getBatch().begin();
		graphic.draw(Scene.getPrincipal().getBatch(), 1);
		Scene.getPrincipal().getBatch().end();

	}

	public int getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(int currentAnimation) {
		stateTime = 0;
		this.currentAnimation = currentAnimation;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public Graphic getGraphic() {
		return graphic;
	}

	public void setGraphic(Graphic graphic) {
		this.graphic = graphic;
	}

	public Enemy() {
	}

	public void update(float delta) {
		timer += delta;

		if (this.hitcounter <= 0) {
			alive = false;
			setCurrentAnimation(Enemy.ANIM_DIE);
			if (isAnimationFinished()) {

			}
		}
	}

	@Override
	public void reset() {
		alive = true;
		timer = 0;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
		if (hit) {
			setCurrentAnimation(Enemy.ANIM_DIE);
			this.hitcounter -= 1;
		} else {
			if (isAnimationFinished()) {
				setCurrentAnimation(Enemy.ANIM_WALKING);
			}
		}
	}

	public boolean isAnimationFinished() {
		return animations.get(getCurrentAnimation()).isAnimationFinished(stateTime);
	}

	public void seekAndDestroy() {
		Player player = GdxGame.getPlayer();
		float  posx = getGraphic().getX();
		float posy = getGraphic().getY();
		
		if (player.getGraphic().getX() < getGraphic().getX()) {
			posx -= 1;
		} else if (player.getGraphic().getX() >= getGraphic().getX()) {
			posx += 1;
		}

		if (player.getGraphic().getY() > getGraphic().getY()) {
			posy += 1;
		} else if (player.getGraphic().getY() <= getGraphic().getY()) {
			posy -= 1;
		}

		if(player.getGraphic().getRect().overlaps(getGraphic().getRect())){
			posx += 0;
			posy += 0;
			destroy(player);
		}

		getGraphic().move((int)posx,(int) posy, GdxGame.timer);
		
	}
	
	public void destroy(Player player) {
		setCurrentAnimation(Enemy.ANIM_PUNCHING);
		player.setCurrentAnimation(Player.ANIM_GETTINGHIT);
		player.setHitPoints(player.getHitPoints()-1);
	}
}
