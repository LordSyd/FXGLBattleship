package com.almasb.fxglgames.Battleship;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * Class governs behavior on click - was handled by TileFactory before. Logic is basically the same
 */

public class ClickBehaviourComponent extends Component{




        
    boolean primary = true;

    /**
     * Called when secondary mouse button was clicked - only changes the boolean and then calls onPrimaryClick()
     */

    public void onSecondaryClick() {

        primary = false;
        onPrimaryClick();

    }

    private void waitAfterTurn(){
        Runnable run = BattleshipMain::showTurnMenu;
        FXGL.getGameTimer().runOnceAfter(run, Duration.millis(700));
    }

    /**
     * Same as it was in Tilefactory, just has a boolean instead of a check for a mouse button
     *
     * The first switch case ship controls the placement of the ships of player 1 and player 2.
     * After the ships are places the next switch case hit allows players to shoot on the board.
     */


    public void onPrimaryClick() {


        if (BattleshipMain.isPlayer1Turn()){
            ShipFactory.updateShipSpawns(BattleshipMain.player1);
        }else{
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
                                {
                                    ShipFactory.updateShipSpawns(BattleshipMain.player1);
                                    TileFactory.getBoardStateColors(tileType, 1);


                                    if (--BattleshipMain.player1ShipsToPlace == 0) {

                                        BattleshipMain.setPlayer1Turn(false);
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
                                )
                            {
                                ShipFactory.updateShipSpawns(BattleshipMain.player2);


                                TileFactory.getBoardStateColors(tileType,2);

                                if (--BattleshipMain.player2ShipsToPlace == 0) {

                                    BattleshipMain.setPlayer1Turn(true);

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
                            if (BattleshipMain.betweenTurnMenuActive =
                                    BattleshipMain.player2.shoot(
                                            entity.getProperties().getValue("x"),
                                            entity.getProperties().getValue("y"))) {
                                BattleshipMain.setPlayer1Turn(false);
                                waitAfterTurn();
                            }
                        }

                        case 2 -> {
                            TileFactory.getBoardStateColors(tileType, 2);

                            if (BattleshipMain.betweenTurnMenuActive =
                                    BattleshipMain.player1.shoot(
                                            entity.getProperties().getValue("x"),
                                            entity.getProperties().getValue("y"))) {
                                BattleshipMain.setPlayer1Turn(true);
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
