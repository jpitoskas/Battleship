package com.gpit.model;

public class Battleship extends Ship{

    private static final int SIZE = 4;
    private static final int POINTS = 250;
    private static final int BONUS = 500;

    public Battleship(int x, int y, int orientation) {
        super(SIZE, POINTS, BONUS, x, y, orientation);
    }

}
