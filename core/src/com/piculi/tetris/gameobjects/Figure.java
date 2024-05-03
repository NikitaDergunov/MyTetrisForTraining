package com.piculi.tetris.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;

public class Figure {
    private static final int normalSpeed = 1;
    private static final int fastSpeed = 2;
    private static final int blockSize = 16;
    private boolean[][] shape = new boolean[4][4];
    private Rectangle[][] blocks = new Rectangle[4][4];
    private int xSpeed;
    private int ySpeed;
    private int x;
    private int y;
    private Color color;
    private FigureType figureType;

    public Figure(FigureType figureType, int x, int y, Color color) {
        fillShapeArray(figureType);
        createBlocks();
        this.xSpeed = normalSpeed;
        this.ySpeed = normalSpeed;
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if(x<=0){
                x=0;
            }else {
                x -= xSpeed;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if (x >= 300 - 4 * blockSize){
                x = 300 - 4 * blockSize;
            }else {
            x += xSpeed;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y -= fastSpeed;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) ){
            rotate();
        }
        if(y<=0){
            y=0;
        }else {
            y-=normalSpeed;
        }
    }
    public void draw(ShapeRenderer shapeRenderer){
        shapeRenderer.setColor(color);
        shapeRenderer.begin();
        for (int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                blocks[i][j].setX(x + i * blockSize);
                blocks[i][j].setY(y + j * blockSize);
                int widthOfBlock;
                int heightOfBlock;
                if(shape[i][j]){
                    widthOfBlock = blockSize;
                    heightOfBlock = blockSize;

                }else {
                    widthOfBlock = 0;
                    heightOfBlock = 0;
                }
                shapeRenderer.rect(blocks[i][j].getX(), blocks[i][j].getY(), widthOfBlock, heightOfBlock);
            }
        }
        shapeRenderer.end();
    }

    private void fillShapeArray(FigureType figureType) {
        this.shape = Arrays.copyOf(figureType.getShape(), figureType.getShape().length);
    }
    private void createBlocks(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                Rectangle block;
                if(shape[i][j]){
                    block = new Rectangle(x + i * 16, y + j * 16, 16, 16);

                }else {
                    block = new Rectangle(x + i * 16, y + j * 16, 0,0);
                }
                blocks[i][j] = block;
            }
        }
    }
    private void rotate(){
        boolean[][] transposed = new boolean[4][4];
        boolean[][] reversed = new boolean[4][4];

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                transposed[i][j] = shape[j][i];
            }
        }
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                reversed[i][j] = transposed[i][3-j];
            }
        }
        shape = reversed;
    }

}
