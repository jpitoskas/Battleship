package com.gpit;

import com.gpit.model.Ship;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private int round;
    private User player;
    private User enemy;
    private UserUI playerUI;
    private UserUI enemyUI;
    private String winner;
    private Label roundLabel;
    private String modeAI;
    private ArrayList<Position> previousShipShotsAI;
    private ArrayList<Position> possibleMovesAI;


    public Game(ArrayList<Ship> playerShips, ArrayList<Ship> enemyShips) {
        this.round = 0;
        this.player = new User("player", playerShips);
        this.enemy = new User("enemy", enemyShips);
        this.playerUI = new UserUI("Player");
        this.enemyUI = new UserUI("Enemy");
        this.winner = null;

        roundLabel = new Label();
        roundLabel.setText("Round " + String.valueOf(round));
        roundLabel.setAlignment(Pos.CENTER);
        roundLabel.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        modeAI = "RANDOM";

        previousShipShotsAI = new ArrayList<Position>();
        possibleMovesAI = new ArrayList<Position>();


    }

    public int getRound() {
        return round;
    }

    public User getPlayer() {
        return player;
    }

    public User getEnemy() {
        return enemy;
    }

    public UserUI getPlayerUI() {
        return playerUI;
    }

    public UserUI getEnemyUI() {
        return enemyUI;
    }

    public void nextRound(){
        this.round++;
        roundLabel.setText("Round " + String.valueOf(round));
    }


    public void restartGameCounter() {
        this.round = 1;
    }

    public boolean isValidShot(int x, int y, String userTag) {

        if ((x < 0) || (y < 0) || (x > 9) || (y > 9)) {
            return false;
        }
        else {
            if (userTag.equals("Player")) {
                return (enemy.getBoardBlock(x, y) == 0);
            } else {
                return (player.getBoardBlock(x, y) == 0);
            }
        }
    }

    public void shoot(int x, int y, String userTag) {
        if (userTag.equals("Player")) {
            enemy.shootBoardBlock(x, y);
        }
        else {
            player.shootBoardBlock(x,y);
        }

    }

    public void playerRound(int x, int y) {




        shoot(x,y, "Player");
        getPlayer().addToShotHistory(new Position(x,y));
        getPlayer().incrementShotsCounter();
        getPlayerUI().getUserInfo().setRemainingShotsLabel(User.MAX_SHOTS - getPlayer().getShotsCounter());
        if (getEnemy().isShip(x,y)) {
            // Draw ship shot on UI
            enemyUI.getBoardUI().drawShipShot(x,y);
            // Get Ship shot
            Ship enemyShipShot = getEnemy().getShipAtPosition(x,y);
            // Update ship state {Untouched, Shot, Sunk}
            enemyShipShot.updateState();
            // Increment Score and Update Score of UI
            getPlayer().incrementScore(enemyShipShot.getPoints());
            getPlayerUI().getUserInfo().setScoreLabel(getPlayer().getScore());
            // Increment Accurate Shots Number
            getPlayer().incrementAccurateShotsCounter();
            // Keep track of which Ship was shot
            getPlayer().addToShotOutcomeHistory(enemyShipShot.getClass().getSimpleName());

            if (enemyShipShot.isSunk()) {
                // Increment Number of Sunk Ships
                getEnemy().incrementShipSunkCounter();
                // Show Number of Active Ships
                getEnemyUI().getUserInfo().setActiveShipsLabel(5-getEnemy().getShipsSunkCounter());

                // Add Bonus Sunk Score and Update Score of UI
                getPlayer().incrementScore(enemyShipShot.getBonus());
                getPlayerUI().getUserInfo().setScoreLabel(getPlayer().getScore());

                            }
        }
        else {
            // Draw Off-Target Shot
            enemyUI.getBoardUI().drawShot(x,y);
            // Keep track that the shot was Off-Target
            getPlayer().addToShotOutcomeHistory("Off-Target");

        }

        getPlayerUI().getUserInfo().setShotAccuracyLabel((int) Math.round(getPlayer().getAccurateShotsCounter()*100.0/getPlayer().getShotsCounter()));

    }

    public void enemyRoundAI() {



        Position shootPos  = findShootingPosition();
        int x = shootPos.getX();
        int y = shootPos.getY();

        shoot(x,y, "Enemy");
        getEnemy().addToShotHistory(new Position(x,y));
        getEnemy().incrementShotsCounter();
        getEnemyUI().getUserInfo().setRemainingShotsLabel(User.MAX_SHOTS - getEnemy().getShotsCounter());
        if (getPlayer().isShip(x,y)) {
            // Draw ship shot on UI
            playerUI.getBoardUI().drawShipShot(x,y);
            // Get Ship shot
            Ship playerShipShot = getPlayer().getShipAtPosition(x,y);
            // Update ship state {Untouched, Shot, Sunk}
            playerShipShot.updateState();
            // Increment Score and Update Score of UI
            getEnemy().incrementScore(playerShipShot.getPoints());
            getEnemyUI().getUserInfo().setScoreLabel(getEnemy().getScore());
            // Increment Accurate Shots Number
            getEnemy().incrementAccurateShotsCounter();
            // Keep track of which Ship was shot
            getEnemy().addToShotOutcomeHistory(playerShipShot.getClass().getSimpleName());

            if (playerShipShot.isSunk()) {
                // Increment Number of Sunk Ships
                getPlayer().incrementShipSunkCounter();
                // Show Number of Active Ships
                getPlayerUI().getUserInfo().setActiveShipsLabel(5-getPlayer().getShipsSunkCounter());

                // Add Bonus Sunk Score and Update Score of UI
                getEnemy().incrementScore(playerShipShot.getBonus());
                getEnemyUI().getUserInfo().setScoreLabel(getEnemy().getScore());
            }
        }
        else {
            // Draw Off-Target Shot
            playerUI.getBoardUI().drawShot(x,y);
            // Keep track that the shot was Off-Target
            getEnemy().addToShotOutcomeHistory("Off-Target");
        }

        getEnemyUI().getUserInfo().setShotAccuracyLabel((int) Math.round(getEnemy().getAccurateShotsCounter()*100.0/getEnemy().getShotsCounter()));

    }

    public boolean hasStarted() {
        return (this.round != 0);
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean hasEnded() {

        if (getPlayer().getShotsCounter() == User.MAX_SHOTS && getEnemy().getShotsCounter()==User.MAX_SHOTS) {
            if (getPlayer().getScore() > getEnemy().getScore()) {
                winner = "Player";
                return true;
            } else if (getPlayer().getScore() < getEnemy().getScore()) {
                winner = "Enemy";
                return true;
            } else if (getPlayer().getScore() == getEnemy().getScore()) {
                winner = "Draw";
                return true;
            }
        }
        else if (getPlayer().getShipsSunkCounter() == 5) {
            winner = "Enemy";
            return true;
        }
        else if (getEnemy().getShipsSunkCounter() == 5) {
            winner = "Player";
            return true;
        }

        return false;
    }

    public String getWinner() {
        return winner;
    }

    public Label getRoundLabel() {
        return roundLabel;
    }

    private Position findShootingPosition() {
        int x;
        int y;
        Position pos;

        if (!previousShipShotsAI.isEmpty()) {
            if (getPlayer().getShipAtPosition(previousShipShotsAI.get(0).getX(), previousShipShotsAI.get(0).getY()).isSunk()) {
                modeAI = "RANDOM";
                previousShipShotsAI.clear();
                possibleMovesAI.clear();
            }
        }

        Random rand = new Random();
        if (modeAI.equals("RANDOM")) {

            x = rand.nextInt(10);
            y = rand.nextInt(10);

            while (!isValidShot(x, y, "Enemy")) {
                x = rand.nextInt(10);
                y = rand.nextInt(10);
            }
            if (getPlayer().isShip(x,y)){
                modeAI = "FIRST_SHIP_SHOT";
                pos = new Position(x,y);
                previousShipShotsAI.add(pos);

                Position left = pos.getLeft();
                if (isValidShot(left.getX(), left.getY(),"Enemy")) {
                    possibleMovesAI.add(left);
                }

                Position right = pos.getRight();
                if (isValidShot(right.getX(), right.getY(),"Enemy")) {
                    possibleMovesAI.add(right);
                }

                Position up = pos.getUp();
                if (isValidShot(up.getX(), up.getY(),"Enemy")) {
                    possibleMovesAI.add(up);
                }

                Position down = pos.getDown();
                if (isValidShot(down.getX(), down.getY(),"Enemy")) {
                    possibleMovesAI.add(down);
                }

            }
        }
        else if (modeAI.equals("FIRST_SHIP_SHOT")) {

            int r = rand.nextInt(possibleMovesAI.size());
            pos = possibleMovesAI.get(r);
            x = pos.getX();
            y = pos.getY();

            if (getPlayer().isShip(x,y)) {

                previousShipShotsAI.add(pos);

                if (getPlayer().getShipAtPosition(x, y).isSunk()) {
                    modeAI = "RANDOM";
                    previousShipShotsAI.clear();
                    possibleMovesAI.clear();
                }
                else {

                    possibleMovesAI.clear();

                    if (getPlayer().getShipAtPosition(x, y).isHorizontal()) {
                        modeAI = "HORIZONTAL";
                        Position right;
                        Position left;
                        if (x > previousShipShotsAI.get(0).getX()) {
                            right = pos.getRight();
                            left = previousShipShotsAI.get(0).getLeft();
                        }
                        else {
                            right = previousShipShotsAI.get(0).getRight();
                            left = pos.getLeft();
                        }

                        if (isValidShot(right.getX(), right.getY(), "Enemy")) {
                            possibleMovesAI.add(right);
                        }
                        if (isValidShot(left.getX(), left.getY(), "Enemy")) {
                            possibleMovesAI.add(left);
                        }
                    }
                    else if (getPlayer().getShipAtPosition(x, y).isVertical()) {
                        modeAI = "VERTICAL";
                        Position down ;
                        Position up;
                        if (y > previousShipShotsAI.get(0).getY()) {
                            down = pos.getDown();
                            up = previousShipShotsAI.get(0).getUp();
                        }
                        else {
                            down = previousShipShotsAI.get(0).getDown();
                            up = pos.getUp();
                        }

                        if (isValidShot(down.getX(), down.getY(), "Enemy")) {
                            possibleMovesAI.add(down);
                        }
                        if (isValidShot(up.getX(), up.getY(), "Enemy")) {
                            possibleMovesAI.add(up);
                        }
                    }

                }
            }
            else{
                possibleMovesAI.remove(r);
            }

        }
        else if (modeAI.equals("HORIZONTAL")) {
            int r = rand.nextInt(possibleMovesAI.size());
            pos = possibleMovesAI.get(r);
            x = pos.getX();
            y = pos.getY();

            if (getPlayer().isShip(x, y)) {
                previousShipShotsAI.add(pos);
                possibleMovesAI.clear();

                if (getPlayer().getShipAtPosition(x, y).isSunk()) {
                    modeAI = "RANDOM";
                    previousShipShotsAI.clear();
                }
                else{
                    Position lefter = previousShipShotsAI.get(0);
                    Position righter = previousShipShotsAI.get(0);
                    for (Position current: previousShipShotsAI) {
                        if (current.getX() < lefter.getX()) {
                            lefter = current;
                        }
                        if (current.getX() > righter.getX()) {
                            righter = current;
                        }
                    }
                    Position left = lefter.getLeft();
                    Position right = righter.getRight();

                    if (isValidShot(left.getX(), left.getY(), "Enemy")) {
                        possibleMovesAI.add(left);
                    }
                    if (isValidShot(right.getX(), right.getY(), "Enemy")) {
                        possibleMovesAI.add(right);
                    }
                }

            }
            else {
                possibleMovesAI.remove(r);
            }
        }
        else if (modeAI.equals("VERTICAL")) {
            int r = rand.nextInt(possibleMovesAI.size());
            pos = possibleMovesAI.get(r);
            x = pos.getX();
            y = pos.getY();

            if (getPlayer().isShip(x, y)) {
                previousShipShotsAI.add(pos);
                possibleMovesAI.clear();

                if (getPlayer().getShipAtPosition(x, y).isSunk()) {
                    modeAI = "RANDOM";
                    previousShipShotsAI.clear();
                }
                else{
                    Position upper = previousShipShotsAI.get(0);
                    Position downer = previousShipShotsAI.get(0);
                    for (Position current: previousShipShotsAI) {
                        if (current.getY() < upper.getY()) {
                            upper = current;
                        }
                        if (current.getY() > downer.getY()) {
                            downer = current;
                        }
                    }
                    Position up = upper.getUp();
                    Position down = downer.getDown();

                    if (isValidShot(up.getY(), up.getY(), "Enemy")) {
                        possibleMovesAI.add(up);
                    }
                    if (isValidShot(down.getX(), down.getY(), "Enemy")) {
                        possibleMovesAI.add(down);
                    }
                }

            }
            else {
                possibleMovesAI.remove(r);
            }
        }
        else {
            x = 190;
            y = 3210;
        }


        return new Position(x,y);

    }
}



