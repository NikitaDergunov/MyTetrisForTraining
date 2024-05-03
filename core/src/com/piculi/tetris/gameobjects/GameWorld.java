package com.piculi.tetris.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import static com.piculi.tetris.constants.GameConstants.BLOCK_SIZE;
import static com.piculi.tetris.constants.GameConstants.TOTAL_BLOCKS_X;
import static com.piculi.tetris.constants.GameConstants.TOTAL_BLOCKS_Y;

public class GameWorld {
    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;
    private boolean[][] gameBoard = new boolean[TOTAL_BLOCKS_X][TOTAL_BLOCKS_Y];
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
        for (Figure figure : figures) {
            figure.update();
            //insertFigureIntoGameBoard(figure);
        }
        for (Line line : lines) {
            line.update();
            //insertLineIntoGameBoard(line);
        }
    }
    public void draw(){
        ScreenUtils.clear(Color.GRAY);
        drawGrid();
        camera.update();
        for (Figure figure : figures) {
            figure.draw(shapeRenderer);
        }
        for (Line line : lines) {
            line.draw(shapeRenderer);
        }


    }
    private void spawnFigure(){
        figures.add(new Figure(FigureType.getRandom(), 50, 800, Color.WHITE));
        lines.add(new Line(50, 0));
    }
    private void insertFigureIntoGameBoard(Figure figure){
        int x = getDescreteX(figure.x);
        int y = getDescreteY(figure.y);
        boolean[][] shape = figure.shape;
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    gameBoard[x + i][y + j] = true;
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
        return x-100/TOTAL_BLOCKS_X;
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
