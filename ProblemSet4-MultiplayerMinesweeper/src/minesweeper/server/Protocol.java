package minesweeper.server;

import java.net.ServerSocket;


/**
 * Protocol implements the communication protocol used by the server and clients.
 * <p>
 * The description of the protocol can be found in extensive detail at:
 * <a href="https://ocw.mit.edu/ans7870/6/6.005/s16/psets/ps4/#problem_1_set_up_the_server_to_deal_with_multiple_clients:~:text=and%20exit%20telnet.-,Protocol%20and%20specification,-You%20must%20implement">...</a>
 */
public class Protocol {

    public int X;
    public int Y;
    ServerSocket socket;

    public Protocol (ServerSocket socket, int X, int Y) {
        this.socket = socket;
        this.X = X;
        this.Y = Y;
    }

    public String handleRequest(String input) {
        String regex = "(look)|(help)|(bye)|"
                + "(dig -?\\d+ -?\\d+)|(flag -?\\d+ -?\\d+)|(deflag -?\\d+ -?\\d+)";
        if ( ! input.matches(regex)) {
            // invalid input
            // TODO Problem 5
        }
        String[] tokens = input.split(" ");
        if (tokens[0].equals("look")) {
            // 'look' request
            // TODO Problem 5
        } else if (tokens[0].equals("help")) {
            // 'help' request
            // TODO Problem 5
        } else if (tokens[0].equals("bye")) {
            // 'bye' request
            // TODO Problem 5
        } else if (input.equals("test")) {
            return socket.getLocalSocketAddress().toString();
        } else if (input.equals("hello")) {
            return "Welcome to Minesweeper. Board: " + X + " columns by " + Y + " rows. Players: " + MinesweeperServer.playerCount +
            " including you. Type 'help' for help.";
        } else if (input.equals("players")) {

            String table = "\n\r";
            for (Player player : MinesweeperServer.players) {
                table = table + player.name + " : " + player.score + "\n\r";
            }
            return table;
        }

        else {
            int x = Integer.parseInt(tokens[1]);
            int y = Integer.parseInt(tokens[2]);
            if (tokens[0].equals("dig")) {
                // 'dig x y' request
                // TODO Problem 5
            } else if (tokens[0].equals("flag")) {
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
