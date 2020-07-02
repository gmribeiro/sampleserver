package org.myfirstserver.server.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection {

    // Needed while the client side and custom messages are not implemented - we can only send 1 string
    private final String ENCODING = "UTF-8";

    // All final except for the boolean "alive" because we only want to set the attributes inside the constructor
    private final String KILL_COMMAND = "disconnect";
    private boolean alive;

    private final Socket socket;

    private final InputStream input;
    private final Scanner scanner;

    private final OutputStream output;
    private final PrintWriter writer;

    public Connection(Socket socket) throws IOException {

        this.socket = socket;
        this.alive = true;

        try {
            input = this.socket.getInputStream();
            scanner = new Scanner(input, ENCODING);

            output = this.socket.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(output, ENCODING), true);

        } catch (IOException e) {
            throw new IOException("[CONNECTION] ERROR - Initializing instance", e);
        }
    }

    public String killCommand() {
        return KILL_COMMAND;
    }

    public boolean alive() {
        return alive;
    }

    public void kill() throws IOException {

        this.alive = false;

        try {
            this.socket.close();

            this.scanner.close();
            this.input.close();
    
            this.writer.close();
            this.output.close();
        } catch (IOException e) {
            throw new IOException("[CONNECTION] ERROR - Initializing instance", e);
        }
    }

    public Scanner input() {
        return scanner;
    }

    public PrintWriter output() {
        return writer;
    }
}