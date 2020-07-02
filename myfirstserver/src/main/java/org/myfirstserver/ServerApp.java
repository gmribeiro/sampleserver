package org.myfirstserver;

import org.myfirstserver.server.Server;


public class ServerApp 
{
    public static void main( String[] args )
    {
        // Implement interactive menu (step by step) which allows user to define the port. if an empty message is provided, use the DEFAULT_PORT
        // DEFAULT_PORT should be a private static final int in the server class - might be a final variable in this class too or later a configuration
        // add thread pool input if this implementation goes that far
        Server server = new Server();
        server.start();
    }
}
