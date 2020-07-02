package org.sample.server.myfirstserver;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.Socket;
import org.myfirstserver.server.connection.Connection;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void newConnection() {

        Socket testSocket = new Socket();

        try {
            Connection conn = new Connection(testSocket);
            conn.kill();


            assertTrue( !conn.alive() );
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
