package com.elajax.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Display {

	private static final float CAMERA_SPEED = 20f;

	private static final float CAMERA_ZOOM_SPEED = 20f;
	
	public Viewport viewport;

	public ArrayMap<String, Viewport> viewports;

	private int currentViewportIndex;
	
	private Viewport currentViewport;
	
	private String currentViewportName;

	private OrthographicCamera camera;

	private OrthographicCamera miniMapCamera;
	
	public Display(String viewpt) {
		viewports = new ArrayMap<String, Viewport>();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		miniMapCamera = new OrthographicCamera();
		miniMapCamera.zoom = 10f;
		createViewports();
		viewport = selectViewport(viewpt);
	}

	public void createViewports() {

		Viewport v = new StretchViewport(GameUtils.WIDTH, GameUtils.HEIGHT, getCamera());

		viewports.put(StretchViewport.class.getSimpleName(), v);

		v = new FitViewport(GameUtils.WIDTH, GameUtils.HEIGHT, getCamera());
		viewports.put(FitViewport.class.getSimpleName(), v);

		v = new FillViewport(GameUtils.WIDTH, GameUtils.HEIGHT, getCamera());
		viewports.put(FillViewport.class.getSimpleName(), v);

		v = new ExtendViewport(GameUtils.WIDTH, GameUtils.HEIGHT, getCamera());
		viewports.put(ExtendViewport.class.getSimpleName(), v);

		v = new ScreenViewport(getCamera());
		//v.setWorldSize(GameUtils.WORLD_WIDTH, GameUtils.WORLD_HEIGHT);
		v.getCamera().position.x = 0;
		v.getCamera().position.x = 0;
		v.getCamera().position.z = 0.05f;
		viewports.put(ScreenViewport.class.getSimpleName(), v);

		currentViewportIndex = -1;
	}

	public void selectNextViewport() {
		// (-1 + 1) % 5 = 0 % 5 = 0
		// (0 + 1) % 5 = 1 % 5 = 1
		// (1 + 1) % 5 = 2 % 5 = 2
		// (2 + 1) % 5 = 3 % 5 = 3
		// (3 + 1) % 5 = 4 % 5 = 4
		// (4 + 1) % 5 = 5 % 5 = 0
		// (0 + 1) % 5 = 1 % 5 = 1
		currentViewportIndex = (currentViewportIndex + 1) % viewports.size;

		currentViewport = viewports.getValueAt(currentViewportIndex);
		currentViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		currentViewportName = viewports.getKeyAt(currentViewportIndex);
	}

	private Viewport selectViewport(String viewport) {
		currentViewportIndex = viewports.indexOfKey(viewport);
		currentViewport = viewports.getValueAt(currentViewportIndex);
		currentViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		currentViewportName = viewports.getKeyAt(currentViewportIndex);
		return currentViewport;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		viewport.setCamera(camera);
	}

	public OrthographicCamera getMiniMapCamera() {
		return miniMapCamera;
	}
	
	public void parseCameraInput() {
		// deltaTime is time passed between two frames
		float deltaTime = Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.position.x -= CAMERA_SPEED * deltaTime;
			miniMapCamera.position.x -= CAMERA_SPEED * deltaTime;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.position.x += CAMERA_SPEED * deltaTime;
			miniMapCamera.position.x += CAMERA_SPEED * deltaTime;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.position.y += CAMERA_SPEED * deltaTime;
			miniMapCamera.position.y += CAMERA_SPEED * deltaTime;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.position.y -= CAMERA_SPEED * deltaTime;
			miniMapCamera.position.y -= CAMERA_SPEED * deltaTime;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
			camera.zoom -= CAMERA_ZOOM_SPEED * deltaTime;
			miniMapCamera.zoom -= CAMERA_ZOOM_SPEED * deltaTime;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
			camera.zoom += CAMERA_ZOOM_SPEED * deltaTime;
			miniMapCamera.zoom += CAMERA_ZOOM_SPEED * deltaTime;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {

		}
		
		camera.update();
		miniMapCamera.update();
	}


}
