package com.gpit;

import com.gpit.exceptions.AdjacentTilesException;
import com.gpit.exceptions.InvalidCountExceception;
import com.gpit.exceptions.OverlapTilesException;
import com.gpit.exceptions.OversizeException;
import com.gpit.model.Ship;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Stack;

import static java.lang.Math.min;

public class BattleshipApp extends Application {
    private VBox screen;
    private boolean hasStarted;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Initializer init = new Initializer();
        this.screen = new VBox();
        this.hasStarted = false;





        MenuBar menuBar = new MenuBar();

        VBox topScreen = new VBox();
        topScreen.getChildren().add(menuBar);

        this.screen.getChildren().add(topScreen);
        this.screen.setSpacing(20);

        play(init);


        Menu applicationMenu = new Menu("Application");
        MenuItem menuStartItem = new MenuItem("Start");
        MenuItem menuLoadItem = new MenuItem("Load");
        MenuItem menuExitItem = new MenuItem("Exit");
        applicationMenu.getItems().addAll(menuStartItem, menuLoadItem, menuExitItem);

        Menu detailsMenu = new Menu("Details");
        MenuItem menuEnemyShipsItem = new MenuItem("Enemy Ships");
        MenuItem menuPlayerShotsItem = new MenuItem("Player Shots");
        MenuItem menuEnemyShotsItem = new MenuItem("Enemy Shots");
        detailsMenu.getItems().addAll(menuEnemyShipsItem, menuPlayerShotsItem, menuEnemyShotsItem);

        menuBar.getMenus().addAll(applicationMenu, detailsMenu);



        menuEnemyShipsItem.setOnAction( event -> {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.getDialogPane().getScene().getWindow().setOnCloseRequest(e -> alert.hide());
            alert.setTitle("Enemy Ships");
            alert.setHeaderText("Enemy Ships:");
            String message = "";
            for (Ship ship: init.getGame().getEnemy().getShips()) {
                message += ship.getClass().getSimpleName() + " :  " + ship.getStateAsString() + "\n";
            }
            alert.setContentText(message);
            alert.showAndWait();
        });

        menuPlayerShotsItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.getDialogPane().getScene().getWindow().setOnCloseRequest(e -> alert.hide());
            alert.setTitle("Player Shots");
            alert.setHeaderText("Five most recent Player's Shots:");
            String message = "";
            ArrayList<Position> playerShotHistory = init.getGame().getPlayer().getShotHistory();
            ArrayList<String> playerShotOutcomeHistory = init.getGame().getPlayer().getShotOutcomeHistory();

