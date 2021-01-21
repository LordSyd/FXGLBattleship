package com.almasb.fxglgames.Battleship;

import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.ui.FXGLButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import static com.almasb.fxgl.dsl.FXGL.*;
/**
 * This class governs the layout of the screen shown between turns. It gets passed the state of the game through it's
 * constructor. Not all cases are implemented at the moment
 */

//todo get rid of placeholder text

public class NewTurnSubScene  extends SubScene implements EventHandler<ActionEvent> {


    public NewTurnSubScene(int nextPlayerID, boolean gameRunning) {


        String nextPlayerText = "I should not appear";
        var text = getUIFactoryService().newText(nextPlayerText , Color.GOLD, 32);

        if (!gameRunning){
            switch (nextPlayerID){
                case 1 -> nextPlayerText = "Player One - Place your ships";
                case 2 -> nextPlayerText = "Player Two - Place your ships ";
            }
        }else{
            switch (nextPlayerID){
                case 1 -> nextPlayerText = "Player One - it´s your turn";
                case 2 -> nextPlayerText = "Player Two - it´s your turn";
            }
        }

        text = getUIFactoryService().newText(nextPlayerText , Color.GOLD, 30);

        text.setTextAlignment(TextAlignment.CENTER);

        var bg = new Rectangle(600, 230, Color.color(0.3627451f, 0.3627451f, 0.5627451f, 0.55));
        bg.setArcWidth(50);
        bg.setArcHeight(50);
        bg.setStroke(Color.WHITE);
        bg.setStrokeWidth(10);


        var button = new FXGLButton("READY");
        button.setOnAction(this);
        button.setTranslateX(100);
        button.setTranslateY(400);



        var stackPane = new StackPane(bg, text);
        getContentRoot().getChildren().add(stackPane);
        getContentRoot().getChildren().add(button);

    }



    @Override
    public void handle(ActionEvent event) {

        Player activePlayer;


        System.out.println("pressed");

        BattleshipMain.closeTurnMenu();
        TileFactory.updateBoardState();
        if(BattleshipMain.isPlayer1Turn()){
            activePlayer = BattleshipMain.player1;
        }else{
            activePlayer = BattleshipMain.player2;
        }
        ShipFactory.updateShipSpawns(activePlayer);

    }
}

