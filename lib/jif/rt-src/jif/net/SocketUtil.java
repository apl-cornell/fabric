package jif.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import jif.lang.Label;

/**
 * This class provides some useful utilities for Jif programs using Java's
 * sockets.
 */
public class SocketUtil {
    private SocketUtil() { }
    private static boolean DEBUG = false;

    /**
     * Listen to the ServerSocket, and pass any new connections to the 
     * SocketAcceptor, in a new thread.
     * @param ss
     * @param a
     */
    public static void acceptConnections(Label lbl, ServerSocket ss, final SocketAcceptor a) {
        if (ss == null || a == null) return;
        while (true) {
            try {
                if (DEBUG) System.out.println("Listening on port " + ss.getLocalPort());
                Socket s = ss.accept();  
                if (DEBUG) System.out.println("Got socket: " + s.getPort());
                new Thread(new SocketAcceptorRunner(a,s)).start();   
            }
            catch (Exception e) {
                // recover silently
                if (DEBUG) e.printStackTrace();
            }
        }
        
    }
    private static class SocketAcceptorRunner implements Runnable {
        private final SocketAcceptor a;
        private final Socket s;
        SocketAcceptorRunner(SocketAcceptor a, Socket s) {
            this.a = a;
            this.s = s;
        }
        public void run() {
            try {
                if (DEBUG) System.out.println("Calling accept for socket #" + s.getPort());
                a.accept(s.getInputStream(), s.getOutputStream());
                if (DEBUG) System.out.println("Finished accept for socket #" + s.getPort());
            }
            catch (Exception e) {
                // just fail silently
                if (DEBUG) e.printStackTrace();
            }
            finally {
                try {
                    s.close();
                }
                catch (IOException e) {
                    if (DEBUG) e.printStackTrace();
                }                
            }
        }
        
    }
}
