package com.almasb.fxglgames.Battleship;

import javafx.scene.Parent;

/**
 * This class instantiates ships that has been placed as well saving their orientation. Used to save ship coordinates
 * and orientation for ship spawning.
 * It also defines the health (lives) of the player, it is set by the type and length of a ship.
 */

public class Ship extends Parent {
    private final int type;
    private final boolean vertical;
    private final double x;
    private final double y;

    private int health;

    public Ship(int type, boolean vertical,  double x, double y) {
        this.type = type;
        this.vertical = vertical;
        health = type;
        this.x = x;
        this.y = y;

    }


    public int getType() {
        return type;
    }

    public boolean isVertical() {
        return vertical;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * methods not used at the moment, for future proofing
     * @return
     */

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void hit() {
        health--;
    }

    public boolean isAlive() {
        return health > 0;
    }
}
