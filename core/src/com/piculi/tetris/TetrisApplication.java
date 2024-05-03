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
import com.piculi.tetris.gameobjects.GameWorld;
import com.piculi.tetris.gameobjects.Line;

import static com.piculi.tetris.constants.GameConstants.BLOCK_SIZE;

public class TetrisApplication extends ApplicationAdapter {
	GameWorld gameWorld;
	
	@Override
	public void create () {
		gameWorld = new GameWorld();
	}

	@Override
	public void render () {
		gameWorld.update();
		gameWorld.draw();
	}
	
	@Override
	public void dispose () {
		gameWorld.dispose();
	}
}
