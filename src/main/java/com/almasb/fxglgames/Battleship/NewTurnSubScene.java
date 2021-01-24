package com.almasb.fxglgames.Battleship;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.ui.FXGLButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxglgames.Battleship.BattleshipMain.*;
import static com.almasb.fxglgames.Battleship.ShipFactory.*;
import static com.almasb.fxglgames.Battleship.TileFactory.*;
import static com.almasb.fxglgames.Battleship.ClickBehaviourComponent.*;
/**
 * This class governs the layout of the screen shown between turns. It gets passed the state of the game through it's
 * constructor. First case checks if the ships are placed, if not the player gets a message to place ships.
 * After the ships are placed the next message indicates turn order of the players. GameRunning is true after the ships are placed.
 */


public class NewTurnSubScene  extends SubScene implements EventHandler<ActionEvent> {

    public static void buildBackground() {
        Entity background = entityBuilder()
                .view("yourturn.png")
                .at(0,0)
                .zIndex(-500)
                .scale(1,1)
                .buildAndAttach();
    }




    public NewTurnSubScene(int nextPlayerID, boolean gameRunning) {

        buildBackground();


        String nextPlayerText = "I should not appear";
        var text = getUIFactoryService().newText(nextPlayerText, Color.SILVER, 32);

        if (!gameRunning) {

            switch (nextPlayerID) {
                case 1 -> nextPlayerText = "Player One - Place your ships!";
                case 2 -> nextPlayerText = "Player Two - Place your ships!";
            }
        }else{
                switch (nextPlayerID){
                    case 1 -> nextPlayerText = "Player One - your turn!";
                    case 2 -> nextPlayerText = "Player Two - your turn!";
                }
            }


        text = getUIFactoryService().newText(nextPlayerText , Color.BLACK, 30);

        text.setTextAlignment(TextAlignment.CENTER);
        text.setTranslateX(370);
        text.setTranslateY(150);

        var bg = new Rectangle(450, 100, Color.color(0.102014f, 0.510800f, 0.8038743f, 0.80));
        bg.setArcWidth(50);
        bg.setArcHeight(50);
        bg.setStroke(Color.DARKGRAY);
        bg.setStrokeWidth(10);
        bg.setTranslateX(370);
        bg.setTranslateY(150);

        var button = new FXGLButton("READY");
        button.setOnAction(this);
        button.setTranslateX(500);
        button.setTranslateY(400);
        button.setScaleX(1.5);
        button.setScaleY(1.5);


        var stackPane = new StackPane(bg, text);
        getContentRoot().getChildren().add(stackPane);
        getContentRoot().getChildren().add(button);

    }

    @Override
    public void handle(ActionEvent event) {

        Player activePlayer;
        System.out.println("pressed");
        closeTurnMenu();
        updateBoardState();

        if(isPlayer1Turn()){
            activePlayer = player1;
        }else{
            activePlayer = player2;
        }
        updateShipSpawns(activePlayer);
    }
}

