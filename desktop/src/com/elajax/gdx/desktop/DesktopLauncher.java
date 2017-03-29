package com.elajax.gdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.elajax.gdx.GameUtils;
import com.elajax.gdx.GdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) GameUtils.WIDTH;
		config.height = (int) GameUtils.HEIGHT;
		config.resizable = true;
		config.foregroundFPS = 60;
		config.vSyncEnabled = true;
		config.audioDeviceSimultaneousSources=3;
		new LwjglApplication(new GdxGame(), config);
	}
}
