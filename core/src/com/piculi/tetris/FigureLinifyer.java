package com.piculi.tetris;

import com.piculi.tetris.gameobjects.Figure;
import com.piculi.tetris.gameobjects.Line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.piculi.tetris.constants.GameConstants.BLOCK_SIZE;
import static com.piculi.tetris.constants.GameConstants.SCREEN_HEIGHT;
import static com.piculi.tetris.constants.GameConstants.TOTAL_BLOCKS_Y;
import static com.piculi.tetris.constants.GameConstants.X_MARGIN;

public class FigureLinifyer {
    public static boolean[][] putFigureIntoBoard(Figure figure, boolean[][] gameBoard){
        boolean[][] newGameBord = Arrays.copyOf(gameBoard, gameBoard.length);
        Coordinates leftBottom = getAbsoluteCoordinates(figure);
        int leftBottomX = leftBottom.x;
        int leftBottomY = leftBottom.y;
        List<Coordinates> shapeBlockCoordinates = getCoordinates(figure, leftBottomX, leftBottomY);
        if(shapeBlockCoordinates.stream().anyMatch(c->c.y<0)){
            shapeBlockCoordinates = shapeBlockCoordinates.stream().map(c->new Coordinates(c.x, c.y+1)).collect(Collectors.toList());
        }
        shapeBlockCoordinates.forEach(c->gameBoard[c.x][c.y] = true);
            return newGameBord;
    }

    public static List<Coordinates> getCoordinates(Figure figure, int leftBottomX, int leftBottomY) {
        List<Coordinates> shapeBlockCoordinates = new ArrayList<>();
        for (int i = 0; i< figure.shape.length; i++){
            for (int j = 0; j< figure.shape[i].length; j++){
                if (figure.shape[i][j]){
                    shapeBlockCoordinates.add(new Coordinates(leftBottomX +i, leftBottomY +j));
                }
            }
        }
        return shapeBlockCoordinates;
    }
    public static List<Coordinates> getCoordinatesForTouch(Figure figure, int leftBottomX, int leftBottomY) {
        List<Coordinates> shapeBlockCoordinates = new ArrayList<>();
        for (int i = 0; i< figure.shape.length; i++){
            for (int j = 0; j< figure.shape[i].length; j++){
                if (figure.shape[i][j]){
                    shapeBlockCoordinates.add(new Coordinates(leftBottomX +i*BLOCK_SIZE, leftBottomY + j*BLOCK_SIZE));
                }
            }
        }
        return shapeBlockCoordinates;
    }

    private static Coordinates getAbsoluteCoordinates(Figure figure){
        int x = (figure.x-X_MARGIN)/BLOCK_SIZE;
        int y = (figure.y)/BLOCK_SIZE;
        return new Coordinates(x, y);
    }

    public static boolean isTouching(Figure activeFigure, boolean[][] gameBoard) {
        List<Coordinates> shapeBlockCoordinates = getCoordinatesForTouch(activeFigure, activeFigure.x, activeFigure.y);
        for (Coordinates c: shapeBlockCoordinates){
            if(isTouchingForCoordinates(c, gameBoard)) return true;
        }
        return false;
    }

    public static boolean isTouchingForCoordinates(Coordinates c, boolean[][] gameBoard) {
        if (c.y-3 <= 0) return true;
        if (c.y>SCREEN_HEIGHT-TOTAL_BLOCKS_Y*BLOCK_SIZE) return false;
        return gameBoard[(c.x-X_MARGIN)/BLOCK_SIZE][Math.max(c.y/BLOCK_SIZE-1,0)];
    }

    public static class Coordinates{
        public int x;
        public int y;
        public Coordinates(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
