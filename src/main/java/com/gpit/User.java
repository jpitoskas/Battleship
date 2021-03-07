package com.gpit;

import com.gpit.model.Ship;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class User {
    private int score;
    private Board board;
    private Board shotsBoard;
    private ArrayList<Ship> ships;
    private String userTag;
    public static int MAX_SHOTS = 40;
    private int shotsCounter;
    private int accurateShotsCounter;
    private int shipsSunkCounter;
    private ArrayList<Position> shotHistory;
    private ArrayList<String> shotOutcomeHistory;

    public User(String tag, ArrayList<Ship> ships) {
        this.score = 0;
        this.board = new Board();
        this.shotsBoard = new Board();
        this.ships = ships;
        this.userTag = tag;
        this.shotsCounter = 0;
        this.shipsSunkCounter = 0;
        this.accurateShotsCounter = 0;
        this.shotHistory = new ArrayList<Position>();
        this.shotOutcomeHistory = new ArrayList<String>();
    }


    public int getScore() {
        return score;
    }

    public Board getBoard() {
        return board;
    }

    public int getBoardBlock(int x, int y) {
        return board.getGridBlock(x, y);
    }

    public void shootBoardBlock(int x, int y) {
        board.updateGrid(x,y);
    }


    public Board getShotsBoard() {
        return shotsBoard;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public String getUserTag() {
        return userTag;
    }

    public int getShotsCounter() {
        return shotsCounter;
    }

    public int getAccurateShotsCounter() {
        return accurateShotsCounter;
    }

    public int getShipsSunkCounter() {
        return shipsSunkCounter;
    }

    public ArrayList<Position> getShotHistory() {
        return shotHistory;
    }

    public ArrayList<String> getShotOutcomeHistory() {
        return shotOutcomeHistory;
    }

    public ArrayList<Position> getAllShipPositions() {

        ArrayList<Position> allShipPositions = new ArrayList<>();

        for (Ship ship: ships) {
            for (Position position: ship.getCoordinates()) {
                allShipPositions.add(position);
            }
        }

        return allShipPositions;
    }

    public boolean isShip(int x, int y) {
        return getAllShipPositions().contains(new Position(x,y));
    }

    public Ship getShipAtPosition(int x, int y) {
        for (Ship ship: ships) {
            for (Position position: ship.getCoordinates()) {
                if (position.equals(new Position(x,y))) {
                    return ship;
                }
            }
        }
        return null;
    }

    public void incrementScore(int points){
        this.score += points;
    }

    public void incrementShotsCounter() {
        this.shotsCounter++;
    }

    public void incrementAccurateShotsCounter() { this.accurateShotsCounter++;}

    public void incrementShipSunkCounter() {
        this.shipsSunkCounter++;
    }

    public void addToShotHistory(Position pos) {
        shotHistory.add(pos);
    }

    public void addToShotOutcomeHistory(String shotOutcome) {
        shotOutcomeHistory.add(shotOutcome);
    }

}
