package com.piculi.tetris.gameobjects.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score {
    private int x;
    private int y;
    private Color color;
    private int scoreValue;
    private int level;
    private BitmapFont font;
    public Score(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        scoreValue = 0;
        level = 1;
        font = new BitmapFont();
        font.setColor(color);
    }

    public void increaseScore(double value) {
        scoreValue += (int) value;
        if (scoreValue >= level*1000) {
            level++;
        }
    }
    public void increaseScoreWithLinesRemoved(int linesRemoved) {
        switch (linesRemoved) {
            case 1:
                increaseScore(level*1.25*40);
                break;
            case 2:
                increaseScore(level*1.25*100);
                break;
            case 3:
                increaseScore(level*1.25*300);
                break;
            case 4:
                increaseScore(level*1.25*1200);
                break;
        }
    }
    public void draw(SpriteBatch batch){
        batch.begin();
        font.draw(batch, "Score: " + scoreValue + "\nLevel: " + level, x, y);
        batch.end();
    }
    public void gameOver() {
        scoreValue = 0;
        level = 1;
    }

    public int getLevel() {
        return level;
    }

}
