package com.piculi.tetris.gameobjects.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score {
    private int x;
    private int y;
    private Color color;
    private int scoreValue;
    private BitmapFont font;
    public Score(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        scoreValue = 0;
        font = new BitmapFont();
        font.setColor(color);
    }

    public void increaseScore(int value) {
        scoreValue += value;
    }
    public void increaseScoreWithLinesRemoved(int linesRemoved) {
        switch (linesRemoved) {
            case 1:
                increaseScore(40);
                break;
            case 2:
                increaseScore(100);
                break;
            case 3:
                increaseScore(300);
                break;
            case 4:
                increaseScore(1200);
                break;
        }
    }
    public void draw(SpriteBatch batch){
        batch.begin();
        font.draw(batch, "Score: " + scoreValue, x, y);
        batch.end();
    }
}
