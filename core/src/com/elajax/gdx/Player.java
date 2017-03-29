package com.elajax.gdx;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Player implements ApplicationListener {
	
	private int hitPoints = 10;
	public int getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public int getLifes() {
		return lifes;
	}

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}

	private int lifes = 3;
	
	private Graphic graphic;
	private TextureRegion[] regions;
	public List<Animation<TextureRegion>> animations;
	private List<TextureRegion[]> frames;
	private int currentAnimation = 0;
	private float stateTime = 0;
	private boolean hitting = false;

	public static final int ANIM_RUNNING = 0;
	public static final int ANIM_GETTINGHIT = 1;
	public static final int ANIM_FATALITY = 2;
	public static final int ANIM_PUNCHING = 3;
	public static final int ANIM_WALKING = 4;
	public static final int ANIM_JUMPKICK = 5;
	public static final int ANIM_IDLEGUARD = 6;
	public static final int ANIM_IDLEWEAK = 7;
	public static final int ANIM_JUMP = 8;
	public static final int ANIM_MORPH = 9;

	public Player(float x, float y) {
		graphic = new Graphic("player/player1.png", x, y, 50, 50, GdxGame.display.viewport);

		regions = new TextureRegion[12];
		// Normon's Animations
		regions[ANIM_RUNNING] = new TextureRegion(new Texture("player/player1.png"), 400, 400, 300, 50);
		regions[ANIM_GETTINGHIT] = new TextureRegion(new Texture("player/player1.png"), 450, 350, 250, 50);
		regions[ANIM_FATALITY] = new TextureRegion(new Texture("player/player1.png"), 500, 250, 200, 50);
		regions[ANIM_PUNCHING] = new TextureRegion(new Texture("player/player1.png"), 450, 300, 250, 50);
		regions[ANIM_WALKING] = new TextureRegion(new Texture("player/player1.png"), 500, 200, 200, 50);
		regions[ANIM_JUMPKICK] = new TextureRegion(new Texture("player/player1.png"), 550, 100, 150, 50);
		regions[ANIM_IDLEGUARD] = new TextureRegion(new Texture("player/player1.png"), 0, 400, 400, 50);
		regions[ANIM_IDLEWEAK] = new TextureRegion(new Texture("player/player1.png"), 0, 150, 500, 50);
		regions[ANIM_JUMP] = new TextureRegion(new Texture("player/player1.png"), 600, 50, 100, 50);
		regions[ANIM_MORPH] = new TextureRegion(new Texture("player/player1.png"), 0, 0, 700, 50);

		// Bulk's Animations
		regions[10] = new TextureRegion(new Texture("player/player1.png"), 0, 0, 700, 50);
		regions[11] = new TextureRegion(new Texture("player/player1.png"), 0, 0, 700, 50);

		frames = new ArrayList<TextureRegion[]>();
		for (int i = 0; i<regions.length; i++) {
			TextureRegion[] frame = regions[i].split(50, 50)[0];
			frames.add(frame);
		}

		animations = new ArrayList<Animation<TextureRegion>>();
		animations.add(new Animation<TextureRegion>(regions[ANIM_RUNNING].split(50, 50).length*0.075f, new Array<TextureRegion>(frames.get(ANIM_RUNNING)), PlayMode.LOOP));
		animations.add(new Animation<TextureRegion>(regions[ANIM_GETTINGHIT].split(50, 50).length*0.075f, new Array<TextureRegion>(frames.get(ANIM_GETTINGHIT)), PlayMode.NORMAL));
		animations.add(new Animation<TextureRegion>(regions[ANIM_FATALITY].split(50, 50).length*0.075f, new Array<TextureRegion>(frames.get(ANIM_FATALITY)), PlayMode.NORMAL));
		animations.add(new Animation<TextureRegion>(regions[ANIM_PUNCHING].split(50, 50).length*0.075f, new Array<TextureRegion>(frames.get(ANIM_PUNCHING)), PlayMode.NORMAL));
		animations.add(new Animation<TextureRegion>(regions[ANIM_WALKING].split(50, 50).length*0.075f,new Array<TextureRegion>( frames.get(ANIM_WALKING)), PlayMode.LOOP));
		animations.add(new Animation<TextureRegion>(regions[ANIM_JUMPKICK].split(50, 50).length*0.075f,new Array<TextureRegion>( frames.get(ANIM_JUMPKICK)), PlayMode.NORMAL));
		animations.add(new Animation<TextureRegion>(regions[ANIM_IDLEGUARD].split(50, 50).length*0.075f, new Array<TextureRegion>(frames.get(ANIM_IDLEGUARD)), PlayMode.LOOP_PINGPONG));
		animations.add(new Animation<TextureRegion>(regions[ANIM_IDLEWEAK].split(50, 50).length*0.075f, new Array<TextureRegion>(frames.get(ANIM_IDLEWEAK)), PlayMode.LOOP_PINGPONG));
		animations.add(new Animation<TextureRegion>(regions[ANIM_JUMP].split(50, 50).length*0.075f,new Array<TextureRegion>( frames.get(ANIM_JUMP)), PlayMode.NORMAL));
		animations.add(new Animation<TextureRegion>(regions[ANIM_MORPH].split(50, 50).length*0.075f,new Array<TextureRegion>( frames.get(ANIM_MORPH)), PlayMode.NORMAL));

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
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed
		getGraphic().act(stateTime);   		// animation time
		TextureRegion currentFrame = (TextureRegion) animations.get(currentAnimation).getKeyFrame(stateTime, false);
		graphic.setSprite(new Sprite(currentFrame));
		Scene.getPrincipal().getBatch().begin();
		graphic.draw(Scene.getPrincipal().getBatch(), 1);
		Scene.getPrincipal().getBatch().end();
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

	public int getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(int currentAnimation) {
		stateTime = 0;
		this.currentAnimation = currentAnimation;
	}

	public boolean isHitting() {
		return hitting;
	}

	public void setHitting(boolean hit) {		
		this.hitting = hit;
	}

	public boolean isMoving() {
		return graphic.isMoving();
	}

	public boolean isAnimationFinished() {
		return animations.get(getCurrentAnimation()).isAnimationFinished(stateTime);
	}
}
