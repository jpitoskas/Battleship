package com.gpit;
import javafx.geometry.Pos;
import javafx.scene.control.Label;



public class UserInfo {


    private String userTag;
    private Label activeShipsLabel;
    private Label scoreLabel;
    private Label shotAccuracyLabel;
    private Label remainingShotsLabel;



    public UserInfo(String userTag) {
        this.userTag = userTag;

        this.activeShipsLabel = new Label(this.userTag + " Active Ships: " + String.valueOf(5));
        this.activeShipsLabel.setAlignment(Pos.CENTER);
        this.activeShipsLabel.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        this.scoreLabel = new Label(this.userTag + " Score: " + String.valueOf(0));
        this.scoreLabel.setAlignment(Pos.CENTER);
        this.scoreLabel.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        this.shotAccuracyLabel = new Label(this.userTag + " Shot Accuracy: %");
        this.shotAccuracyLabel.setAlignment(Pos.CENTER);
        this.shotAccuracyLabel.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        this.remainingShotsLabel = new Label(this.userTag + " Remaining Shots: " + String.valueOf(User.MAX_SHOTS));
        this.remainingShotsLabel.setAlignment(Pos.CENTER);
        this.remainingShotsLabel.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

    }

    public Label getActiveShipsLabel() {
        return activeShipsLabel;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public Label getShotAccuracyLabel() {
        return shotAccuracyLabel;
    }

    public Label getRemainingShotsLabel() {
        return remainingShotsLabel;
    }

    public void setActiveShipsLabel(int activeShipsCounter) {
        activeShipsLabel.setText(this.userTag + " Active Ships: " + String.valueOf(activeShipsCounter));
    }

    public void setScoreLabel(int score) {
        scoreLabel.setText(this.userTag + " Score: " + String.valueOf(score));
    }

    public void setShotAccuracyLabel(int shotAccuracy) {
        shotAccuracyLabel.setText(this.userTag + " Shot Accuracy: " + String.valueOf(shotAccuracy) + "%");
    }

    public void setRemainingShotsLabel(int remainingShots) {
        remainingShotsLabel.setText(this.userTag + " Remaining Shots: " + String.valueOf(remainingShots));
    }
}