//    public void enemyRoundAI() {
//
//
//
//        Position shootPos  = findShootingPosition();
//        int x = shootPos.getX();
//        int y = shootPos.getY();
//
//        shoot(x,y, "Enemy");
//        getEnemy().addToShotHistory(new Position(x,y));
//        getEnemy().incrementShotsCounter();
//        getEnemyUI().getUserInfo().setRemainingShotsLabel(User.MAX_SHOTS - getEnemy().getShotsCounter());
//        if (getPlayer().isShip(x,y)) {
//            // Draw ship shot on UI
//            playerUI.getBoardUI().drawShipShot(x,y);
//            // Get Ship shot
//            Ship playerShipShot = getPlayer().getShipAtPosition(x,y);
//            // Update ship state {Untouched, Shot, Sunk}
//            playerShipShot.updateState();
//            // Increment Score and Update Score of UI
//            getEnemy().incrementScore(playerShipShot.getPoints());
//            getEnemyUI().getUserInfo().setScoreLabel(getEnemy().getScore());
//            // Increment Accurate Shots Number
//            getEnemy().incrementAccurateShotsCounter();
//            // Keep track of which Ship was shot
//            getEnemy().addToShotOutcomeHistory(playerShipShot.getClass().getSimpleName());
//
//            if (playerShipShot.isSunk()) {
//                // Increment Number of Sunk Ships
//                getPlayer().incrementShipSunkCounter();
//                // Show Number of Active Ships
//                getPlayerUI().getUserInfo().setActiveShipsLabel(5-getPlayer().getShipsSunkCounter());
//
//                // Add Bonus Sunk Score and Update Score of UI
//                getEnemy().incrementScore(playerShipShot.getBonus());
//                getEnemyUI().getUserInfo().setScoreLabel(getEnemy().getScore());
//            }
//        }
//        else {
//            // Draw Off-Target Shot
//            playerUI.getBoardUI().drawShot(x,y);
//            // Keep track that the shot was Off-Target
//            getEnemy().addToShotOutcomeHistory("Off-Target");
//        }
//
//        getEnemyUI().getUserInfo().setShotAccuracyLabel((int) Math.round(getEnemy().getAccurateShotsCounter()*100.0/getEnemy().getShotsCounter()));
//
//    }