package com.almasb.fxglgames.Battleship;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * Class governs behavior on click - was handled by TileFactory before. Logic is basically the same
 */

public class ClickBehaviourComponent extends Component{




        
    boolean primary = true;
    public static boolean canClick = true;


    private void waitAfterTurn(){

        canClick = false;

        if (!BattleshipMain.isAIActive()) {

        Runnable showTurnMenu = BattleshipMain::showTurnMenu;
        FXGL.getGameTimer().runOnceAfter(showTurnMenu, Duration.millis(700));
        }else{
            Runnable startAITurn = BattleshipMain::startAITurn;
            FXGL.getGameTimer().runOnceAfter(startAITurn, Duration.millis(200));
        }

    }

    /**
     * Called when secondary mouse button was clicked - only changes the boolean and then calls onPrimaryClick()
     */

    public void onSecondaryClick() {
        if(canClick){
            primary = false;
            onPrimaryClick();
        }

    }



    /**
     * Same as it was in Tilefactory, just has a boolean instead of a check for a mouse button
     *
     * The first switch case ship controls the placement of the ships of player 1 and player 2.
     * After the ships are places the next switch case hit allows players to shoot on the board.
     */


    public void onPrimaryClick() {
        System.out.println(canClick);
        if (canClick) {

            if (BattleshipMain.isPlayer1Turn()) {
                getGameWorld().getEntitiesByType(ShipFactory.Type.SHIP).forEach(Entity::removeFromWorld);
                ShipFactory.updateShipSpawns(BattleshipMain.player1);
            } else {
                getGameWorld().getEntitiesByType(ShipFactory.Type.SHIP).forEach(Entity::removeFromWorld);
                ShipFactory.updateShipSpawns(BattleshipMain.player2);
            }

            int playerId = entity.getProperties().getValue("Player");
            String tileType = entity.getProperties().getValue("boardType");

            switch (tileType) {
                case "ship" -> {
                    if (!BattleshipMain.isGameRunning()) {
                        switch (playerId) {
                            case 1 -> {
                                {
                                    if (BattleshipMain.player1.placeShip(
                                            new Ship(BattleshipMain.player1ShipsToPlace,
                                                    primary,
                                                    entity.getX(),
                                                    entity.getY()),
                                            entity.getProperties().getValue("x"), entity.getProperties().getValue("y")))
                                        getGameWorld().getEntitiesByType(ShipFactory.Type.SHIP).forEach(Entity::removeFromWorld);{
                                        ShipFactory.updateShipSpawns(BattleshipMain.player1);
                                        TileFactory.getBoardStateColors(tileType, 1);


                                        if (--BattleshipMain.player1ShipsToPlace == 0) {

                                            canClick = false;


                                            waitAfterTurn();
                                        }
                                    }
                                }
                            }
                            case 2 -> {
                                if (BattleshipMain.player2.placeShip(
                                        new Ship(
                                                BattleshipMain.player2ShipsToPlace,
                                                primary,
                                                entity.getX(),
                                                entity.getY()
                                        ),
                                        entity.getProperties().getValue("x"), entity.getProperties().getValue("y"))
                                ) {
                                    getGameWorld().getEntitiesByType(ShipFactory.Type.SHIP).forEach(Entity::removeFromWorld);
                                    ShipFactory.updateShipSpawns(BattleshipMain.player2);


                                    TileFactory.getBoardStateColors(tileType, 2);

                                    if (--BattleshipMain.player2ShipsToPlace == 0) {
                                        canClick = false;
                                        waitAfterTurn();
                                    }
                                }
                            }
                        }
                    }
                }
                case "hit" -> {
                    if (BattleshipMain.isGameRunning()) {

                        switch (playerId) {
                            case 1 -> {
                                TileFactory.updateBoardState();
                                if (BattleshipMain.player2.shoot(
                                                entity.getProperties().getValue("x"),
                                                entity.getProperties().getValue("y")))
                                {
                                    canClick = false;
                                    waitAfterTurn();
                                    System.out.println(canClick);
                                }
                            }

                            case 2 -> {
                                TileFactory.getBoardStateColors(tileType, 2);

                                if (BattleshipMain.player1.shoot(
                                                entity.getProperties().getValue("x"),
                                                entity.getProperties().getValue("y")))
                                {
                                    canClick = false;
                                    waitAfterTurn();
                                }
                            }
                        }
                    }
                }
            }
            TileFactory.updateBoardState();


        }
    }
}
