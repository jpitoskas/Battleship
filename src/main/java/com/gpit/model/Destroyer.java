package com.gpit.model;

public class Destroyer extends Ship{

    private static final int SIZE = 2;
    private static final int POINTS = 50;
    private static final int BONUS = 0;

    public Destroyer(int x, int y, int orientation) {
        super(SIZE, POINTS, BONUS, x, y, orientation);
    }

}
