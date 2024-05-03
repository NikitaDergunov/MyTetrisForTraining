package com.piculi.tetris.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;

import static com.piculi.tetris.constants.GameConstants.BLOCK_SIZE;
import static com.piculi.tetris.constants.GameConstants.SCREEN_WIDTH;

public class Figure {
    private static final int normalSpeed = 2;
    private static final int fastSpeed = normalSpeed*2;
    private static final int blockSize = BLOCK_SIZE;
    public boolean[][] shape = new boolean[4][4];
    private Rectangle[][] blocks = new Rectangle[4][4];
    private int xSpeed;
    private int ySpeed;
    public int x;
    public int y;
    private Color color;
    private FigureType figureType;

    public Figure(FigureType figureType, int x, int y, Color color) {
        this.figureType = figureType;
        fillShapeArray(figureType);
        createBlocks();
        this.xSpeed = normalSpeed;
        this.ySpeed = normalSpeed;
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(x<=50){
                x=50;
            }else {
                x -= BLOCK_SIZE;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            int margin = figureType.equals(FigureType.I) ? 4 : 3;
            if (x >= SCREEN_WIDTH - margin * blockSize){
                x = SCREEN_WIDTH - margin* blockSize;
            }else {
            x += BLOCK_SIZE;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y -= fastSpeed;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) ){
            rotate();
        }
        if(y<=0){
            y=800;
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
