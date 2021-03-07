package com.gpit;

import com.gpit.exceptions.AdjacentTilesException;
import com.gpit.exceptions.InvalidCountExceception;
import com.gpit.exceptions.OverlapTilesException;
import com.gpit.exceptions.OversizeException;
import com.gpit.model.Ship;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Initializer {

    private int scenarioID;
    private Game game;
    private ArrayList<Ship> playerShips;
    private ArrayList<Ship> enemyShips;


    public Initializer() {
        this.game = new Game(new ArrayList<Ship>(), new ArrayList<Ship>());
    }

    public void initializeGame(int id) throws IOException, URISyntaxException {
        this.scenarioID = id;
        ScenarioLoader scenario = new ScenarioLoader(id);

        Mapper playerMapper = new Mapper(scenario.getPlayerScenario());
        Mapper enemyMapper = new Mapper(scenario.getEnemyScenario());

        playerShips = playerMapper.getShips();
        enemyShips = enemyMapper.getShips();
        

//        isValidGame(playerShips, enemyShips);


        this.game = new Game(playerShips, enemyShips);
//        this.game.setRound(0);
//        game.restartGameCounter();
    }
//        Mapper: scenario -> ships


    //        for (int i = 0; i < this.size; i++) {
//            if (coordinates[i].getX() < 0 || coordinates[i].getY() < 0 || coordinates[i].getX() > 9 || coordinates[i].getY() > 9){
//                throw new OversizeException();
//            }
//        }
    public void initializeGame() throws IOException, URISyntaxException {
        initializeGame(scenarioID);
    }

    public void checkValidGame() throws OversizeException, InvalidCountExceception, OverlapTilesException, AdjacentTilesException {

        checkOverSize(playerShips);
        checkOverSize(enemyShips);

        checkInvalidCount(playerShips);
        checkInvalidCount(enemyShips);

        checkOverlapTiles(playerShips);
        checkOverlapTiles(enemyShips);

        checkAdjacentTiles(playerShips);
        checkAdjacentTiles(enemyShips);

    }


    public void checkOverSize(ArrayList<Ship> ships) throws OversizeException {

        for (Ship ship: ships) {
            for (Position pos: ship.getCoordinates()) {
                if (pos.getX() < 0 || pos.getY() < 0 || pos.getX() > 9 || pos.getY() > 9) {
                    throw new OversizeException();
                }
            }
        }

    }

    public void checkInvalidCount(ArrayList<Ship> ships) throws InvalidCountExceception {

        ArrayList<String> shipTypes = new ArrayList<String>();
        for (Ship ship: ships) {
            shipTypes.add(ship.getClass().getSimpleName());
        }
        if (shipTypes.size() != 5) {
            throw new InvalidCountExceception();
        }

        if (!shipTypes.contains("Carrier")) {
            throw new InvalidCountExceception();
        }

        if (!shipTypes.contains("Destroyer")) {
            throw new InvalidCountExceception();
        }

        if (!shipTypes.contains("Submarine")) {
            throw new InvalidCountExceception();
        }

        if (!shipTypes.contains("Battleship")) {
            throw new InvalidCountExceception();
        }

        if (!shipTypes.contains("Cruiser")) {
            throw new InvalidCountExceception();
        }
    }

    public void checkOverlapTiles(ArrayList<Ship> ships) throws OverlapTilesException {

        ArrayList<Position> allShipPositions = new ArrayList<Position>();

        for (Ship ship: ships) {
            for (Position position: ship.getCoordinates()) {
                allShipPositions.add(position);
            }
        }

        Set<Position> allShipPositionsSet = new HashSet<Position>(allShipPositions);

        if(allShipPositionsSet.size() < allShipPositions.size()){
            throw new OverlapTilesException();
        }

    }

    public void checkAdjacentTiles(ArrayList<Ship> ships) throws AdjacentTilesException {

        ArrayList<Position> allShipPositions = new ArrayList<Position>();

        for (Ship ship: ships) {
            for (Position position: ship.getCoordinates()) {
                allShipPositions.add(position);
            }
        }

        Set<Position> allShipPositionsSet = new HashSet<Position>(allShipPositions);


        ArrayList<Position> allShipAdjacentPositions = new ArrayList<Position>();
        for (Ship ship: ships) {
            if (ship.isHorizontal()) {
                allShipAdjacentPositions.add(ship.getStart().getLeft());
                allShipAdjacentPositions.add(ship.getEnd().getRight());
                for (Position position: ship.getCoordinates()) {
                    allShipAdjacentPositions.add(position.getUp());
                    allShipAdjacentPositions.add(position.getDown());
                }
            }
            else if (ship.isVertical()) {
                allShipAdjacentPositions.add(ship.getStart().getUp());
                allShipAdjacentPositions.add(ship.getEnd().getDown());
                for (Position position: ship.getCoordinates()) {
                    allShipAdjacentPositions.add(position.getLeft());
                    allShipAdjacentPositions.add(position.getRight());
                }
            }
        }

        Set<Position> allShipAdjacentPositionsSet = new HashSet<Position>(allShipAdjacentPositions);

        Set<Position> intersectionSet = (new HashSet<>(allShipAdjacentPositionsSet));
        intersectionSet.retainAll(allShipPositionsSet);


        if (!intersectionSet.isEmpty()) {
            throw new AdjacentTilesException();
        }


    }

    public Game getGame() {
        return game;
    }
}
