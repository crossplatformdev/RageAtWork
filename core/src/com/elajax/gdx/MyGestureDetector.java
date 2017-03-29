package com.elajax.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class MyGestureDetector  extends GestureDetector{

	private com.badlogic.gdx.scenes.scene2d.Stage stage;
	
	public MyGestureDetector(com.badlogic.gdx.scenes.scene2d.Stage stage) {
		super(new GestureListener() {
			
			@Override
			public boolean zoom(float initialDistance, float distance) {
				System.out.println("zoom(float initialDistance==="+initialDistance+", float distance==="+distance);
				GdxGame.display.getCamera().zoom += 1;
				GdxGame.display.getCamera().update();
				return false;
			}
			
			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean tap(float x, float y, int count, int button) {
				System.out.println("Stage click x=="+x+"y=="+y);
				Player player = GdxGame.getPlayer();
				if(count==1){

					player.setCurrentAnimation(Player.ANIM_PUNCHING);
					player.setHitting(true);
					if (player.isAnimationFinished()) {
						player.setCurrentAnimation(Player.ANIM_IDLEGUARD);
						player.setHitting(false);
					}

				} else if (count==2){
					player.setCurrentAnimation(Player.ANIM_RUNNING);
					float marginWidth = player.getGraphic().getWidth() / 2;
					float marginHeight = player.getGraphic().getHeight() / 2;
					float posx = player.getGraphic().getX();
					float posy = Gdx.input.getY();

					float delta = (Gdx.input.getX() <= GameUtils.WIDTH / 2) ? -3 : 3;
					posx += delta;
					player.getGraphic().move((int) (x+GdxGame.scroll-GameUtils.WIDTH/2),(int)  (GameUtils.HEIGHT - y - 25), Gdx.graphics.getDeltaTime());
					if (player.isAnimationFinished()) {
						player.setCurrentAnimation(Player.ANIM_IDLEWEAK);
						player.setHitting(false);
					}
				}

				return false;
			}
			
			@Override
			public void pinchStop() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
				GdxGame.display.getCamera().zoom -= 1;
				return false;
			}
			
			@Override
			public boolean panStop(float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean longPress(float x, float y) {
				Player player = GdxGame.getPlayer();
					player.setCurrentAnimation(Player.ANIM_MORPH);
					if (player.isAnimationFinished()) {
						// Once morphed
					}
				return true;
			}

			@Override
			public boolean fling(float velocityX, float velocityY, int button) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		this.stage = stage;
	}

	  @Override
	    public boolean keyDown(int keycode) {
		  stage.keyDown(keycode);
	        super.keyDown(keycode);
	        return false;
	    }

	    @Override
	    public boolean keyUp(int keycode) {
	        stage.keyUp(keycode);
	        super.keyUp(keycode);
	        // TODO Auto-generated method stub
	        return false;
	    }

	    @Override
	    public boolean keyTyped(char character) {
	        // TODO Auto-generated method stub
	        stage.keyTyped(character);
	        super.keyTyped(character);
	        return false;
	    }

	    @Override
	    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	        stage.touchDown(screenX, screenY, pointer, button);
	        super.touchDown(screenX, screenY, pointer, button);
	        return false;
	    }

	    @Override
	    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
	        stage.touchUp(screenX, screenY, pointer, button);
	        super.touchUp(screenX, screenY, pointer, button);
	        return false;
	    }

	    @Override
	    public boolean touchDragged(int screenX, int screenY, int pointer) {
	        // TODO Auto-generated method stub
	        stage.touchDragged(screenX, screenY, pointer);
	        super.touchDragged(screenX, screenY, pointer);

	        return false;
	    }

	    @Override
	    public boolean mouseMoved(int screenX, int screenY) {
	        stage.mouseMoved(screenX, screenY);
	        super.mouseMoved(screenX, screenY);
	        return false;
	    }

	    @Override
	    public boolean scrolled(int amount) {
	        stage.scrolled(amount);
	        super.scrolled(amount);
	        // TODO Auto-generated method stub
	        return false;
	    }

}
