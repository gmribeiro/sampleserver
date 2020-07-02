package org.myfirstserver.server.connection;

import java.io.IOException;
import java.util.concurrent.Callable;

// Consider changing to callable, since runnables can't throw exceptions
public class Handler implements  Callable<Void> {

    private final Connection c;

    public Handler(Connection c) {
        this.c = c;
    }

    @Override
    public Void call() {
        while (c.alive() && c.input().hasNextLine()) {

            String inputString = c.input().nextLine();
            c.output().println("echo: " + inputString);

            if (inputString.equalsIgnoreCase(c.killCommand())) {
                try {
                    c.kill();
                } catch (IOException e) {
                    System.err.println("[HANDLER] ERROR - it wasn't possible to kill the connection");
                }
            }
        }

        return null;
    }
}