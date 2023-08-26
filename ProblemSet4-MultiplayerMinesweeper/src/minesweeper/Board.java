/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO: Specification
 * Given horizontal and vertical sizes, it represents a board of the game minesweeper. Minimum
 * dimensions are 5x5, and maximum are 30x16.
 *
 * The board is composed from Y lines, each with X squares. Every square can represent 4
 * states: untouched, flagged, dug and count. (Where count displays the number of neighboring bombs, and
 * dug is just count=0).
 *
 * The coordinates (0,0) start at the top-left corner. Increasing horizontally to the right and
 * vertically downwards.
 */
public class Board {


    //TODO abstraction f, rep invariant, safety exposure, thread.
    /**
     * Abstraction function:
     * Represents a board from the game Minesweeper.
     *
     * Rep invariant:
     * The original number of rows and columns must be maintained.
     * A square must have one of the 4 states.
     *
     * Safety from rep exposure:
     *
     *
     * Thread safety:
     *
     */
    public final int sizeX;
    public final int sizeY;
    public char[][] board;
    public ArrayList<Pair<Integer, Integer>> bombLocations;
    private int bombCount;


    public Board(int sizeX, int sizeY) {

        if(sizeX < 5 || sizeX > 30 || sizeY < 5 || sizeY > 16) {
            throw new RuntimeException("These dimensions are not allowed");
        } else {

            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.board = new char[sizeX][sizeY];

            for (char[] arr : board) {
                Arrays.fill(arr, '-');
            }
        }
    }
//TODO update docs and test to reflect new constructor
    //TODO new constructor which autoplaces mines. It must
    //accept a density parameter to auto place mines. apparently .20 is ideal
    public Board(char[][] originalBoard) {

        this.sizeX = originalBoard.length;
        this.sizeY = originalBoard[0].length;

        if(sizeX < 5 || sizeX > 30 || sizeY < 5 || sizeY > 16) {
            throw new RuntimeException("These dimensions are not allowed");
        } else {
            this.board = Arrays.copyOf(originalBoard,originalBoard.length);
        }
    }

    //TODO checkRep()
    //TODO toString(), equals(), and hashcode() methods.

    /**
     * Changes the state of the square at the given X,Y coordinates to the
     * new state indicated by the command parameter.
     *
     * This method is the public interface for the setFlagged, setDug, setCount
     * methods. setSquare makes all of this methods thread safe.
     *
     * //TODO this is for the sub methods
     * If the coordinates are not inside the grid, a RuntimeException is thrown.
     * If the state of square was not changed (i.e. the state had already that value) false is
     * returned. If the state was modified, it returns true.
     *
     * Returns bomb if the player activated a bomb.
     * Returns true if it was modified successfully and there is no further consequences
     * for the player.
     * Returns false if there was an error modifying the board.
     *
     * @param X coordinate on the X axis.
     * @param Y coordinate on the Y axis.
     * @param command A String equal to: flagged, dug or count.
     *
     * @return An enumeration type value corresponding with the actions to be taken by
     * the server.
     * @throws RuntimeException when the coordinates are not inside the grid.
     */
    //TODO setSquare is the one to check whether the coords are
    //outside the grid.
    //TODO the return value should actually be an enum value.
    public String setSquare(int X, int Y, String command) throws RuntimeException {return "null";}

    /**
     * Sets the status of the square indicated by the given coordinates
     * to be flagged. The method keeps track of active bombs using the
     * bombCount field.
     *
     * @param X coordinate on the X axis.
     * @param Y coordinate on the Y axis.
     * @return Whether the status was successfully changed.
     */
    private boolean setFlagged(int X, int Y) {

        char square = this.board[X][Y];

        if (square == '-') {
            this.board[X][Y] = 'F';

            if (bombLocations.contains(new Pair<>(X, Y))) {
                bombLocations.remove(new Pair<>(X, Y));
                bombCount--;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the status of the square indicated by the given coordinates
     * to be dug. Should the square contain a bomb, it will inform the server
     * in order to kick the player. Otherwise, it will change the status from
     * untouched to count, calculating the number of neighboring bombs.
     *
     * @param X coordinate on the X axis.
     * @param Y coordinate on the Y axis.
     * @return Whether a bomb was found at X, Y.
     */
    private boolean setDug(int X, int Y) {

        if(bombLocations.contains(new Pair<>(X, Y))) {
            return true;
        } else {
            int nearbyBombs = calculateCount(X, Y);
            setCount(X, Y, nearbyBombs);
            propagate(X, Y);
            return false;
        }
    }

    /**
     * Sets the count of the square indicated by the given coordinates
     * to be counter parameter.
     *
     * Returns true if the square contained a bomb. Otherwise, returns false.
     *
     * @param X coordinate on the X axis.
     * @param Y coordinate on the Y axis.
     * @param counter Integer to be displayed in the board.
     * @return True if the square contained a bomb. Otherwise, false.
     */
    private boolean setCount(int X, int Y, int counter) {

        char square = this.board[X][Y];
        ArrayList<Character> validChars = new ArrayList<>(List.of('0', '1', '3', '4', '5', '6', '7', '8'));

        if(!validChars.contains(square)) {
            return false;
        } else {
            this.board[X][Y] = (char) counter;
            return true;
        }
    }

    /**
     * Calculates the number of neighboring bombs in an 8 square grid
     * surroundings of the given coordinates.
     *
     * @param X coordinate on the X axis.
     * @param Y coordinate on the Y axis.
     * @return The count of neighboring bombs.
     */
    private int calculateCount(int X, int Y) {
        return 0;
    }

    /**
     * Returns the number of bombs which have not been flagged or dug.
     *
     * @return int, the number of bombs which have not been or dug.
     */
    public int getNumberActiveBombs() {
        return bombCount;
    }

    /**
     * Implements the propagation of the dug state to nearby squares of the X, Y coordinates,
     * if and only if those squares have state untouched and are not bombs.
     */
    public void propagate(int X, int Y) {}
    // TODO: Abstraction function, rep invariant, rep exposure, thread safety
    
    // TODO: Specify, test, and implement in problem 2
    
}
