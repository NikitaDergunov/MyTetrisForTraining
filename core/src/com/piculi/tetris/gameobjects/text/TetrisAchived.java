package com.piculi.tetris.gameobjects.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TetrisAchived {
    private int x;
    private int y;
    private Color color;
    private String text;
    private BitmapFont font;
    private boolean isDisplayed = false;
    private long displayTime;
    public TetrisAchived(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.text = "TETRIS!";
        font = new BitmapFont();
        font.setColor(color);
    }
    public void display(String text) {
        this.text = text;
        isDisplayed = true;
        displayTime = System.currentTimeMillis();
    }
    public void draw(SpriteBatch batch) {
        if (isDisplayed) {
            batch.begin();
            font.draw(batch, text, x, y);
            batch.end();
            if (System.currentTimeMillis() - displayTime > 1000) {
                isDisplayed = false;
            }
        }
    }
}
