package com.elajax.gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Graphic extends Actor {

	private Texture texture;
	private Sprite sprite;
	//private Matrix4 projection;
	private TextureRegion region;


	private boolean direction = Direction.RIGHT;

	private Logger log;
	private boolean isMoving = false;
	private Viewport viewport;

	public Graphic() {

	}

	public Graphic(final String file, float x, float y, float width, float height, Viewport viewport) {
		super();
		log = new Logger(Graphic.class.getCanonicalName(), Logger.DEBUG);
		this.viewport = viewport;
		texture = new Texture(file);
		region = new TextureRegion(texture);
		sprite = new Sprite(region);
		setSprite(sprite);
		setPosition(x, y);
		setSize(width, height);
		setOriginX(width / 2);
		setOriginY(height / 2);
		sprite.setBounds(x, y, width, height);		
		
		setVisible(true);
		setTouchable(Touchable.enabled);
		setDebug(true);
	}

	public Rectangle getRect() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		boolean flip = (getDirection() == Direction.LEFT);
		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.draw(sprite, flip ? getX() + getWidth() : getX(), getY(), flip ? - getWidth() : getWidth(), getHeight());
	}


	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public void dispose() {
		texture.dispose();
	}

	public TextureRegion getRegion() {
		return region;
	}

	public void setRegion(TextureRegion region) {
		this.region = region;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public boolean getDirection() {
		return direction;
	}

	public void setDirection(boolean direction) {
		this.direction = direction;
	}

	@Override
	protected void positionChanged() {
		super.positionChanged();
	}
	
	public boolean isMoving() {
		return isMoving;
	}
	
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	@Override
	public void moveBy(float x, float y) {
		isMoving = true;
		super.moveBy(x, y);
		isMoving = false;
	}
	
	void move(int screenX, int screenY, float stateTime) {
		int clamp1 = (int) MathUtils.clamp(screenX, 10, GameUtils.STAGE_DIMENSION_X - 10 - getWidth());
		int clamp2 = (int) MathUtils.clamp(screenY, 0, 160); // Height
		// of
		// the
		// ground
		// in
		// pixels
		boolean toLeft = false;
		float moveMentX = 0;
		if (getX() < clamp1) {
			toLeft = false;
			moveMentX = clamp1 - getX();
		}

		else

		if (getX() > clamp1) {
			toLeft = true;
			moveMentX = getX() - clamp1;
		}

		float moveMentY = 0;
		if (getY() < clamp2) {
			moveMentY = clamp2 - getY();
		} else

		if (getY() > clamp2) {
			moveMentY = getY() - clamp2;
		}

		if (toLeft) {
			setDirection(Direction.LEFT);
		} else {
			setDirection(Direction.RIGHT);
		}

		float duration = (moveMentX + moveMentY) * 10f; // 10 pixels per second
		MoveToAction action = Actions.moveTo(clamp1, clamp2, duration);
		addAction(action);
		act(stateTime);
	}
}
