/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Preconditions:
 * Every square must have one of 4 status: untouched, dug, flagged or count.
 * Board must maintain the dimensions it was initialized with.
 * <p>
 * Special cases:
 * Initialization with 0, 1, small and big dimensions.
 * <p>
 * Test strategy:
 * We test the creating of Board objects with different lengths. These include
 * 0, 1, small and MAX dimensions (we also include testing for even and odd -dimensioned grids).
 * Also, we test some combinations of dimensions such as 0x1, 0xMAX, 0xsmall, and so on.
 * <p>
 * We initially test a 5x5 grid, and then perform some test in a scaled-up version with
 * 10x10 squares. This should show that the Object is able to execute operations
 * in multiple sized boards as well as boards with even and odd dimension lengths.
 * <p>
 * Breakdown of every test used:
 * <p>
 * boardConstructorTest()
 * Test the correct initialization of boards with dimensions including
 * beginner 5x5 up to 30x16 boards. Including even and odd dimensions.
 * <p>
 * setSquare()
 * In 5x5 and 10x10 grid we test the following:
 * <p>
 * In terms of coordinates:
 * - Setting a square outside the grid. Should throw an Exception. Not include in test yet.
 * - Setting a square inside the grid.
 * - Setting a square which is part of a boundary.
 * <p>
 * In terms of operations:
 * <p>
 * The following operations will be tested on squares which both,
 * contain and not contain a bomb.
 * <p>
 * setDug:
 * If the state is untouched:
 * - If it doesn't contain a bomb, it changes the state to count=0 (or dug).
 * Otherwise, report the bomb's presence to the server and remove said bomb from the board,
 * bomb counter, and bomb locations.
 * If the state is flagged:
 * - The player should not be able to dig a flagged square, so this must
 * be reported to the server. The board object is not mutated.
 * <p>
 * setFlagged:
 * If the state is untouched:
 * - If it contains a bomb, we reduce the bomb counter and give a point to the player,
 * Otherwise, we do not decrease the bomb counter or reward points.
 * <p>
 * setCount:
 * If the state is count:
 * We update the counter's integer in order to display the new
 * information.
 * <p>
 * getNumberActiveBombs()
 * We initialize boards with different bomb quantities and locations.
 * And we perform some setSquare operations after the setup.
 * The method should report the correct number of active bombs at creating time and
 * after some operation have been performed.
 */
public class BoardTest {
    
    // TODO: Testing strategy
    public Board smallEmptyBoard() {
        return new Board(5,5);
    }

    public ArrayList<Bomb> drawingToBoard(char[][] originalBoard) {

        ArrayList<Bomb> bombs = new ArrayList<>();

        for(int i = 0; i < originalBoard.length; i++) {
            char[] arr = originalBoard[i];

            for(int j = 0; j < arr.length; j++) {
                char c = arr[j];
                if(c == 'B') {
                    bombs.add(new Bomb(i, j));
                }
            }
        }
        return bombs;
    }

