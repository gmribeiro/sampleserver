package org.myfirstserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.myfirstserver.server.connection.Connection;
import org.myfirstserver.server.connection.Handler;

public class Server {
    // Might be a map when the client side is implemented or an attribute called
    // "client-id" may be added to the connection class and initilized in the
    // constructor
    // Should be seen as a shared resource amongst workers and its get method
    // should be a synchronized one - as well as the method to put messages in the
    // queue (Handler will do this)

    // handlers write to the message queue and another type of thread - slaves- will use the receiver
    // outputStream present (inside connection) to send the message (or trigger the sending)
    // Server must know and manage slaves. The handler mplements callable instead of Runnable, to return the message into the queue

    private final int port = 9123;

    // CachedThreadPools create threads as they are required and re-use
    // older asleep threads (instead of just deleting their reference) - check other options
    private ExecutorService serverExecutor;

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            this.serverExecutor = Executors.newCachedThreadPool();

            System.out.println("[SERVER] Hello! Server instance is running");

            while (true) {
                acceptNewConnection(serverSocket);
            }
        } catch (Exception e) {
            System.err.println("[SERVER] ERROR - It wasn't possible to start the server - port may be already in use");
            e.printStackTrace();
        } finally {
            // Shuts the server exexutor before killing the server
            serverExecutor.shutdown();
        }
    }

    private void acceptNewConnection(ServerSocket serverSocket) {
        try {
            Socket connSocket = serverSocket.accept();
            Handler handler = new Handler(new Connection(connSocket));

            // No future is required since we're using a runnable - change to callable later
            FutureTask<Void> futureTask = new FutureTask<Void>(handler);

            serverExecutor.execute(futureTask);

        } catch (IOException e) {
            System.err.println("[SERVER] ERROR - failed to accept new connection");
        }
    }
}