package com.gpit.model;

import com.gpit.exceptions.OversizeException;

public class Submarine extends Ship{

    private static final int SIZE = 3;
    private static final int POINTS = 100;
    private static final int BONUS = 0;

    public Submarine(int x, int y, int orientation) {
        super(SIZE, POINTS, BONUS, x, y, orientation);

    }

}
