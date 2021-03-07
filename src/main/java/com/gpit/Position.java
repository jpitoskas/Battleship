package com.gpit;

import java.util.Objects;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position getLeft() {
        return new Position(x-1, y);
    }

    public Position getRight() {
        return new Position(x+1, y);
    }

    public Position getUp() {
        return new Position(x, y-1);
    }

    public Position getDown() {
        return new Position(x, y+1);
    }

    @Override
    public boolean equals(Object pos)
    {
        return (this.x ==((Position) pos).getX() && this.y == ((Position) pos).getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }


    @Override public String toString(){
        return "(" + String.valueOf(this.x) + "," + String.valueOf(this.y) + ")";
    }

}
