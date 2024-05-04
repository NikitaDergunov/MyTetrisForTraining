package com.piculi.tetris.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.piculi.tetris.FigureLinifyer;
import com.piculi.tetris.gameobjects.text.Score;
import com.piculi.tetris.gameobjects.text.TetrisAchived;

import java.util.ArrayList;
import java.util.List;

import static com.piculi.tetris.constants.GameConstants.BLOCK_SIZE;
import static com.piculi.tetris.constants.GameConstants.TOTAL_BLOCKS_X;
import static com.piculi.tetris.constants.GameConstants.TOTAL_BLOCKS_Y;

public class GameWorld {
    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;
    private boolean[][] gameBoard = new boolean[TOTAL_BLOCKS_X][TOTAL_BLOCKS_Y];
    private Figure activeFigure;
    private Score score;
    TetrisAchived tetrisAchived;
    Sound lineClearSound;
    Music backgroundMusic;
    List<Figure> figures = new ArrayList<>();
    List<Line> lines = new ArrayList<>();
    public GameWorld(){
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Tetris.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        lineClearSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lineclear.mp3"));
        shapeRenderer.setAutoShapeType(true);
        this.score = new Score(10,700,Color.BLACK);
        this.tetrisAchived = new TetrisAchived(150, 600, Color.PURPLE);
        spawnFigure();
    }

    public void update() {
        removeCompleteLinesAndShiftDown();
        activeFigure.update();
        activeFigure.isAtBottom = FigureLinifyer.isTouching(activeFigure, gameBoard);
        if (activeFigure.isAtBottom){
            gameBoard = FigureLinifyer.putFigureIntoBoard(activeFigure, gameBoard);
            spawnFigure();
        }
       // figures.stream().filter(figure -> figure.isAtBottom).forEach(this::insertFigureIntoGameBoard);
    }

    private void removeCompleteLinesAndShiftDown() {
        int linesRemoved = 0;
        boolean[][] tempBoard = new boolean[gameBoard[0].length][gameBoard.length];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                tempBoard[j][i] = gameBoard[i][j];
            }
        }
        if (isBoardEmpty()) {
            return;
        }
        for (int y = 0; y < tempBoard[0].length; y++) {
            if (isTrueRow(tempBoard[y])) {
               tempBoard[y] = new boolean[tempBoard[y].length];
                linesRemoved++;
            }
        }
        for (int y = 0; y < tempBoard[0].length; y++) {
            if (isFalseRow(tempBoard[y])) {
                for (int i = y; i < tempBoard[0].length; i++) {
                    if(i == tempBoard[0].length - 1) {
                        tempBoard[i] = new boolean[tempBoard[i].length];
                    } else
                    tempBoard[i] = tempBoard[i + 1];}
                }
                tempBoard[tempBoard[0].length - 1] = new boolean[tempBoard[0].length];
            }
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = tempBoard[j][i];
            }
        }
        if(linesRemoved > 0) {
            lineClearSound.play();
            if(linesRemoved==4) tetrisAchived.display();
            score.increaseScoreWithLinesRemoved(linesRemoved);
        }
    }


    private boolean isBoardEmpty() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (gameBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isFalseRow(boolean[] tempRow) {
        for (int i = 0; i < tempRow.length; i++) {
            if (tempRow[i]) {
                return false;
            }
        }
        return true;
    }
    private boolean isTrueRow(boolean[] tempRow) {
        for (int i = 0; i < tempRow.length; i++) {
            if (!tempRow[i]) {
                return false;
            }
        }
        return true;
    }


    public void draw(){
        ScreenUtils.clear(Color.GRAY);
        drawGrid();
        camera.update();
        activeFigure.draw(shapeRenderer);
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard[x].length; y++) {
                if (gameBoard[x][y]) {
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(Color.WHITE);
                    shapeRenderer.rect(50 + x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    shapeRenderer.end();
                }
            }
        }
        score.draw(spriteBatch);
    }

    private void spawnFigure(){
        activeFigure = new Figure(FigureType.getRandom(), 200, 800, Color.WHITE, gameBoard);
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
        spriteBatch.dispose();
        backgroundMusic.dispose();
        lineClearSound.dispose();
    }
}
