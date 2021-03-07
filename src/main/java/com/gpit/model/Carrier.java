package com.gpit.model;

public class Carrier extends Ship{

    private static final int SIZE = 5;
    private static final int POINTS = 350;
    private static final int BONUS = 1000;

    public Carrier(int x, int y, int orientation) {
        super(SIZE, POINTS, BONUS, x, y, orientation);
    }

}
