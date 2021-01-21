package com.almasb.fxglgames.Battleship;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.event.EventType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.Iterator;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * One of the most important classes of this app and where most of the magic happens. Uses a mouse event handler attached
 * to each tile spawn to get if it was clicked. This event handler then calls the corresponding method of the attached ClickBehaviourComponent.
 */

public class TileFactory implements EntityFactory {

    /**
     * These arrays get filled with tiles on spawn, used to iterate over them on click to show change of state on screen
     * by changing the tile's color. Uses 4 array lists to make check logic inside getBoardState() less complicated
     */

    static ArrayList<Entity> player1shipTiles = new ArrayList<>();
    static ArrayList<Entity> player1hitTiles = new ArrayList<>();
    static ArrayList<Entity> player2shipTiles = new ArrayList<>();
    static ArrayList<Entity> player2hitTiles = new ArrayList<>();


    /**
     * Method to streamline update between frames, could be optimised
     */
    protected static void updateBoardState(){

        getBoardStateColors("ship",1);
        getBoardStateColors("hit",1);
        getBoardStateColors("ship",2);
        getBoardStateColors("hit",2);
    }

    /**
     * Important method that gets and updates the color of the tiles.
     *
     * the method takes in the board to check identifier "ship" or "hit", and the player ID 1 or 2 to signal which
     * player should be checked. The rest is handled via switches and booleans
     *
     * @param boardToCheck board identifier as string
     * @param playerID player ID as int
     */
    protected static void getBoardStateColors(@NotNull String boardToCheck, int playerID){

        Entity temp;

        switch (boardToCheck){
            case "ship" -> {
                switch (playerID) {
                    case 1 -> {
                        Iterator<Entity>  iterator = player1shipTiles.iterator();
                        for (int i = 0; i < player1shipTiles.size(); i++) {
                            temp = iterator.next();
                            int tempShipState = BattleshipMain.player1.getStateOfShipsCell(
                                    temp.getProperties().getValue("x"),
                                    temp.getProperties().getValue("y"));

                            switch (tempShipState) {
                                case 1 -> temp.getComponent(TileViewComponent.class).setColor(Color.LIGHTGRAY);
                                case 2 -> temp.getComponent(TileViewComponent.class).setColor(Color.RED);
                                case 3 -> temp.getComponent(TileViewComponent.class).setColor(Color.BLACK);
                            }
                        }
                    }
                    case 2 -> {
                        Iterator<Entity>  iterator = player2shipTiles.iterator();
                        for (int i = 0; i < player2shipTiles.size(); i++) {
                            temp = iterator.next();
                            int tempShipState = BattleshipMain.player2.getStateOfShipsCell(
                                    temp.getProperties().getValue("x"),
                                    temp.getProperties().getValue("y"));

                            switch (tempShipState) {
                                case 1 -> temp.getComponent(TileViewComponent.class).setColor(Color.LIGHTGRAY);
                                case 2 -> temp.getComponent(TileViewComponent.class).setColor(Color.RED);
                                case 3 -> temp.getComponent(TileViewComponent.class).setColor(Color.BLACK);
                            }
                        }
                    }
                }
            }
            case "hit" -> {
                switch (playerID) {
                    case 1 -> {
                        Iterator<Entity> iterator = player1hitTiles.iterator();
                        for (int i = 0; i < player1hitTiles.size(); i++) {
                            temp = iterator.next();
                            int hitState = BattleshipMain.player2.getStateOfHitCell(
                                    temp.getProperties().getValue("x"),
                                    temp.getProperties().getValue("y"));

                            switch (hitState) {
                                case 1 -> temp.getComponent(TileViewComponent.class).setColor(Color.BLACK);
                                case 2 -> temp.getComponent(TileViewComponent.class).setColor(Color.RED);
                            }
                        }
                    }
                    case 2 -> {
                        Iterator<Entity> iterator = player2hitTiles.iterator();
                        for (int i = 0; i < player2hitTiles.size(); i++) {
                            temp = iterator.next();
                            int hitState = BattleshipMain.player1.getStateOfHitCell(
                                    temp.getProperties().getValue("x"),
                                    temp.getProperties().getValue("y"));

                            switch (hitState) {
                                case 1 -> temp.getComponent(TileViewComponent.class).setColor(Color.BLACK);
                                case 2 -> temp.getComponent(TileViewComponent.class).setColor(Color.RED);
                            }
                        }
                    }
                }
            }
        }
    }

    //Todo update comment

    /**
     *Main part of the entity factory. Adds components on spawn and adds an event handler that handles click events
     *on the tiles. Event handler calls method in ClickBehaviourComponent depending on mouse button clicked.
     *
     * @param data spawn data
     * @return Entity
     */

    @Spawns("tile")
    public Entity newTile(SpawnData data) {

        TileViewComponent original = new TileViewComponent(Color.GRAY);

        var tile = entityBuilder(data)

                .bbox(new HitBox(BoundingShape.box(30,30)))
                .with(original)
                .with(new ClickBehaviourComponent())
                .build();

        tile.getViewComponent().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            play( "switch_005.wav");

            if (e.getButton() == MouseButton.SECONDARY){
                tile.getComponent(ClickBehaviourComponent.class).onSecondaryClick();
            }else if (e.getButton() == MouseButton.PRIMARY) {
                tile.getComponent(ClickBehaviourComponent.class).onPrimaryClick();
            }

        });
        return tile;
    }


}


