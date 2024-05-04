package com.piculi.tetris.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.piculi.tetris.FigureLinifyer;

import java.util.Arrays;
import java.util.List;

import static com.piculi.tetris.constants.GameConstants.BLOCK_SIZE;
import static com.piculi.tetris.constants.GameConstants.SCREEN_WIDTH;
import static com.piculi.tetris.constants.GameConstants.TEST_MODE;
import static com.piculi.tetris.constants.GameConstants.TOTAL_BLOCKS_X;
import static com.piculi.tetris.constants.GameConstants.X_MARGIN;

public class Figure {
    private static  int normalSpeed = 2;
    private static  int fastSpeed = normalSpeed*2;
    private static final int blockSize = BLOCK_SIZE;
    public boolean[][] shape;
    private Rectangle[][] blocks;
    private int xSpeed;
    private int ySpeed;
    public int x;
    public int y;
    private Color color;
    private FigureType figureType;
    //test
    public boolean isAtBottom = false;
    private boolean[][] gameBoard;
    private int level = 1;

    public Figure(FigureType figureType, int x, int y, Color color, boolean[][] gameBoard) {
        this.gameBoard = gameBoard;
        this.figureType = figureType;
        fillShapeArray(figureType);
        blocks = new Rectangle[shape.length][shape[0].length];
        createBlocks();
        this.xSpeed = normalSpeed;
        this.ySpeed = normalSpeed;
        this.x = x;
        this.y = y;
        this.color = color;
        rotate();
    }
    public void update(int level){
        this.level = level;
        if(isAtBottom) return;
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            x -= BLOCK_SIZE;
            if(x<X_MARGIN) {
                x = X_MARGIN;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            if(getYofTherightmostBlock()+BLOCK_SIZE>=SCREEN_WIDTH-X_MARGIN){
                x = SCREEN_WIDTH - X_MARGIN -BLOCK_SIZE - getShapeWidthInPixels();

            }else {
            x += BLOCK_SIZE;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y -= BLOCK_SIZE;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) ){
            rotate();
            moveFigureBackInBoundsAfterRotation();
        }
            y-=normalSpeed + level;
    }

    public void draw(ShapeRenderer shapeRenderer){
        shapeRenderer.begin();
        if(TEST_MODE){
            List<FigureLinifyer.Coordinates> shapeBlockCoordinates = FigureLinifyer.getCoordinatesForTouch(this, x, y);
            shapeBlockCoordinates.forEach(c->{
                Color color2;
                if (FigureLinifyer.isTouchingForCoordinatesDown(c, gameBoard)){
                    color2 = Color.RED;
                }else {
                    color2 = Color.GREEN;
                }
                shapeRenderer.setColor(color2);
                shapeRenderer.rect(c.x, c.y-blockSize, blockSize, blockSize);

            });
        }
        shapeRenderer.setColor(color);
        for (int i=0;i<shape.length;i++){
            for(int j=0;j<shape[0].length;j++){
                blocks[i][j].setX(x + i * blockSize);
                blocks[i][j].setY(y + j * blockSize);
                int widthOfBlock;
                int heightOfBlock;
                if(TEST_MODE) {
                    widthOfBlock = 1;
                    heightOfBlock = 1;
                }else
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
        for (int i=0;i<shape.length;i++){
            for(int j=0;j<shape[0].length;j++){
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
    public void rotate(){

        boolean[][] transposed = new boolean[shape.length][shape[0].length];
        boolean[][] reversed = new boolean[shape.length][shape[0].length];

        for (int i=0;i<shape.length;i++){
            for(int j=0;j<shape[0].length;j++){
                transposed[i][j] = shape[j][i];
            }
        }
        int rows = shape.length;
        int cols = shape[0].length;
        for (int i=0;i<shape.length;i++){
            for(int j=0;j<shape[0].length;j++){
                reversed[i][j] = transposed[i][cols-1-j];
            }
        }
        while (isColumnFalse(reversed, 0)||isRowFalse(reversed, 0)){
                reversed = rollArrayLeftAndDown(reversed);
        }
        shape = reversed;
    }

    public int getYofTherightmostBlock(){
        int index = 0;
        for (int i=0;i<shape.length;i++){
            for(int j=0;j<shape[0].length;j++){
                if(shape[i][j]){
                    index = Math.max(index, i);
                }
            }
        }

        return x + index * blockSize;
    }
    public int getShapeWidthInPixels(){
        int width = 0;
        for (int i=0;i<shape.length;i++){
            for(int j=0;j<shape[0].length;j++){
                if(shape[i][j]){
                    width = Math.max(width, i);
                }
            }
        }
        return width * blockSize;
    }
    private boolean[][] rollArrayLeftAndDown(boolean[][] array) {
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

        // Roll up
        if (isRowFalse(array, rows - 1)) {
            for (int i = 0; i < cols; i++) {
                for (int j = rows - 1; j > 0; j--) {
                    newArray[j][i] = newArray[j - 1][i];
                }
                newArray[0][i] = false;
            }
        }

        return newArray;
    }

    private boolean isColumnFalse(boolean[][] array, int colIndex) {
        for (boolean[] booleans : array) {
            if (booleans[colIndex]) {
                return false;
            }
        }
        return true;
    }

    private boolean isRowFalse(boolean[][] array, int rowIndex) {
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

    private void moveFigureBackInBoundsAfterRotation(){
        List<FigureLinifyer.Coordinates> shapeBlockCoordinates = FigureLinifyer.getCoordinatesForTouch(this, x, y);
        int border = X_MARGIN+TOTAL_BLOCKS_X*BLOCK_SIZE;
        if (shapeBlockCoordinates.stream().anyMatch(c->c.x+1>border)){
            x=x-BLOCK_SIZE;
        }
        if (shapeBlockCoordinates.stream().anyMatch(c->c.x-1<X_MARGIN)){
            x=X_MARGIN;
        }
    }

}
