package com.piculi.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.piculi.tetris.gameobjects.Figure;
import com.piculi.tetris.gameobjects.FigureType;

public class TetrisApplication extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;
	OrthographicCamera camera;
	Figure figure;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		figure = new Figure(FigureType.T, 300, 300, Color.WHITE);
	}

	@Override
	public void render () {
		// clear the screen with a dark blue color. The
		// arguments to clear are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		//shapeRenderer.setProjectionMatrix(camera.combined);
		figure.update();
		figure.draw(shapeRenderer);
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
