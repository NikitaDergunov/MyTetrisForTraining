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
import static com.piculi.tetris.constants.GameConstants.X_MARGIN;

public class Figure {
    private static  int normalSpeed = 2;
    private static  int fastSpeed = normalSpeed*2;
    private static final int blockSize = BLOCK_SIZE;
    public boolean[][] shape = new boolean[4][4];
    private Rectangle[][] blocks = new Rectangle[4][4];
    private int xSpeed;
    private int ySpeed;
    public int x;
    public int y;
    private Color color;
    private FigureType figureType;
    //test
    public boolean isAtBottom = false;

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
        if(isAtBottom) return;
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(x<=X_MARGIN-1){
                x=X_MARGIN;
            }else {
                x -= BLOCK_SIZE;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            if(getYofTherightmostBlock()+BLOCK_SIZE>=SCREEN_WIDTH-X_MARGIN){
                x = SCREEN_WIDTH - X_MARGIN - getShapeWidthInPixels();

            }else {
            x += BLOCK_SIZE;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y -= BLOCK_SIZE;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) ){
            rotate();
        }
        if(y<=0){
            isAtBottom = true;
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
        while (isColumnFalse(reversed, 0)||isRowFalse(reversed, 0)){
                reversed = rollArrayLeftAndDown(reversed);
        }
        printArrayToConsole(reversed);
        shape = reversed;
    }
    private int getYofTherightmostBlock(){
        int index = 0;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if(shape[i][j]){
                    index = Math.max(index, i);
                }
            }
        }

        return x + index * blockSize;
    }
    private int getShapeWidthInPixels(){
        int width = 0;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if(shape[i][j]){
                    width = Math.max(width, i);
                }
            }
        }
        return width * blockSize;
    }
    public boolean[][] rollArrayLeftAndDown(boolean[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        boolean[][] newArray = new boolean[rows][cols];

        // Roll left
        if (isColumnFalse(array, 0)) {
            for (int i = 0; i < rows; i++) {
                System.arraycopy(array[i], 1, newArray[i], 0, cols - 1);
                newArray[i][cols - 1] = false;
            }
        }

        // Roll down
        if (isRowFalse(newArray, 0)) {
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows - 1; j++) {
                    newArray[j][i] = newArray[j + 1][i];
                }
                newArray[rows - 1][i] = false;
            }
        }

        return newArray;
    }

    public boolean isColumnFalse(boolean[][] array, int colIndex) {
        for (boolean[] booleans : array) {
            if (booleans[colIndex]) {
                return false;
            }
        }
        return true;
    }

    public boolean isRowFalse(boolean[][] array, int rowIndex) {
        for (boolean b : array[rowIndex]) {
            if (b) {
                return false;
            }
        }
        return true;
    }
    private void printArrayToConsole(boolean[][] array){
        String[][] newArray = new String[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if(array[j][i]){
                    newArray[j][i] = "Q";
                }else {
                    newArray[j][i] = "_";
                }
            }
        }
        for (int i = 0; i < newArray.length; i++) {
            System.out.println(Arrays.toString(newArray[i]));
        }
    }

}
