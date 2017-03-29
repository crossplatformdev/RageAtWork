package com.elajax.gdx;

import com.badlogic.gdx.ApplicationListener;

public class Stage extends com.badlogic.gdx.scenes.scene2d.Stage implements ApplicationListener {
	
	protected Graphic graphic;
	
	public Stage(String filename){
		graphic = new Graphic(filename, 0, 0, GameUtils.STAGE_DIMENSION_X, GameUtils.STAGE_DIMENSION_Y, GdxGame.display.viewport);
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
		getBatch().begin();
		graphic.draw(getBatch(), 1);
		getBatch().end();
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
			graphic.dispose();
	}

	public Graphic getGraphic() {
		return graphic;
	}

	public void setGraphic(Graphic graphic) {
		this.graphic = graphic;
	}

}