    public Board smallBoard() {
        //with .2 density, total of 5 bombs.
        char[][] DRAWING = {
                {'B', '-', '-', 'B', '-'},
                {'-', '-', '-', 'B', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', 'B', '-', '-', 'B'},
                {'-', '-', '-', '-', '-'}
        };

        Board finalBoard = new Board(5, 5);
        finalBoard.bombLocations = drawingToBoard(DRAWING);
        finalBoard.activeBombCount = finalBoard.bombLocations.size();
        return finalBoard;
    }

    public Board bigBoard() {
        //with .2 density, total of 20 bombs.
        char[][] DRAWING = {//3,2
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', 'B'},
                {'-', '-', 'B', '-', '-', '-', 'B', '-', '-', 'B'},
                {'-', '-', '-', '-', '-', '-', 'B', '-', '-', 'B'},
                {'-', '-', '-', '-', 'B', '-', 'B', '-', '-', '-'},
                {'B', '-', '-', '-', '-', '-', '-', 'B', '-', '-'},
                {'B', '-', '-', 'B', 'B', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', 'B', 'B', '-', '-', 'B', '-', 'B', 'B'},
                {'-', '-', '-', '-', 'B', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', 'B', '-', '-', '-', '-', '-'}
        };

        Board finalBoard = new Board(10, 10);
        finalBoard.bombLocations = drawingToBoard(DRAWING);
        finalBoard.activeBombCount = finalBoard.bombLocations.size();
        return finalBoard;
    }

    public Board propagateBoard() {
        //with .2 density, total of 20 bombs.
        char[][] DRAWING = {
                {'-', '-', '-', '-', '-', '-', '-', '-', 'B'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', 'B', '-', 'B', '-'},
                {'-', 'B', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', 'B', 'B', '-', '-', '-', '-', '-', '-'},
                {'-', '-', 'B', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', 'B', 'B', '-', '-', '-', '-'},
                {'-', 'B', '-', '-', '-', '-', '-', '-', '-'}
        };

        Board finalBoard = new Board(9, 9);
        finalBoard.bombLocations = drawingToBoard(DRAWING);
        finalBoard.activeBombCount = finalBoard.bombLocations.size();
        return finalBoard;
    }


    public boolean sameValue(char[][] board, char expected) {

        for(char[] arr : board) {
            for(char c : arr) {
                if(c != expected) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void boardConstructorTest() {

        Board board = smallEmptyBoard();
        assertEquals(5, board.sizeX);
        assertEquals(5, board.sizeY);

        assertTrue(sameValue(board.board, '-'));

        board = new Board(30,16);
        assertEquals(30, board.sizeX);
        assertEquals(16, board.sizeY);


        assertTrue(sameValue(board.board, '-'));


        //TODO constructor from reading a board and
        //TODO constructor with auto bomb placing activated
    }

    @Test
    public void setSquareTest() {

        //**Test based on square coordinates:
        Board board = smallBoard();

        //Test for coordinates in both boundaries.
        assertEquals("bomb", board.setSquare(0,0, "dug"));

        Board expectedBoard = smallBoard();
        expectedBoard.board[0][0] = ' ';
        assertEquals(expectedBoard.board, board.board);

        //Test for coordinates inside the grid.
        board = smallBoard();

        assertEquals("true", board.setSquare(1,2, "dug"));

        expectedBoard = smallBoard();
        expectedBoard.board[1][2] = '2';
        assertEquals(expectedBoard.board, board.board);

        //**Test based on operations:

        //Test on small grid:
        //Test of dug on untouched square, with bomb.
        board = smallBoard();
        expectedBoard = smallBoard();

        assertEquals("bomb", board.setSquare(0, 3, "dug"));
        expectedBoard.board[0][3] = '1';
        assertEquals(expectedBoard.board, board.board);
        //should have removed it
        expectedBoard.bombLocations.remove(new Bomb(0, 3));
        assertEquals(expectedBoard.bombLocations, board.bombLocations);
        assertEquals(4, board.getNumberActiveBombs());

        //Test of flagged on untouched square, without bomb.
        board = smallBoard();
        expectedBoard = smallBoard();

        assertEquals("true", board.setSquare(1, 0, "flagged"));
        expectedBoard.board[1][0] = 'F';
        assertEquals(expectedBoard.board, board.board);

        //Test of dug on flagged square, without bomb.
        board = smallBoard();
        board.setSquare(2, 1, "flagged");

        expectedBoard = smallBoard();
        expectedBoard.board[2][1] = 'F';

        assertEquals("false", board.setSquare(2, 1, "dug"));
        expectedBoard.board[2][1] = 'F';
        assertEquals(expectedBoard.board, board.board);
        assertEquals(expectedBoard.bombLocations, board.bombLocations);

        //Test on big grid:

        //Test of dug on untouched, without bomb.
        board = bigBoard();
        expectedBoard = bigBoard();

        assertEquals("true", board.setSquare(4, 9, "dug"));
        expectedBoard.board[4][9] = ' ';
        assertEquals(expectedBoard.board, board.board);

        //Test of flagged on untouched, with bomb.
        board = bigBoard();
        expectedBoard = bigBoard();


        assertEquals("true", board.setSquare(1, 6, "flagged"));
        expectedBoard.board[1][6] = 'F';
        assertEquals(expectedBoard.board, board.board);

        int index = board.bombLocations.indexOf(new Bomb(1, 6));

        assertEquals(true, board.bombLocations.get(index).flagged);
        assertEquals(19, board.getNumberActiveBombs());

        //Test of dug on flagged, with bomb.
        board = bigBoard();
        board.setSquare(4, 0, "flagged");

        expectedBoard = bigBoard();
        expectedBoard.board[4][0] = 'F';

        assertEquals("false", board.setSquare(4, 0, "dug"));
        assertEquals(expectedBoard.board, board.board);
    }

    @Test
    public void getNumberActiveBombsTest() {

        Board board = smallEmptyBoard();

        assertEquals(0, board.getNumberActiveBombs());

        board = bigBoard();

        assertEquals(20, board.getNumberActiveBombs());

        board = smallBoard();

        assertEquals(5, board.getNumberActiveBombs());

        board.setSquare(0, 0, "dug");
        assertEquals(4, board.getNumberActiveBombs());

        board.setSquare(0, 3, "flagged");
        assertEquals(3, board.getNumberActiveBombs());
    }


    @Test
    public void propagateTest() {

        Board board = propagateBoard();

        //1,3
        board.propagate(1, 3);

        char[][] Expected = {
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', '1', '-'},
                {' ', ' ', ' ', ' ', '1', '1', '2', '2', '-'},
                {'1', '1', '1', ' ', '1', '-', '-', '-', '-'},
                {'-', '-', '1', ' ', '1', '1', '2', '1', '1'},
                {'-', '-', '3', '1', ' ', ' ', ' ', ' ', ' '},
                {'-', '-', '-', '2', ' ', ' ', ' ', ' ', ' '},
                {'-', '-', '-', '4', '2', '1', ' ', ' ', ' '},
                {'-', '-', '-', '-', '-', '1', ' ', ' ', ' '},
                {'-', '-', '-', '-', '-', '1', ' ', ' ', ' '}
        };

        assertEquals(Expected, board.board);
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

}
