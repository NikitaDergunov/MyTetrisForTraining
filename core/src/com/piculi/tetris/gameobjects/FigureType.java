package com.piculi.tetris.gameobjects;

public enum FigureType {
    I(new boolean[][]{
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false},
            {true, true, true, true}
    }),
    SQUARE(new boolean[][]{
            {true, true },
            {true, true}
    }),
    T(new boolean[][]{
            {false, false, false},
            {true, true, true},
            {false, true, false}
    }),
    Z(new boolean[][]{
            {false, false, false},
            {true, true, false},
            {false, true, true}
    }),
    S(new boolean[][]{
            {false, false, false},
            {false, true, true},
            {true, true, false}
    }),
    L(new boolean[][]{
            {true, false, false},
            {true, false, false},
            {true, true, false}
    });
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
