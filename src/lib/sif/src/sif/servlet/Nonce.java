package sif.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.ServletException;

/**
 * @author andru
 * 
 * Support for generating random nonces. A Nonce objects is
 * a generator for nonces.
 **/
public class Nonce {
    private static final int NONCE_LENGTH = 8; // number of bytes
    private final Servlet servlet;
    private final Random rand;

    /** A nonce object encapsulates a nonce. 
     * @throws ServletException
     * @throws IOException*/
    public Nonce(Servlet srv) throws ServletException {
        servlet = srv;
        rand = newSecureRandom();
    }

    /** Generate a pseudo-random sequence of bytes that
     * can be used as a nonce. */
    public Name generate() {
        byte[] bytes = new byte[NONCE_LENGTH];
        rand.nextBytes(bytes);
        return new Name(bytes);

    }

    private SecureRandom newSecureRandom() throws ServletException {
        SecureRandom toReturn = null;
        try {
            toReturn = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new ServletException("Cannot construct MD5 digests.");
        }
        
        // seed the random number generator
        ByteArrayOutputStream seed = new ByteArrayOutputStream();

        // add the host id
        String hostid = servlet.getPrivateHostID();

        if (hostid == null) {
            System.out.println("Error: cannot find private host ID in configuration.");
        }
        byte[] hostidbytes = hostid.getBytes();
        seed.write(hostidbytes, 0, hostidbytes.length);
        
        // add the current time
        long millis = System.currentTimeMillis();
        byte[] b = new byte[] {(byte)(millis & 0xFF000000 >> 24),
                (byte)(millis & 0xFF0000 >> 16),
                (byte)(millis & 0xFF00 >> 8),
                (byte)(millis & 0xFF)};     

        seed.write(b, 0, b.length);
        
        toReturn.setSeed(seed.toByteArray());

        return toReturn;
    }
}