            for (int i = playerShotHistory.size()-1; i > playerShotHistory.size() - min(playerShotHistory.size(),5) - 1; i--) {
                message += "Shot " + playerShotHistory.get(i).toString() + " :  " + playerShotOutcomeHistory.get(i) + "\n";
            }
            alert.setContentText(message);
            alert.show();
        });

        menuEnemyShotsItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.getDialogPane().getScene().getWindow().setOnCloseRequest(e -> alert.hide());
            alert.setTitle("Enemy Shots");
            alert.setHeaderText("Five most recent Enemy's Shots:");
            String message = "";
            ArrayList<Position> enemyShotHistory = init.getGame().getEnemy().getShotHistory();
            ArrayList<String> enemyShotOutcomeHistory = init.getGame().getEnemy().getShotOutcomeHistory();
            for (int i = enemyShotHistory.size()-1; i > enemyShotHistory.size() - min(enemyShotHistory.size(),5) - 1; i--) {
                message += "Shot " + enemyShotHistory.get(i).toString() + " :  " + enemyShotOutcomeHistory.get(i) + "\n";
            }
            alert.setContentText(message);
            alert.show();
        });



        TextInputDialog loadDialog = new TextInputDialog();
        loadDialog.setTitle("Load Scenario");
        loadDialog.setContentText("SCENARIO_ID");
        loadDialog.setHeaderText(null);

        menuLoadItem.setOnAction(event -> {
            loadDialog.showAndWait();
        });

        Button loadDialogButtonOK = (Button) loadDialog.getDialogPane().lookupButton(ButtonType.OK);
        loadDialogButtonOK.setOnAction(event -> {
            try {
                init.initializeGame(Integer.parseInt(loadDialog.getEditor().getText()));
                init.checkValidGame();
                screen.getChildren().remove(screen.getChildren().size()-1);
                screen.getChildren().remove(screen.getChildren().size()-1);
                screen.getChildren().remove(screen.getChildren().size()-1);
                play(init);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Load Scenario");
                alert.setHeaderText("");
                alert.setContentText("Scenario with ID '" + loadDialog.getEditor().getText() + "' was loaded successfully!\nPress START for the game to begin.");
                alert.show();

            } catch (OversizeException e) {
                showAlert("Oversize Error", loadDialog.getEditor().getText());
                e.printStackTrace();
            } catch (InvalidCountExceception e) {
                showAlert("Invalid Count Error", loadDialog.getEditor().getText());
                e.printStackTrace();
            } catch (OverlapTilesException e) {
                showAlert("Overlap Tiles Error", loadDialog.getEditor().getText());
                e.printStackTrace();
            } catch (AdjacentTilesException e) {
                showAlert("Adjacent Tiles Error", loadDialog.getEditor().getText());
                e.printStackTrace();
            } catch (Exception e) {
                showAlert("Scenario Not Found", loadDialog.getEditor().getText());
                e.printStackTrace();
            }

        });

        menuStartItem.setOnAction(event -> {



            try {
                init.initializeGame();
                screen.getChildren().remove(screen.getChildren().size()-1);
                screen.getChildren().remove(screen.getChildren().size()-1);
                screen.getChildren().remove(screen.getChildren().size()-1);
                init.getGame().setRound(0);
                init.getGame().nextRound();
                play(init);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Start Game");
                alert.setHeaderText("");
                alert.setContentText("The game has started. Prepare for battle!");
                alert.show();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

//            VBox vb = play(init);
//            primaryStage.setScene(new Scene(vb, 860,540));
        });

        menuExitItem.setOnAction(event -> Platform.exit());


        Scene mainScene = new Scene(screen, 900, 600);
        primaryStage.setTitle("MediaLab Battleship");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public void play(Initializer init){

        Game game = init.getGame();




        HBox playerAxisX = new HBox();
        for (int i = 0; i < 10; i++) {
            Rectangle tile = new Rectangle(30,30);
            tile.setFill(Color.TRANSPARENT);
            tile.setStroke(Color.TRANSPARENT);
            Text text = new Text(String.valueOf(i));
            playerAxisX.getChildren().add(new StackPane(tile,text));
        }

        VBox playerAxisY = new VBox();
        Rectangle playerEmptyTile = new Rectangle(30,30);
        playerEmptyTile.setFill(Color.TRANSPARENT);
        playerEmptyTile.setStroke(Color.TRANSPARENT);
        playerAxisY.getChildren().add(playerEmptyTile);
        for (int i = 0; i < 10; i++) {
            Rectangle tile = new Rectangle(30,30);
            tile.setFill(Color.TRANSPARENT);
            tile.setStroke(Color.TRANSPARENT);
            Text text = new Text(String.valueOf(i));
            playerAxisY.getChildren().add(new StackPane(tile,text));
        }

        HBox enemyAxisX = new HBox();
        for (int i = 0; i < 10; i++) {
            Rectangle tile = new Rectangle(30,30);
            tile.setFill(Color.TRANSPARENT);
            tile.setStroke(Color.TRANSPARENT);
            Text text = new Text(String.valueOf(i));
            enemyAxisX.getChildren().add(new StackPane(tile,text));
        }

        VBox enemyAxisY = new VBox();
        Rectangle enemyEmptyTile = new Rectangle(30,30);
        enemyEmptyTile.setFill(Color.TRANSPARENT);
        enemyEmptyTile.setStroke(Color.TRANSPARENT);
        enemyAxisY.getChildren().add(enemyEmptyTile);
        for (int i = 0; i < 10; i++) {
            Rectangle tile = new Rectangle(30,30);
            tile.setFill(Color.TRANSPARENT);
            tile.setStroke(Color.TRANSPARENT);
            Text text = new Text(String.valueOf(i));
            enemyAxisY.getChildren().add(new StackPane(tile,text));
        }


        UserInfo playerInfo = game.getPlayerUI().getUserInfo();
        BoardUI playerBoard = game.getPlayerUI().getBoardUI();

        playerBoard.drawShips(game.getPlayer().getShips());

        UserInfo enemyInfo = game.getEnemyUI().getUserInfo();
        BoardUI enemyBoard = game.getEnemyUI().getBoardUI();

//        enemyBoard.drawShips(game.getEnemy().getShips());

        VBox playerBoardVBox = new VBox();
        playerBoardVBox.getChildren().addAll(playerAxisX, playerBoard.getBoard());

        VBox enemyBoardVBox = new VBox();
        enemyBoardVBox.getChildren().addAll(enemyAxisX, enemyBoard.getBoard());

        HBox playerBoardFull = new HBox();
        playerBoardFull.getChildren().addAll(playerAxisY, playerBoardVBox);

        HBox enemyBoardFull = new HBox();
        enemyBoardFull.getChildren().addAll(enemyAxisY, enemyBoardVBox);


        VBox playerVBox = new VBox();
        playerVBox.getChildren().addAll(playerInfo.getActiveShipsLabel(), playerInfo.getScoreLabel(), playerInfo.getShotAccuracyLabel(), playerBoardFull, playerInfo.getRemainingShotsLabel());
        playerVBox.setAlignment(Pos.CENTER);
        playerVBox.setSpacing(5);


        VBox enemyVBox = new VBox();
        enemyVBox.getChildren().addAll(enemyInfo.getActiveShipsLabel(), enemyInfo.getScoreLabel(), enemyInfo.getShotAccuracyLabel(), enemyBoardFull, enemyInfo.getRemainingShotsLabel());
        enemyVBox.setAlignment(Pos.CENTER);
        enemyVBox.setSpacing(5);


        HBox screenCenter = new HBox();
        screenCenter.getChildren().addAll(playerVBox, enemyVBox);
        screenCenter.setAlignment(Pos.CENTER);
        screenCenter.setSpacing(50);


        ComboBox<Integer> insertPosX = new ComboBox<>();
        insertPosX.getItems().addAll(0,1,2,3,4,5,6,7,8,9);

        ComboBox<Integer> insertPosY = new ComboBox<>();
        insertPosY.getItems().addAll(0,1,2,3,4,5,6,7,8,9);

        //        insertPosX.setValue(0);
        //        insertPosY.setValue(0);

//        TextField insertPosX = new TextField();
//        TextField insertPosY = new TextField();

        Button button = new Button("Shoot");
        if (game.hasStarted() && !game.hasEnded()) {
            button.setDisable(false);
        }
        else {
            button.setDisable(true);
        }
        button.setOnAction(event -> {

            if (insertPosX.getValue() != null && insertPosY.getValue() !=null) {
                int x = insertPosX.getSelectionModel().getSelectedItem();
                int y = insertPosY.getSelectionModel().getSelectedItem();

                if (game.isValidShot(x,y, "Player")) {

                    init.getGame().nextRound();

                    game.playerRound(x, y);

                    if (game.hasEnded()) {
                        button.setDisable(true);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game Finished");
                        alert.setHeaderText("");
                        if (!game.getWinner().equals("Draw")) {
                            alert.setContentText(game.getWinner() + " has won the game!");
                        }
                        else {
                            alert.setContentText("It's a Draw.");
                        }
                        alert.show();
                    }

                    game.enemyRoundAI();

                    if (game.hasEnded()) {
                        button.setDisable(true);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game Finished");
                        alert.setHeaderText("");
                        if (!game.getWinner().equals("Draw")) {
                            alert.setContentText(game.getWinner() + " has won the game!");
                        }
                        else {
                            alert.setContentText("It's a Draw.");
                        }
                        alert.show();
                    }

//                    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//                    executorService.schedule(game::enemyRoundAI, 1, TimeUnit.SECONDS);
                }

                insertPosX.setValue(null);
                insertPosY.setValue(null);


            }
        });

        HBox screenBottom = new HBox();
        screenBottom.getChildren().addAll(insertPosX, insertPosY, button);
        screenBottom.setAlignment(Pos.CENTER);
        screenBottom.setSpacing(50);

        Label roundLabel = game.getRoundLabel();

        screen.getChildren().addAll(roundLabel, screenCenter, screenBottom);
    }

    public void showAlert(String message, String id) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Loading Error");
        alert.setHeaderText("Unable to load scenario with ID: " + "'" + id + "'");
        alert.setContentText(message);
        alert.show();
    }

    public void drawMove(Text text) {
        text.setText("O");
        text.setFill(Color.BLACK);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
