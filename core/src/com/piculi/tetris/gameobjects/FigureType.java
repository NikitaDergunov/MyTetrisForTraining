package com.piculi.tetris.gameobjects;

public enum FigureType {
    I(new boolean[][]{
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false},
            {true, true, true, true}
    }),
    SQUARE(new boolean[][]{
            {false, false, false, false},
            {false, false, false, false},
            {true, true, false, false},
            {true, true, false, false}
    }),
    T(new boolean[][]{
            {false, false, false, false},
            {false, false, false, false},
            {true, true, true, false},
            {false, true, false, false}
    }),
    Z(new boolean[][]{
            {false, false, false, false},
            {false, false, false, false},
            {true, true, false, false},
            {false, true, true, false}
    }),
    L(new boolean[][]{
            {false, false, false, false},
            {true, false, false, false},
            {true, false, false, false},
            {true, true, false, false}
    }),;
    boolean[][] shape;
    FigureType(boolean[][] shape) {
        this.shape = shape;
    }
    public boolean[][] getShape() {
        return shape;
    }
    public static FigureType getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
