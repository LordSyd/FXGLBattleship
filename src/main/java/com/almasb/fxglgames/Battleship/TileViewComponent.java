package com.almasb.fxglgames.Battleship;


import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This governs the look of each tile. The color gets set on construction, by passing Color.color identifier on to the
 * constructor
 */

public class TileViewComponent extends ChildViewComponent {

    private Color tileColor;
    Rectangle bg = new Rectangle(30, 30);


    public TileViewComponent(Color color) {
        this.tileColor = color;

        bg.setFill(tileColor);

        Rectangle bg2 = new Rectangle(31, 31, Color.rgb(20, 150, 250, 0.30));
        bg2.setStrokeWidth(2);
        bg2.setStroke(Color.BLACK);

        getViewRoot().getChildren().addAll(new StackPane(bg, bg2));
    }

    public void setColor(Color newColor){
        this.tileColor = newColor;
        bg.setFill(tileColor);
    }


}