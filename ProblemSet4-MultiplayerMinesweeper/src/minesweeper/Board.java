/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Random;

/**
 * Given horizontal and vertical sizes, it represents a board of the game minesweeper. Minimum
 * and maximum dimensions are 5x5 and 30x16, respectively.
 * <p>
 * The board is composed of X lines, each with Y squares. Every square can represent 4
 * states: untouched, flagged, dug and count. (Where count displays the number of neighboring bombs, and
 * dug is just count=0).
 * <p>
 * The coordinates (0,0) start in the top-left corner. X coordinates increase downwards. Y coordinates
 * increase rightwards.
 */
public class Board {

    /**
     * Abstraction function:
     * Represents a board from the game Minesweeper.
     * <p>
     * Rep invariant:
     * The original number of rows and columns must be maintained.
     * A square must have any of the 4 valid states.
     * activeBombCount and the board must account for the same number of ACTIVE bombs (not flagged or detonated).
     * <p>
     * Safety from rep exposure:
     * sizeX, sizeY are final.
     * The reference to board, activeBombCount and bombLocations are final.
     * Mutations to the board are exclusively performed from setSquare, which calls the required methods
     * as necessary. All other methods are private.
     * getNumberActiveBombs() is the getter for activeBombCount, which doesn't expose how the number
     * is calculated.
     * <p>
     * Thread safety:
     * sizeX, sizeY are immutable. So they do not pose a risk of interleaving.
     * The reference to board is final. board can only be mutated under synchronized methods, which is thread safe.
     * The reference to bombLocations is final, and mutations are perform exclusively under synchronized methods.
     * Reads from and mutations to activeBombCount are performed exclusively under synchronized methods.
     * methods toString(), equals(), hashCode() are synchronized.
     * getNumberActiveBombs() is synchronized and is a getter to activeBombCounter.
     * All other methods are private and called only by setSquare, which is a synchronized method.
     */
    public final int sizeX;
    public final int sizeY;
    public final char[][] board;
    public final ArrayList<Bomb> bombLocations = new ArrayList<>();
    private int activeBombCount;
    private final ArrayList<Character> counterCharacters = new ArrayList<>(List.of('-', '1', '2', '3', '4', '5', '6', '7', '8'));
    private final ArrayList<Character> allValidStates = new ArrayList<>(List.of(' ', 'F', '-', '1', '2', '3', '4', '5', '6', '7', '8'));
    public Board(int sizeX, int sizeY, boolean populate) {
        this(sizeX, sizeY, populate, null, 0);
    }
    public Board(int sizeX, int sizeY, boolean populate, final ArrayList<Bomb> bombLocations, int activeBombCount) {

        if(sizeX < 5 || sizeX > 30 || sizeY < 5 || sizeY > 16) {
            throw new RuntimeException("These dimensions are not allowed");
        } else {

            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.board = new char[sizeX][sizeY];


            if(bombLocations != null) {

                this.bombLocations.addAll(bombLocations);
                System.out.println(Arrays.toString(bombLocations.toArray()));
                System.out.println(Arrays.toString((this.bombLocations.toArray())));
                this.activeBombCount = activeBombCount;

            } else {

                if(populate) {
                    this.populate(0.2);
                }
            }

            for (final char[] arr : board) {
                Arrays.fill(arr, '-');
            }

        }
        checkRep();
    }
    private void checkRep() {

        //dimensions of the grid are guarantee by the immutability of an array.
        assert ((board.length == sizeX) && (board[0].length == sizeY));

        //Every char corresponds to a valid state.
        for(char[] arr : board) {
            for(char c : arr) {
                assert allValidStates.contains(c);
            }
        }
    }
    @Override
    public synchronized String toString() {
        String result;

        result = "Size: " + sizeX + "x" + sizeY + "Bombs: " + activeBombCount + "\n";

        for(char[] arr : board) {
            result = result + Arrays.toString(arr) + "\n";
        }
        return result;
    }

