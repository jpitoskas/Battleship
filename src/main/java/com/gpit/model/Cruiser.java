package com.gpit.model;

public class Cruiser extends Ship{

    private static final int SIZE = 3;
    private static final int POINTS = 100;
    private static final int BONUS = 250;

    public Cruiser(int x, int y, int orientation) {
        super(SIZE, POINTS, BONUS, x, y, orientation);
    }

}
