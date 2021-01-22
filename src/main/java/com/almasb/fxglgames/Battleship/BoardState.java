package com.almasb.fxglgames.Battleship;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *This class saves the state of the board by using two dimensional arrays. It also handles writing into and retrieving
 * from cells with setter and getter, as well as checking for neighbours of cells (it is not allowed to place in neighbor cells) and checking if a given point is within the gameboard
 */

public class BoardState {
    private int[][] State = new int[10][10];

    public BoardState() {}

    public void setStateOfCell(int x, int y, int newState) {
        State[x][y] = newState;
    }

    public int getStateOfCell(int x, int y) {
        return State[x][y];
    }

    public int[] getNeighbors(int x, int y){
        Point2D[] points = new Point2D[] {
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };

        List<Integer> neighbors = new ArrayList<>();

        for (Point2D p : points) {
            if (isValidPoint(p)) {
                neighbors.add(getStateOfCell((int)p.getX(), (int)p.getY()));
            }
        }

        int[] ret = new int[neighbors.size()];
        Iterator<Integer> iterator = neighbors.iterator();

        for (int i =0; i < ret.length; i++){
            ret[i] = iterator.next();
        }

        return ret;

    }

    public boolean isValidPoint(Point2D point) { return isValidPoint(point.getX(), point.getY()); }

    public boolean isValidPoint(double x, double y) { return x >= 0 && x < 10 && y >= 0 && y < 10; }

}