    /**
     * Two Board objects must be equal if their boards are equal and
     * their bombs are located in the same positions. Amount of bombs must
     * be equal too.
     *
     * @param obj Object of type Board to compare this to.
     * @return true if the boards and bombs qualities (location and quantity) are the same. Otherwise, false.
     */
    @Override
    public synchronized boolean equals(Object obj){

        if(obj == null){
            return false;
        }

        if(obj.getClass() != this.getClass()){
            return false;
        }

        final Board other = (Board) obj;

        return ((this.board == other.board) && (this.bombLocations == other.bombLocations));
    }

    /**
     * Two objects which are considered equal must calculate equal hashes.
     * Since we define equality of Board objects as having equal boards and bomb's qualities (location and quantity),
     * we hash using board and bombLocations fields only.
     *
     * @return int representing a hash for this object.
     */
    @Override
    public synchronized int hashCode() {
        return Arrays.deepHashCode(this.board) + this.bombLocations.hashCode();
    }

    /**
     * Changes the state of the square at the given X,Y coordinates to the
     * new state indicated by the command parameter.
     * <p>
     * This method is the public interface for the setFlagged, setDug, setCount
     * methods. setSquare makes all of this methods thread safe.
     * <p>
     *
     * The square specified by X, Y must be INSIDE the grid OR be a boundary square. Otherwise, a
     * RuntimeException is thrown.
     * <p>
     * Returns bomb if the player activated a bomb.
     * Returns "true" if the board was modified successfully and there is no further consequences
     * for the player.
     * Returns "false" if there was an error modifying the board. (i.e. the state had already that value or its state is "flagged")
     *
     * @param X coordinate on the X axis.
     * @param Y coordinate on the Y axis.
     * @param command A String equal to: flagged, dug or count.
     *
     * @return "bomb", "true" or "false", as explained above.
     * @throws RuntimeException when the coordinates are not inside the grid or part of a boundary.
     */

    public synchronized String setSquare(int X, int Y, String command, boolean propagate) throws RuntimeException {
        //TODO This use case is better fitted for an Enum datatype.
        if(X < 0 || Y < 0 || X >= sizeX || Y >= sizeY) {
            throw new RuntimeException("Out of bounds square");
        }

        if(command.equals("dug")) {
            return setDug(X, Y, propagate);

        } else if(command.equals("flagged")) {

            if (setFlagged(X, Y)) {
                return "true";

            } else {
                return "false";
            }
        }
        return "false";
    }

    /**
     * Sets the status of the square indicated by the given coordinates
     * to be flagged. The method marks the bombs as tracked using the
     * bombLocations array, and keeps track of remaining, active bombs
     * using the activeBombCount field.
     * <p>
     * The square specified by X, Y must be INSIDE the grid OR be a boundary square.
     *
     * @param X coordinate on the X axis.
     * @param Y coordinate on the Y axis.
     * @return Whether the status was successfully changed.
     */
    private boolean setFlagged(int X, int Y) {

        char square = this.board[X][Y];

        if (square == '-') {
            this.board[X][Y] = 'F';

            int indexBomb = bombLocations.indexOf(new Bomb(X, Y));

            if (indexBomb != -1) {
                bombLocations.get(indexBomb).setFlag(true);
                activeBombCount--;
            }
            checkRep();
            return true;
        } else {
            checkRep();
            return false;
        }
    }

    /**
     * Sets the status of the square indicated by the given coordinates
     * to be dug. Should the square contain a bomb, it returns a String "bomb" and removes
     * the bomb from bombLocations and bombCount.
     * Otherwise, it will change the status from untouched to count, calling calculateCount and propagate.
     * <p>
     * The square specified by X, Y must be INSIDE the grid OR be a boundary square.
     *
     * @param X coordinate on the X axis.
     * @param Y coordinate on the Y axis.
     * @return "bomb" if a bomb was found at X, Y. "true" if we successfully changed the status to count, but a bomb was not found.
     * "false" if the board was not modified, and a bomb was not found.
     */
    private String setDug(int X, int Y, boolean propagate) {
        //TODO This use case is better fitted for an Enum datatype.
        if(this.board[X][Y] == '-') {

            int nNearbyBombs = calculateCount(X, Y);
            if(propagate) propagate(X, Y);
            setCount(X, Y, nNearbyBombs);

            if (bombLocations.contains(new Bomb(X, Y))) {
                bombLocations.remove(new Bomb(X, Y));
                activeBombCount--;

                checkRep();
                return "bomb";
            } else {
                checkRep();
                return "true";
            }
        } else {
            checkRep();
            return "false";
        }
    }

