package com.gpit;

import com.gpit.model.*;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    private List<ArrayList<Integer>> scenario;
    private ArrayList<Ship> ships;

    public Mapper(List<ArrayList<Integer>> scenario) {

        this.scenario = scenario;
        this.ships = new ArrayList<Ship>();
        scenario.forEach( (lst) -> {
            this.ships.add(transform(lst));
        } );


    }

    private Ship transform(ArrayList<Integer> shipScenario) {

        int shipType = shipScenario.get(0);
        int posY = shipScenario.get(1);
        int posX = shipScenario.get(2);
        int shipOrientation = shipScenario.get(3);

        switch (shipType) {
            case 1:
                return new Carrier(posX, posY, shipOrientation);
            case 2:
                return new Battleship(posX, posY, shipOrientation);
            case 3:
                return new Cruiser(posX, posY, shipOrientation);
            case 4:
                return new Submarine(posX, posY, shipOrientation);
            case 5:
                return new Destroyer(posX, posY, shipOrientation);
            default:
                throw new IllegalStateException("Undefined Ship Type" + String.valueOf(shipType));

        }

    }

    public ArrayList<Ship> getShips() {
        return ships;
    }



}
