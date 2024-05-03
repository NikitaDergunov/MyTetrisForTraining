package com.piculi.tetris.gameobjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.piculi.tetris.constants.GameConstants.BLOCK_SIZE;
import static com.piculi.tetris.constants.GameConstants.TOTAL_BLOCKS_X;

public class Line {
    private static final int width = TOTAL_BLOCKS_X;
    public int x;
    public int y;
    public boolean[] shape;

    public Line(boolean[] body, int x, int y) {
        this.x = x;
        this.y = y;
        shape = body;
    }
    public Line(int x, int y) {
        this.x = x;
        this.y = y;
        shape = new boolean[width];
        for (int i = 0; i < shape.length; i++) {
            shape[i] = true;
        }
    }
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < shape.length; i++) {
            if (shape[i]) {
                shapeRenderer.rect(x + i * BLOCK_SIZE, y, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
        shapeRenderer.end();

    }
    public void update() {

    }
}
