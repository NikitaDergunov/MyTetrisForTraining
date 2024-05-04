package com.piculi.tetris;

import com.piculi.tetris.gameobjects.Figure;
import com.piculi.tetris.gameobjects.Line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.piculi.tetris.constants.GameConstants.BLOCK_SIZE;
import static com.piculi.tetris.constants.GameConstants.X_MARGIN;

public class FigureLinifyer {
    public static List<Line> makeLinesFromFigure(Figure figure) {
        figure.rotate();
        figure.rotate();
        figure.rotate();
        List<Line> lines = new ArrayList<Line>();
        boolean[][] shape = figure.shape;
        for (int y=shape.length-1; y>=0; y--){
            boolean[] row = shape[y];
            System.out.println(Arrays.toString(row));
            if (isFalseArray(row)){
                continue;
            }
            boolean[] line= new boolean[20];
            Arrays.fill(line,false);
            int leftOffset = (figure.x-X_MARGIN)/BLOCK_SIZE;
            for (int x = 0; x<row.length; x++){
                if(row[x]){
                    line[leftOffset+x] = true;
                }
            }
            Line lineObj = new Line(row, figure.x, (figure.y-figure.y%BLOCK_SIZE)-y*BLOCK_SIZE);
            lines.add(lineObj);
        }
        lines.forEach(Line::printLine);
        return lines;
    }
    private static boolean isFalseArray(boolean[] array){
        for (boolean b: array){
            if (b){
                return false;
            }
        }
        return true;
    }
}
