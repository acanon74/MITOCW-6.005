package minesweeper.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * PlayerThread represents a Thread which manages the server's connection with a
 * given client, and its Player object.
 */
public class PlayerThread implements Runnable {

    /**
     * Abstraction function:
     * Takes communication streams and a Player object to represent a Client.
     * <p>
     * Representation invariant:
     * obj and socket must be immutable.
     * communication streams with the client, in and out, must be immutable.
     * protocol must be the protocol established by the server and client.
     * <p>
     * Safety from Rep exposure:
     * All fields are private and immutable.
     *
     * Thread safety:
     * Its fields are immutable and private, so they do not pose a risk of interleaving.
     * getPlayer() gets the lock of the Player object, so reading is thread safe.
     */

    /**Player object that stores the client's information. */
    private final Player obj;
    /**Socket object used by the server and client to communicate. */
    private final Socket socket;
    /** Streams used to communicate with the client. */
    private final BufferedReader in;
    private final PrintWriter out;
    /** Protocol used when communicating with the server. */
    private final Protocol protocol;

    public PlayerThread(Protocol protocol, Socket socket) throws IOException {
        this.protocol = protocol;
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.obj = createPlayer();
    }

    /**
     * createPlayer prompts for user identification at the beginning of the communication, and
     * stores the information in the Player object.
     * @return A Player object if communication was successful. Otherwise, null;
     */
    private Player createPlayer() {
        this.out.println("Type your username: ");
        try {
            String name = this.in.readLine();
            return new Player(name);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }
    public Player getPlayer() {
        synchronized (obj) {
            return this.obj;
        }
    }

    /**
     * The thread takes care of receiving messages from the client by reading the
     * input stream. Also, it writes to the output stream once the request has
     * been processed by the given protocol.
     *
     * The message "QUIT" acts as a poison pill. Upon receiving "QUIT", the thread will close the
     * socket and remove all references to the Player object (playerCount and remove the obj from players in
     * MineSweeperServer).
     */
    public void run() {

        this.out.println(protocol.handleRequest("hello"));

        String line;

        while (true) {

        try {

            line = this.in.readLine();

            //We use the poison pill technique. Typing QUIT closes the connection and thread.
            if (line.equalsIgnoreCase("QUIT")) {

                this.out.println(protocol.handleRequest("bye"));

                synchronized (MinesweeperServer.players) {
                    MinesweeperServer.players.remove(obj);
                    MinesweeperServer.playerCount--;
                }

                socket.close();
                return;

            } else {
                String serverReponse = protocol.handleRequest(line);
                this.out.println(serverReponse);
                this.out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        }
    }
}