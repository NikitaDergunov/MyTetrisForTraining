package com.piculi.tetris.gameobjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UpNext {
    private int x;
    private int y;
    private BitmapFont font;
    private boolean[][] shape;
    private FigureType figureType;
    public UpNext(int x, int y, FigureType figureType){
        this.x = x;
        this.y = y;
        this.figureType = figureType;
        font = new BitmapFont();
        shape = figureType.getShape();

    }
    public void update(FigureType figureType){
        this.figureType = figureType;
        shape = figureType.getShape();
    }

    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    shapeRenderer.rect(x + i * 20, y + j * 20, 20, 20);
                }
            }
        }
        shapeRenderer.end();
        spriteBatch.begin();
        font.draw(spriteBatch, "Next:", x, y + 100);
        spriteBatch.end();
    }

}

