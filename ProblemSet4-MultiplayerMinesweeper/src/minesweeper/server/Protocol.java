package minesweeper.server;

import java.net.ServerSocket;

import minesweeper.Board;
/**
 * Protocol implements the communication protocol used by the server and clients.
 * <p>
 * The description of the protocol can be found in extensive detail at:
 * <a href="https://ocw.mit.edu/ans7870/6/6.005/s16/psets/ps4/#problem_1_set_up_the_server_to_deal_with_multiple_clients:~:text=and%20exit%20telnet.-,Protocol%20and%20specification,-You%20must%20implement">...</a>
 */
public class Protocol {

    /**
     * Abstraction function:
     * Represents the communication protocol used on the MineSweeper game.
     * <p>
     * Representation invariant:
     * X, Y must be the size of the board used in the game.
     * socket must be the socket used by the server.
     * <p>
     * Safety from Rep exposure:
     * X, Y and socket are final and private.
     *
     * Thread safety:
     * Its fields are immutable, so they do not pose a risk of interleaving.
     * Whenever the handleRequest method access an object's fields or methods, it does so
     * using synchronized methods.
     * There is only one protocol object created. Because it is a static field of the
     * server, and it is passed by reference to each Thread.
     * For a ThreadPlayer to mutate the board, it must first acquire its lock.
     */

    final private int X;
    final private int Y;
    final private ServerSocket socket;
    final private Board board;

    public Protocol (ServerSocket socket, Board board, int X, int Y) {
        this.socket = socket;
        this.board = board;
        this.X = X;
        this.Y = Y;
    }

    public String handleRequest(String input) {

        synchronized (board) {

            String regex = "(look)|(help)|(bye)|"
                    + "(dig -?\\d+ -?\\d+)|(flag -?\\d+ -?\\d+)|(deflag -?\\d+ -?\\d+)";
            if ( ! input.matches(regex)) {
                // invalid input
                // TODO Problem 5
            }
            String[] tokens = input.split(" ");
            if (tokens[0].equals("look")) {
                return board.bombLocations.toString() + board.toString();
                // TODO Problem 5
            } else if (tokens[0].equals("help")) {
                // 'help' request
                // TODO Problem 5
            } else if (tokens[0].equals("bye")) {
                return "Bye! Thank you for playing\n";
                // TODO Problem 5
            } else if (input.equals("test")) {
                return socket.getLocalSocketAddress().toString();
            } else if (input.equals("hello")) {
                return "Welcome to Minesweeper. Board: " + X + " columns by " + Y + " rows. Players: " + MinesweeperServer.playerCount +
                " including you. Type 'help' for help.\n";
            } else if (input.equals("players")) {
                String table = "\r\n";
                for (Player player : MinesweeperServer.players) {
                    table = table + "||> " + player.name + " : " + player.getScore() + "\n";
                }
                return table;
            }

            else {
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);
                if (tokens[0].equals("dig")) {

                    String outcome = board.setSquare(x, y, "dug", true);
    //didnt find bomb, +1 score
                    if(outcome.equals("bomb")) {
                        return handleRequest("look") + "**> You Exploded!\n";
                    } else if(outcome.equals("true")) {
                        return handleRequest("look") + "Move received at (" + x + ", " + y + ")\n";
                    } else {
                        return handleRequest("look") + "That move is not possible now, be faster!\n";
                    }

                    // 'dig x y' request
                    // TODO Problem 5
                } else if (tokens[0].equals("flag")) {
                    String outcome = board.setSquare(x, y, "flagged", false);
                    if(outcome.equals("true")) {
                        return handleRequest("look") + "Move received at (" + x + ", " + y + ")\n";
                    } else {
                        return handleRequest("look") + "That move is not possible now, be faster!\n";
                    }
                    // 'flag x y' request
                    // TODO Problem 5
                } else if (tokens[0].equals("deflag")) {
                    // 'deflag x y' request
                    // TODO Problem 5
                }
            }
            // TODO: Should never get here, make sure to return in each of the cases above
            throw new UnsupportedOperationException();
        }
    }
}
