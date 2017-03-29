package com.elajax.gdx;

import com.badlogic.gdx.ApplicationListener;

public class Scene implements ApplicationListener {
	private static Stage background;
	private static Stage foreground;
	private static Stage principal;

	public static com.badlogic.gdx.scenes.scene2d.Stage stage;

	public Scene(){
		setBackground(new Stage("level/level1-background.png"));
		setForeground(new Stage("level/level1-foreground.png"));
		setPrincipal(new Stage("level/level1-principal.png"));
	}

	public static Stage getBackground() {
		return background;
	}

	public static void setBackground(Stage background) {
		Scene.background = background;
	}

	public static Stage getForeground() {
		return foreground;
	}

	public static void setForeground(Stage foreground) {
		Scene.foreground = foreground;
	}

	public static Stage getPrincipal() {
		return principal;
	}

	public static void setPrincipal(Stage principal) {
		Scene.principal = principal;
	}

	@Override
	public void create() {

	}

	@Override
	public void resize(int width, int height) {
		//use true to center camera
		update(width, height, false);
	}

	@Override
	public void render() {
		getBackground().render();
		getPrincipal().render();
	}

	public void renderOverlay() {
		getForeground().render();
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
		getPrincipal().dispose();
		getBackground().dispose();
		getForeground().dispose();
	}

	public void update(int width, int height, boolean b) {
		background.getViewport().update(width, height, false);
		principal.getViewport().update(width, height, false);
		foreground.getViewport().update(width, height, false);
	}
}
