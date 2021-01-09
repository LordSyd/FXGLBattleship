package com.almasb.fxglgames.drop;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * This class is not functional. It lacks a way to pass the mouse button clicked to it, so this logic is handled
 * inside the TileFactory class at the moment
 */

public class ClickBehaviourComponent extends Component{


        
        boolean primary;



    public void onClick() {
        System.out.println("clicked:  "+ entity.getProperties().getValue("x") + entity.getProperties().getValue("y"));


        int playerId = entity.getProperties().getValue("Player");
        String tileType = entity.getProperties().getValue("boardType");

        System.out.println("clicked:  "+ entity.getProperties().getValue("x") + entity.getProperties().getValue("y"));


        switch (tileType) {

            case "ship" -> {
                if (!BattleshipMain.gameRunning) {
                    switch (playerId) {
                        case 1 -> {
                            {

                                if (BattleshipMain.player1.placeShip(
                                        new Ship(BattleshipMain.player1ShipsToPlace,
                                                primary),
                                        entity.getProperties().getValue("x"), entity.getProperties().getValue("y")))
                                {
                                    System.out.println("Ship");



                                    TileFactory.getBoardState(tileType, 1);




                                    if (--BattleshipMain.player1ShipsToPlace == 0) {

                                        BattleshipMain.player1Turn = false;


                                        //todo exchange test for menu with real player change submenu
                                        BattleshipMain.showTurnMenu();
                                    }

                                }
                            }
                        }
                        case 2 -> {
                            if (BattleshipMain.player2.placeShip(
                                    new Ship(BattleshipMain.player2ShipsToPlace,
                                            primary),
                                    entity.getProperties().getValue("x"), entity.getProperties().getValue("y")))
                            {
                                System.out.println("Ship");



                                TileFactory.getBoardState(tileType,2);




                                if (--BattleshipMain.player2ShipsToPlace == 0) {

                                    BattleshipMain.player1Turn = true;


                                    //todo exchange test for menu with real player change submenu
                                    BattleshipMain.showTurnMenu();
                                }

                            }

                        }
                    }
                }else{
                    return;
                }
            }
            case "hit" -> {
                if (BattleshipMain.gameRunning) {

                    switch (playerId) {
                        case 1 -> {
                            TileFactory.updateBoardState();
                            if (BattleshipMain.betweenTurnMenuActive =
                                    BattleshipMain.player2.shoot(entity.getProperties().getValue("x"), entity.getProperties().getValue("y"))) {
                                BattleshipMain.player1Turn = false;
                            }

                        }


                        case 2 -> {
                            TileFactory.getBoardState(tileType, 2);

                            if (BattleshipMain.betweenTurnMenuActive =
                                    BattleshipMain.player1.shoot(entity.getProperties().getValue("x"), entity.getProperties().getValue("y"))) {
                                BattleshipMain.player1Turn = true;
                            }
                        }
                    }

                }
            }


        }

        //todo find fix for texture loading bug (backlog)
        //spawn("ship", tile.getX(), tile.getY());

















    }



}
