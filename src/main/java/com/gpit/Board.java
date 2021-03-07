package com.gpit;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board {
    private int[][] grid;

    public Board() {
        this.grid = new int[10][10];

    }

    public void updateGrid(int x, int y) {
        grid[y][x] = 1;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getGridBlock(int x, int y){
        return grid[y][x];
    }

}
