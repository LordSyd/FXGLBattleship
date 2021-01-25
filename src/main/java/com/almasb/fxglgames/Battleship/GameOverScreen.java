package com.almasb.fxglgames.Battleship;

import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.ui.FXGLButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import com.almasb.fxgl.entity.Entity;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * Simple layout class controlling the game over screen.
 * It defines the placement of the text and the restart button.
 */

public class GameOverScreen  extends SubScene implements EventHandler<ActionEvent> {


    public static void buildBackground() {
        Entity background = entityBuilder()
                .view("game_over.png")
                .at(0,0)
                .zIndex(-500)
                .scale(1.5,1.5)
                .buildAndAttach();
    }



    public GameOverScreen(int deadPlayer) {

        String deadPlayerText = "I should not appear";
        var text = getUIFactoryService().newText(deadPlayerText , Color.BLUE, 32);

            switch (deadPlayer){
                case 1 -> deadPlayerText = "Player One Lost!";
                case 2 -> deadPlayerText = "Player Two Lost!";
            }

        text = getUIFactoryService().newText(deadPlayerText , Color.DARKRED, 30);

        text.setTextAlignment(TextAlignment.CENTER);
        text.setTranslateX(370);
        text.setTranslateY(150);

        var bg = new Rectangle(450, 100, Color.color(0.102014f, 0.510800f, 0.8038743f, 0.55));
        bg.setArcWidth(50);
        bg.setArcHeight(50);
        bg.setStroke(Color.DARKRED);
        bg.setStrokeWidth(10);
        bg.setTranslateX(370);
        bg.setTranslateY(150);

        var restart = new FXGLButton("Restart");
        restart.setOnAction(this);
        restart.setTranslateX(500);
        restart.setTranslateY(400);
        restart.setScaleX(1.5);
        restart.setScaleY(1.5);


        var stackPane = new StackPane(bg, text);
        getContentRoot().getChildren().addAll(stackPane, restart);

        buildBackground();

    }

    @Override
    public void handle(ActionEvent event) {
        getAudioPlayer().stopAllMusic();
        getGameController().gotoMainMenu();

    }
}

