package com.gpit.model;

import com.gpit.Position;
import com.gpit.exceptions.OversizeException;

import java.util.ArrayList;

public class Ship {

    //  0 - Intact
    //  1 - Shot
    //  2 - Sunk
    private int state;
    private final int size;
    private final int points;
    private final int bonus;

    private int x0;
    private int y0;
    private int orientation;
    private Position[] coordinates;
//    private boolean[] shotsArray;
    private int shotCount;




    public Ship(int size, int points, int bonus, int x, int y, int orientation) {

        this.state = 0;
        this.size = size;
        this.points = points;
        this.bonus = bonus;
        this.x0 = x;
        this.y0 = y;
        this.orientation = orientation;
        this.coordinates = new Position[size];
        findCoordinates();
//        this.shotsArray = new boolean[size];
        this.shotCount = 0;



    }

    private void findCoordinates() {

        if (this.isHorizontal()) {
            for (int i = 0; i < this.size; i++) {
                this.coordinates[i] = new Position(this.x0 + i, this.y0);
            }
        }
        else if (this.isVertical()) {
            for (int i = 0; i < this.size; i++) {
                this.coordinates[i] = new Position(this.x0, this.y0 + i);
            }
        }

//        for (int i = 0; i < this.size; i++) {
//            if (coordinates[i].getX() < 0 || coordinates[i].getY() < 0 || coordinates[i].getX() > 9 || coordinates[i].getY() > 9){
//                throw new OversizeException();
//            }
//        }

    }

    public boolean isHorizontal() {
        return (this.orientation == 1);
    }

    public boolean isVertical() {
        return (this.orientation == 2);
    }

    public void updateState() {

//        int shotCount = 0;
//        for (int i = 0; i < this.size; i++) {
//            if (!shotsArray[i]) shotCount++;
//        }

        shotCount++;

        if (shotCount == 0) {
            this.state = 0;
        } else if (shotCount == this.size) {
            this.state = 2;
        } else {
            this.state = 1;
        }
    }

    public boolean isSunk() {
        return (this.state==2) ;
    }


    public int getState() {
        return state;
    }

    public int getSize() {
        return size;
    }

    public int getPoints() {
        return points;
    }

    public int getBonus() {
        return bonus;
    }

    public int getX0() {
        return x0;
    }

    public int getY0() {
        return y0;
    }

    public Position[] getCoordinates() {
        return coordinates;
    }

    public Position getStart() {
        return this.coordinates[0];
    }

    public Position getEnd() {
        return this.coordinates[size-1];
    }

    public String getStateAsString(){

        switch(state) {
            case 0:
                return "Untouched";
            case 1:
                return "Shot";
            case 2:
                return "Sunk";
            default:
                return null;
        }
    }



    @Override public String toString(){
        return getClass().getSimpleName();
    }


}