    /**
     * Sets the count of the square indicated by the given coordinates
     * to be the counter parameter.
     * <p>
     * The square specified by X, Y must be INSIDE the grid OR be a boundary square.
     *
     * @param X coordinate on the X axis.
     * @param Y coordinate on the Y axis.
     * @param counter Integer to be displayed in the board.
     * @return true if it successfully replaced the character. Otherwise, false.
     */
    private boolean setCount(int X, int Y, int counter) {

        char square = this.board[X][Y];

        if(counterCharacters.contains(square)) {

            if(counter == 0) {
                this.board[X][Y] = ' ';
            } else {
                this.board[X][Y] = (char) (counter + '0');
            }
            checkRep();
            return true;

        } else {
            checkRep();
            return false;
        }
    }

    /**
     * Calculates the number of neighboring bombs in the 8-square grid
     * surround the given coordinate.
     * <p>
     * The square specified by X, Y must be INSIDE the grid OR be a boundary square.
     *
     * @param X coordinate on the X axis.
     * @param Y coordinate on the Y axis.
     * @return The count of neighboring bombs.
     */
    private int calculateCount(int X, int Y) {

        int count = 0;

            if (bombLocations.contains(new Bomb(X-1, Y))) count++;
            if (bombLocations.contains(new Bomb(X-1, Y+1))) count++;
            if (bombLocations.contains(new Bomb(X, Y+1))) count++;
            if (bombLocations.contains(new Bomb(X+1, Y+1))) count++;
            if (bombLocations.contains(new Bomb(X+1, Y))) count++;
            if (bombLocations.contains(new Bomb(X+1, Y-1))) count++;
            if (bombLocations.contains(new Bomb(X, Y-1))) count++;
            if (bombLocations.contains(new Bomb(X-1, Y-1))) count++;

        return count;
    }

    /**
     * Returns the number of bombs which have not been flagged or dug.
     *
     * @return int, the number of bombs which have not been or dug.
     */
    public synchronized int getNumberActiveBombs() {
        checkRep();
        return activeBombCount;
    }

    /**
     * If the square x,y has no neighbor squares with bombs, then for each of x,y’s untouched
     * neighbor squares, change said square to dug and repeat this step (not the entire DIG procedure)
     * recursively for said neighbor square unless said neighbor square was already dug before said change.
     *
     * @param X x coordinate.
     * @param Y y coordinate.
     * @return It propagates the dug state, modifying the board array.
     */
    private synchronized void propagate(int X, int Y) {

        if(X >= board.length || Y >= board.length || X < 0 || Y < 0) {
            return;
        }

        if(counterCharacters.contains(this.board[X][Y])) {

            if (calculateCount(X, Y) == 0) {
                this.board[X][Y] = ' ';

                    propagate(X - 1, Y);
                    propagate(X - 1, Y + 1);
                    propagate(X, Y + 1);
                    propagate(X + 1, Y + 1);
                    propagate(X + 1, Y);
                    propagate(X + 1, Y - 1);
                    propagate(X, Y - 1);
                    propagate(X - 1, Y - 1);

            } else {
                setCount(X, Y, calculateCount(X, Y));
                checkRep();
                return;
            }
        } else {
            checkRep();
            return;
        }
    }
    /**
     * Given a desired density of bombs, the method computes the maximum number of bombs and
     * places them in the grid. This method mutates the grid and the activeBombCount fields.
     * @param density desired density for the bombs.
     */
    private synchronized void populate(double density) {

        int nBombs = (int) ((sizeX*sizeY)*density);

        Random random = new Random();
        for (int count = 0; count < nBombs; count++) {
            int row = random.nextInt(sizeX);
            int col = random.nextInt(sizeY);

            Bomb newBomb = new Bomb(row, col);

            if(!bombLocations.contains(newBomb)) {
                bombLocations.add(newBomb);
            } else {
                count--;
            }
        }
        this.activeBombCount = nBombs;
    }
}
