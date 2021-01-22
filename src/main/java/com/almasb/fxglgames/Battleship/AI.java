package com.almasb.fxglgames.Battleship;

import com.almasb.fxgl.dsl.FXGL;
import javafx.util.Duration;

import java.util.Random;

import static com.almasb.fxglgames.Battleship.BattleshipMain.setAITurn;
import static com.almasb.fxglgames.Battleship.BattleshipMain.setPlayer1Turn;


public class AI {


    public AI() {
    }

    /**
     * wait-methods, for giving the player visual feedback
     */
    protected void moveAndWait(){
        Runnable move = AI::easyAIMove;
        FXGL.getGameTimer().runOnceAfter(move, Duration.millis(300));
    }

    protected void placeAndWait(){
        Runnable move = AI::AIPlaceShip;
        FXGL.getGameTimer().runOnceAfter(move, Duration.millis(500));

    }

    /**
     * method for ai ship placement, tries to place ship by generating orientation and location at random
     */

     private static void AIPlaceShip(){
        boolean validPlacement;

        Random randomGenerator = new Random();

        int tryPlacementX;
        int tryPlacementY;
        boolean tryOrientation;
        Ship shipToPlace;

        do {
             tryPlacementX = randomGenerator.nextInt(10);
             tryPlacementY = randomGenerator.nextInt(10);
             tryOrientation = randomGenerator.nextBoolean();
             shipToPlace = new Ship(
                     BattleshipMain.player2ShipsToPlace,
                     tryOrientation,
                     tryPlacementX,
                     tryPlacementY);

             validPlacement = BattleshipMain.player2.placeShip(shipToPlace, tryPlacementX, tryPlacementY);
             if (validPlacement){
                 BattleshipMain.player2ShipsToPlace--;
             }

        }while(BattleshipMain.player2ShipsToPlace != 0);
         ClickBehaviourComponent.canClick = true;
    }

    /**
     * logic governing ai shots, takes random guess
     */


    private static void easyAIMove() {

        Random randomGenerator = new Random();

        int shotX;
        int shotY;

        boolean isDone = false;

        do{


            shotX = randomGenerator.nextInt(10);
            shotY = randomGenerator.nextInt(10);

            isDone = BattleshipMain.player1.shoot(shotX, shotY);
            System.out.println("ai shot success");
            if (!BattleshipMain.player1.isDead()){

                TileFactory.updateBoardState();}
        }while(!isDone);
        ClickBehaviourComponent.canClick = true;
    }


    /**
     * placeholder methods, not working
     */
/*
        public void placeShipAI ()
        {
            int SchiffPosiX = randomGenerator.nextInt(10) + 1;
            int SchiffPosiY = randomGenerator.nextInt(10) + 1;

            int success = 0;

            boolean placed = BattleshipMain.player2.placeShip(Ship ship, SchiffPosiY, SchiffPosiX);

            while (success < 6) {

                if (placed) {
                    SchiffPosiX = randomGenerator.nextInt(10) + 1;
                    SchiffPosiY = randomGenerator.nextInt(10) + 1;
                    success++;
                }
                else {
                    SchiffPosiX = randomGenerator.nextInt(10) + 1;
                    SchiffPosiY = randomGenerator.nextInt(10) + 1;
                }

            }






        }
*/











/*
//Computer schießt um das Schiff herum
//Vorsicht nicht über das Spielfeld hinausschießen
//Wenn Computer Schiff trifft x-Achse abfragen (links rechts), //wenn nicht y-Achse abfragen bis Schüsse ins Leere gehen
//Zusatzklausel, so lange schießen bis Leben aus ist?
//Counterbalance zur x-Achsen abfragen, Computer weiß Schiffsleben
//Schiffe als Objekte mit Leben true/false??


    public void MediumAI (AnzahlSpielfelder)
    {

    }



    //Computer weiß wo Schiffe stehen und schießt nach gezieltem //versenken von Schiffen random im wasser umher
    public void HardAI (AnzahlSpielfelder)
    {

        int SchussX = get.ShipX[1];
        int SchussY = get.ShipY[1];

        while (hit(SchussY, SchussX) == true)
              {
                int SchussX = get.ShipX[i];
                int SchussY = get.ShipY[i];

                if (getHealthShip(i) == 0) {
                break;
                }
              }


          player.shoot; /Benachrichtigung über Zug des Spielers



    }



 */
}





/*

Zugtimer??

andere Java Battleship Computer AI:

https://github.com/GrahamBlanshard/AI-Battleship/blob/master/prograham/battleship/player/AIPlayer.java

*/



