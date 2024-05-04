package com.piculi.tetris.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.piculi.tetris.FigureLinifyer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.piculi.tetris.constants.GameConstants.BLOCK_SIZE;
import static com.piculi.tetris.constants.GameConstants.TOTAL_BLOCKS_X;
import static com.piculi.tetris.constants.GameConstants.TOTAL_BLOCKS_Y;
import static com.piculi.tetris.constants.GameConstants.X_MARGIN;

public class GameWorld {
    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;
    private boolean[][] gameBoard = new boolean[TOTAL_BLOCKS_X][TOTAL_BLOCKS_Y];
    private Figure activeFigure;
    List<Figure> figures = new ArrayList<>();
    List<Line> lines = new ArrayList<>();
    public GameWorld(){
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        shapeRenderer.setAutoShapeType(true);
        spawnFigure();
    }

    public void update() {
        clearBoard();
        activeFigure.update();
        for (Line line : lines) {
            line.update();
           // insertLineIntoGameBoard(line);
        }
        if (activeFigure.isAtBottom){
            lines.addAll(FigureLinifyer.makeLinesFromFigure(activeFigure));
            spawnFigure();
        }
       // figures.stream().filter(figure -> figure.isAtBottom).forEach(this::insertFigureIntoGameBoard);
    }



    public void draw(){
        ScreenUtils.clear(Color.GRAY);
        drawGrid();
        camera.update();
        activeFigure.draw(shapeRenderer);
        for (Line line : lines) {
            line.draw(shapeRenderer);
        }


    }

    private void spawnFigure(){
        //figures.add(new Figure(FigureType.getRandom(), 50, 800, Color.WHITE));
        activeFigure = new Figure(FigureType.getRandom(), 50, 800, Color.WHITE);
        //lines.add(new Line(50, 0));
    }
    private void insertFigureIntoGameBoard(Figure figure){
       for(int i = 0; i<figure.shape.length; i++){
           for(int j = 0; j<figure.shape[i].length; j++){
               if(figure.shape[i][j]){
                   gameBoard[getDescreteX(figure.x)+i][getDescreteY(figure.y)-j] = true;
               }
           }
       }

    }
    private void insertLineIntoGameBoard(Line line){
        int x = getDescreteX(line.x);
        int y = getDescreteY(line.y);
        boolean[] shape = line.shape;
        for (int i = 0; i < shape.length; i++) {
            if (shape[i]) {
                gameBoard[x + i][y] = true;
            }
        }
    }
    private int getDescreteX(int x){
        return ((x+X_MARGIN)/TOTAL_BLOCKS_X)-1;
    }
    private int getDescreteY(int y){
        return y/TOTAL_BLOCKS_Y;
    }
    private void clearBoard(){
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = false;
            }
        }
    }
    private void drawGrid(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for (int i = 0; i < TOTAL_BLOCKS_X; i++) {
            for (int j = 0; j < TOTAL_BLOCKS_Y; j++) {
                shapeRenderer.rect(50 + (i * BLOCK_SIZE), j * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
