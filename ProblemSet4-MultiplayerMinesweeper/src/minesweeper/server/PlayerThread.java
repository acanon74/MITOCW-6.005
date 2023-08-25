package minesweeper.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * PlayerThread represents a Thread which manages the server's connection with a
 * given client.
 */
public class PlayerThread implements Runnable {

    /**Player object that stores the client's information. */
    public Player obj;
    /**Socket object used by the server and client to communicate. */
    public Socket socket;

    /** Streams used to communicate with the client. */
    public BufferedReader in;
    public PrintWriter out;

    /** Protocol used when communicating with the server. */
    public Protocol protocol;

    public PlayerThread(Player obj, Protocol protocol, Socket socket) throws IOException {
        this.obj = obj;
        this.protocol = protocol;
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * promptName prompts for user identification at the beginning of the communication, and
     * stores the information in the Player object.
     * @return true if successful at getting the information, false otherwise.
     */
    public boolean promptName() {
        this.out.println("Type your username: ");
        try {
            obj.name = this.in.readLine();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    /**
     * The thread takes care of receiving messages from the client by reading the
     * input stream. Also, it writes to the output stream by once the request has
     * been processed.
     *
     * The message "QUIT" acts as a poison pill. Upon receiving "QUIT", the thread will close the
     * socket and remove all references to the Player object (playerCount and remove the obj from players).
     */
    public void run() {

        String line;

        while (true) {

        try {

            line = this.in.readLine();

            //The used the poison pill technique. Typing QUIT closes the connection and thread.
            if (line.equalsIgnoreCase("QUIT")) {
                MinesweeperServer.players.remove(obj);
                MinesweeperServer.playerCount--;
                socket.close();
                return;

            } else {
                this.out.println(protocol.handleRequest(line));
                this.out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        }
    }
}