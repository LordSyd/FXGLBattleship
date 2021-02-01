package ac.at.fhcampuswien.Battleship;

import java.util.ArrayList;

/**
 * This class represents the players. It has two arrays for saving the ship positions and where the player has shot,
 * if it hit anything and so on. It also has health that gets subtracted from on hit() and it checks if ships can be
 * placed. Also it has a method to return if the player isDead().
 */


public class Player {


    private final BoardState ships = new BoardState();
    private final BoardState shots = new BoardState();

    public ArrayList<Ship> shipInstances = new ArrayList<>();


    private int health = 5+4+3+2+1; //numbers of tiles per ship type

    public Player() {}

    public ArrayList<Ship> getShipInstances() {
        return shipInstances;
    }

    public int getStateOfShipsCell(int x, int y) {
        return ships.getStateOfCell(x, y);
    }

    public int getStateOfHitCell(int x, int y) {
        return shots.getStateOfCell(x, y);
    }

    /**
     * shoots at this players ships, called by other player. Returns false if turn is not over, true if nothing was hit
     * -> turn is over
     *
     *
     * For future reference:
     *
     * ship Board:
     * 1: Ship is there
     * 2: Ship was hit
     * 3: miss by enemy
     *
     * shots Board:
     * 0: Nothing is there
     * 1: Miss
     * 2: Hit
     *
     * @param x int
     * @param y int
     * @return boolean
     */

    public boolean shoot(int x, int y){
        if (shots.getStateOfCell(x,y) != 0){
            return false;
        }else{
            if (ships.getStateOfCell(x,y) == 1){
                hit();
                shots.setStateOfCell(x,y, 2);
                ships.setStateOfCell(x,y, 2);

                return false;
            }else{
                shots.setStateOfCell(x,y, 1);
                ships.setStateOfCell(x,y, 3);
            }

            return true;
        }

    }

    /**
     * Method to try placing ships, takes a ship instance that provides length and orientation of ship and coordinates
     *
     * returns true if placement was successful
     *
     * @param ship Ship
     * @param x int
     * @param y int
     * @return boolean
     */

    public boolean placeShip(Ship ship, int x, int y) {
        if (canPlaceShip(ship, x, y)) {
            this.shipInstances.add(ship);
            int length = ship.getType();

            if (ship.isVertical()) {
                for (int i = y; i < y + length; i++) {
                    ships.setStateOfCell(x, i, 1);
                }
            }
            else {
                for (int i = x; i < x + length; i++) {
                    ships.setStateOfCell(i, y, 1);
                }
            }
            return true;
        }
        return false;

    }

    public boolean isDead(){return health == 0;}

    public void hit() {
        health--;
    }

    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.getType();

        if (ship.isVertical()) {
            for (int i = y; i < y + length; i++) {
                if (!ships.isValidPoint(x, i))
                    return false;

                //Board.Cell cell = getCell(x, i);
                if (ships.getStateOfCell(x, i) != 0)
                    return false;

                for (int neighbor : ships.getNeighbors(x, i)) {
                    if (!ships.isValidPoint(x, i))
                        return false;

                    if (neighbor != 0)
                        return false;
                }
            }
        } else {
            for (int i = x; i < x + length; i++) {
                if (!ships.isValidPoint(i, y))
                    return false;

                if (ships.getStateOfCell(i, y) != 0)
                    return false;

                for (int neighbor : ships.getNeighbors(i, y)) {
                    if (!ships.isValidPoint(i, y))
                        return false;

                    if (neighbor != 0)
                        return false;
                }
            }
        }
        return true;
    }
}



