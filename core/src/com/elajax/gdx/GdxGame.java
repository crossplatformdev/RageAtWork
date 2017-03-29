package com.elajax.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.DisplayMode;
import java.awt.Font;

public class GdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	Texture img;

	private boolean GameIsRunning = true;
	private static Player player;
	private Scene scenario;
	private boolean keyPressed = false;
	private EnemyPool enemyPool;
	public static float scroll;
	public static Display display;

	public static float stateTime;
	public static float timer;
	private Music mp3Music;
	private BitmapFont screenFont;
	private Rectangle viewport;

	@Override
	public void create() {
		scroll = Gdx.graphics.getWidth()/2;
		mp3Music = Gdx.audio.newMusic(Gdx.files.internal("music/LukHash_-_EIGHTIES__REMASTER_.mp3"));
		screenFont = new BitmapFont(Gdx.files.internal("arial-15.fnt"));
		display = new Display(ScreenViewport.class.getSimpleName());
		player = new Player(100, 50);
		scenario = new Scene();
		enemyPool = new EnemyPool();
		MyGestureDetector mgd = new MyGestureDetector(scenario.getPrincipal());
		InputAdapter inputAdapter = new InputAdapter() {
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				System.out.println("touchDown(int screenX===" + screenX + ", int screenY===" + screenY
						+ ", int pointer===" + pointer + ", int button===" + button + ")");
				// This part of code is useful to check screen coordinates.

				if (button == Buttons.LEFT) {

				} else if (button == Buttons.RIGHT) {
					// In an early version there was no scroll and it had fixed screen,
					// So this were used to control player, but is useless right now.

					// Persisted for learning purposes.
					// attack(screenX, screenY, pointer, button);
				}
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				display.getCamera().zoom += amount;
				return true;
			}
		};

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(inputAdapter);
		multiplexer.addProcessor(mgd);
		
		Gdx.input.setInputProcessor(multiplexer);
		scenario.getPrincipal().setDebugAll(true);

		mp3Music.play();

	}

	@Override
	public void render() {
		// update camera
		display.getCamera().update();
		//display.getCamera().apply(Gdx.gl10);

		// set viewport
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
				(int) viewport.width, (int) viewport.height);

		GdxUtils.clearScreen();

		if (GameIsRunning) {
			stateTime += Gdx.graphics.getDeltaTime();
			parseInput();
			updateActors();
			updateCamera();
			drawActors();
			drawFont();
		}

	}

	private void drawFont() {
		scenario.getForeground().getBatch().begin();
		screenFont.draw(scenario.getForeground().getBatch(), "THIS IS A DEMO!\nKEEP IN MIND\nTHAT IS WIP :P", 50, 100);
		scenario.getForeground().getBatch().end();

		String lifemeter = "";
		for(int i=0;i<player.getHitPoints();i++){
			lifemeter += ".";
		}
		scenario.getForeground().getBatch().begin();
		screenFont.draw(scenario.getForeground().getBatch(), lifemeter, player.getGraphic().getX(), player.getGraphic().getY()+player.getGraphic().getHeight()+5);
		scenario.getForeground().getBatch().end();

		for (Enemy enemy: enemyPool.getEnemys()) {
			lifemeter = "";
			for(int i=0;i<enemy.hitcounter;i++){
				lifemeter += ".";
			}
			scenario.getForeground().getBatch().begin();
			screenFont.draw(scenario.getForeground().getBatch(), lifemeter, enemy.getGraphic().getX(), enemy.getGraphic().getY()+enemy.getGraphic().getHeight()+5);
			scenario.getForeground().getBatch().end();
		}

	}

	private void drawActors() {
        display.viewport.apply();
        scenario.render();

		enemyPool.render();
		for (Enemy enemy : enemyPool.getEnemys()) {
			enemy.render();
		}

		player.render();

		scenario.renderOverlay();
	}

	private void updateActors() {
        com.badlogic.gdx.scenes.scene2d.Stage stage = scenario.getPrincipal();

        stage.addActor(scenario.getBackground().getGraphic());
		stage.addActor(scenario.getPrincipal().getGraphic());
		stage.addActor(player.getGraphic());

		updatePool(enemyPool.getEnemys());

		stage.addActor(scenario.getForeground().getGraphic());

		stage.act(stateTime);
	}

	private void updateCamera() {
		float marginWidth = player.getGraphic().getWidth() / 2;
		float marginHeight = player.getGraphic().getHeight() / 2;

		if (player.getGraphic().getX() < scroll) {
			scroll -= 1;
		} else if (player.getGraphic().getX() > scroll) {
			scroll += 1;
		}

		scroll = MathUtils.clamp(scroll,marginWidth + GameUtils.WIDTH / 2, GameUtils.STAGE_DIMENSION_X - marginWidth - GameUtils.WIDTH / 2);
		//scroll = MathUtils.clamp(scroll, player.getGraphic().getX()-100-getPlayer().getGraphic().getWidth()/2, player.getGraphic().getX()+100+getPlayer().getGraphic().getWidth()/2);
		display.getCamera().position.set(scroll, GameUtils.HEIGHT/2, display.getCamera().position.z);

	}

	private void updatePool(Array<Enemy> array) {
		for (Enemy enemy : array) {
			scenario.getPrincipal().addActor(enemy.getGraphic());
			if (player.getGraphic().getRect().overlaps(enemy.getGraphic().getRect())) {
				if (player.isHitting()) {
					if (!player.getGraphic().getDirection()==enemy.getGraphic().getDirection()) {
						if (Enemy.ANIM_WALKING == enemy.getCurrentAnimation()) {
							enemy.setHit(true);
							if (enemy.isAnimationFinished()) {
								enemy.setHit(false);
							}
						}
					}

				}
			}
			enemy.update(stateTime);
			scenario.getPrincipal().addActor(enemy.getGraphic());

		}
	}

	private void parseInput() {
		display.parseCameraInput();
		if (!keyPressed) {
			timer = stateTime;
			if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
				keyPressed = true;
				player.setCurrentAnimation(Player.ANIM_PUNCHING);
				player.setHitting(true);
				if (player.isAnimationFinished()) {
					player.setCurrentAnimation(Player.ANIM_IDLEGUARD);
					player.setHitting(false);
					keyPressed = false;
				}

			} else

			if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
				keyPressed = true;
				player.setCurrentAnimation(Player.ANIM_RUNNING);
				float marginWidth = player.getGraphic().getWidth() / 2;
				float marginHeight = player.getGraphic().getHeight() / 2;
				float posx = player.getGraphic().getX();
				float posy = Gdx.input.getY();

				float delta = (Gdx.input.getX() <= GameUtils.WIDTH / 2) ? -3 : 3;
				posx += delta;
				//player.getGraphic().move((int) (posx), (int) (GameUtils.HEIGHT - posy - marginHeight), timer);
				if (player.isAnimationFinished()) {
					player.setCurrentAnimation(Player.ANIM_IDLEWEAK);
					player.setHitting(false);
					keyPressed = false;
				}
			} else

			if (player.getHitPoints() == 0) {
				keyPressed = true;
				player.setCurrentAnimation(Player.ANIM_GETTINGHIT);
				if (player.isAnimationFinished()) {
					player.setCurrentAnimation(Player.ANIM_WALKING);
					player.setCurrentAnimation(Player.ANIM_MORPH);
					if (player.isAnimationFinished()) {
						keyPressed = false;
						// Once morphed
					}
				}
			}
		}

		if (player.isAnimationFinished()) {
			player.setCurrentAnimation(Player.ANIM_IDLEGUARD);
			player.setHitting(false);
			keyPressed = false;
		}
	}

	@Override
	public void dispose() {
		scenario.dispose();
		enemyPool.dispose();
		player.dispose();
		mp3Music.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		// calculate new viewport
		float aspectRatio = (float)width/(float)height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);

		if(aspectRatio > GameUtils.ASPECT_RATIO)
		{
			scale = (float)height/(float)GameUtils.VIRTUAL_HEIGHT;
			crop.x = (width - GameUtils.VIRTUAL_WIDTH*scale)/2f;
		}
		else if(aspectRatio <  GameUtils.ASPECT_RATIO)
		{
			scale = (float)width/(float) GameUtils.VIRTUAL_WIDTH;
			crop.y = (height -  GameUtils.VIRTUAL_HEIGHT*scale)/2f;
		}
		else
		{
			scale = (float)width/(float) GameUtils.VIRTUAL_WIDTH;
		}

		float w = (float) GameUtils.VIRTUAL_WIDTH*scale;
		float h = (float) GameUtils.VIRTUAL_HEIGHT*scale;

		//crop.set(Scaling.fit.apply(GameUtils.VIRTUAL_WIDTH, GameUtils.VIRTUAL_HEIGHT, GameUtils.WIDTH, GameUtils.HEIGHT));
		viewport = new Rectangle(crop.x, crop.y, w, h);
		display.viewport.setScreenBounds((int)viewport.x,(int)viewport.y,(int)viewport.width,(int)viewport.height);
		display.getCamera().viewportHeight = h;
		display.getCamera().viewportWidth = w;
		Gdx.gl.glViewport(0, 0, width, height);
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

		//scenario.getPrincipal().getViewport().setScreenSize(width, height);
		//scenario.getForeground().getViewport().setScreenSize(width, height);
		//scenario.getBackground().getViewport().setScreenSize(width, height);

		OrthographicCamera camera = display.getCamera();

		camera.setToOrtho(false);
		camera.position.set(scroll, height/2, display.getCamera().position.z);
		camera.update(true);

	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		GdxGame.player = player;
	}
}
