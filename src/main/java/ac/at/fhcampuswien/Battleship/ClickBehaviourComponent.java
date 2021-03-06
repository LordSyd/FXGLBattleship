package ac.at.fhcampuswien.Battleship;


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

    /**
     * wait method to lock out clicking after turn finished and to make transition less jarring
     */

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
     *"Main logic" governing the game boards. Has different switch cases and boolean checks to get correct
     *behaviour depending on game state. also has a boolean to lock out clicking on boards when this should be
     *prevented.
     *
     *Sold my soul to the devil to debug this, don't ask any questions - you are better of not knowing
     */


    public void onPrimaryClick() {

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
                                            entity.getProperties().getValue("x"),
                                            entity.getProperties().getValue("y")))
                                        {
                                            getGameWorld().getEntitiesByType(ShipFactory.Type.SHIP).forEach(Entity::removeFromWorld);
                                        ShipFactory.updateShipSpawns(BattleshipMain.player1);
                                        TileFactory.getBoardStateColors(tileType, 1);

                                        if (--BattleshipMain.player1ShipsToPlace == 0) {
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
                                        entity.getProperties().getValue("x"),
                                        entity.getProperties().getValue("y"))
                                ) {
                                    getGameWorld().getEntitiesByType(ShipFactory.Type.SHIP).forEach(Entity::removeFromWorld);
                                    ShipFactory.updateShipSpawns(BattleshipMain.player2);
                                    TileFactory.getBoardStateColors(tileType, 2);
                                    if (--BattleshipMain.player2ShipsToPlace == 0) {
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
                                if (BattleshipMain.player2.shoot(
                                                entity.getProperties().getValue("x"),
                                                entity.getProperties().getValue("y")))
                                {
                                    waitAfterTurn();

                                }
                            }
                            case 2 -> {

                                if (BattleshipMain.player1.shoot(
                                                entity.getProperties().getValue("x"),
                                                entity.getProperties().getValue("y")))
                                {
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
