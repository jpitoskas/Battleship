package com.gpit;

import com.gpit.model.Ship;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BoardUI {

    private GridPane board;
    private int size;


    public BoardUI() {
        this.board = new GridPane();
        this.size = 10;

        board.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        board.setAlignment(Pos.CENTER);


        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                Rectangle tile = new Rectangle(30, 30);
                tile.setFill(Color.LIGHTBLUE);
                tile.setStroke(Color.BLACK);
                board.add(tile, i, j );
//                int finalJ = j;
//                int finalI = i;
//                tile.setOnMouseClicked(event -> drawShot(finalI, finalJ));
            }
        }
//        board.getChildren().remove(1*10 + 5);
//        Rectangle tile = new Rectangle(30,30);
//        tile.setFill(Color.BLACK);
//        tile.setStroke(Color.BLACK);
//        board.add(tile, 1, 5);


    }

//    public void drawNode (int x, int y) {
//
//        Node result = null;
//        ObservableList<Node> children = board.getChildren();
//
//        for (Node node : children) {
//            if(board.getRowIndex(node) == y && board.getColumnIndex(node) == x) {
//                node.getProperties().setFill(Color.LIGHTBLUE);
////                result = node;
//                break;
//            }
//        }
//
//    }

    public Rectangle getBlock(int x, int y) {
        return (Rectangle) board.getChildren().get(x*this.size + y);
    }

    public Color getBlockColor(int x, int y){
        return (Color) getBlock(x,y).getFill();
    }

//    public boolean isValidShot(int x, int y) {
//
//        if getBlockColor
//
//    }

    public void draw(int x, int y, Color color) {
        getBlock(x,y).setFill(color);
    }

    public void drawShot(int x, int y) {
        draw(x,y, Color.WHITE);
    }

    public void drawShipShot(int x, int y) {
        draw(x,y, Color.RED);
    }


    public void drawShips(ArrayList<Ship> ships) {
        for (Ship ship : ships) {
            for (Position position : ship.getCoordinates()) {
                int x = position.getX();
                int y = position.getY();
                draw(x,y, Color.GREY);
            }
        }

    }

    public GridPane getBoard() {
        return board;
    }
}
