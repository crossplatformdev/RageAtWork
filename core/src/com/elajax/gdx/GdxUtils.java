package com.elajax.gdx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;




/**
 * Created by goran on 19/08/2016.
 */
public class GdxUtils {

	public static void clearScreen() {
		clearScreen(Color.BLACK);
	}
	
	public static void clearScreen(Color color) {
		// clear screen
		// DRY - Don't repeat yourself
		// WET - Waste everyone's time
		Gdx.gl.glClearColor(Float.valueOf((float) color.r), Float.valueOf((float) color.g), Float.valueOf((float) color.b), 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	private GdxUtils() {
	}
}
