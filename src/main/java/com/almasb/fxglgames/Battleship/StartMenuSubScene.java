package com.almasb.fxglgames.Battleship;

import com.almasb.fxgl.entity.Entity;
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
 * Also not functional at the moment. Maybe can be get rid of completely, have to do more testing.
 */

public class StartMenuSubScene  extends SubScene implements EventHandler<ActionEvent> {

    public StartMenuSubScene() {
        var text = getUIFactoryService().newText("Placeholder " , Color.GOLD, 48);
        text.setTextAlignment(TextAlignment.LEFT);

        var bg = new Rectangle(300, 230, Color.color(0.3627451f, 0.3627451f, 0.5627451f, 0.55));
        bg.setArcWidth(50);
        bg.setArcHeight(50);
        bg.setStroke(Color.WHITE);
        bg.setStrokeWidth(10);

        var button = new FXGLButton("Test");
        button.setOnAction(this);
        button.setTranslateX(100);
        button.setTranslateY(400);

        var stackPane = new StackPane(bg, text);
        getContentRoot().getChildren().add(stackPane);
        getContentRoot().getChildren().add(button);
    }

    @Override
    public void handle(ActionEvent event) {

        System.out.println("pressed");

        BattleshipMain.closeStartMenu();
        TileFactory.getBoardStateColors("ship",1);
        TileFactory.getBoardStateColors("ship",2);
    }
}
