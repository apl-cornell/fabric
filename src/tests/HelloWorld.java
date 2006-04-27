import diaspora.common.*;
import diaspora.client.*;
import java.util.*;

public class HelloWorld {
  public static void main(String[] args) throws Throwable {
    Client client = new Client(50, 2, 3);

    // Assume we have a fresh core.

    // Attempt to read OID 0; should get null.
    System.out.println();
    System.out.println("Attempting to read OID 0; should get null");
    try {
      System.out.println("Got " + client.readOID(0L));
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to write OID 0; should fail.
    System.out.println();
    System.out.println("Attempting to write OID 0; should fail");
    try {
      if (client.writeOID(0L, new Public()))
	System.out.println("Succeeded");
      else
	System.out.println("Failed");
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to insert at OID 0; should succeed.
    System.out.println();
    System.out.println("Attempting to insert at OID 0; should succeed");
    try {
      if (client.insertOID(0L, new Public()))
	System.out.println("Succeeded");
      else
	System.out.println("Failed");
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to read OID 0; should succeed.
    System.out.println();
    System.out.println("Attempting to read OID 0; should succeed");
    try {
      System.out.println("Got " + client.readOID(0L));
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to write that OID; should succeed.
    System.out.println();
    System.out.println("Attempting to write OID 0; should succeed");
    try {
      if (client.writeOID(0L, new Writable()))
	System.out.println("Succeeded");
      else
	System.out.println("Failed");
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to read that OID; should fail with access error.
    System.out.println();
    System.out.println("Attempting to read OID 0; should fail with access error");
    try {
      System.out.println("Got " + client.readOID(0L));
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to write that OID; should succeed.
    System.out.println();
    System.out.println("Attempting to write OID 0; should succeed");
    try {
      if (client.writeOID(0L, new Readable()))
	System.out.println("Succeeded");
      else
	System.out.println("Failed");
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to read that OID; should succeed.
    System.out.println();
    System.out.println("Attempting to read OID 0; should succeed");
    try {
      System.out.println("Got " + client.readOID(0L));
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to write that OID; should fail with access error.
    System.out.println();
    System.out.println("Attempting to write OID 0; should fail with access error");
    try {
      if (client.writeOID(0L, new Public()))
	System.out.println("Succeeded");
      else
	System.out.println("Failed");
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Get some OIDs.
    System.out.println();
    System.out.println("Obtaining new OIDs...");
    long[] oids = client.getNewOIDs(0);
    System.out.println("Got " + oids.length + " leases:");
    for (int i = 0; i < oids.length; i++) System.out.println(oids[i]);
  }

  static class Readable extends DObject {
    public boolean canRead(Principal client) { return true; }
    public boolean canWrite(Principal client) { return false; }
  }

  static class Writable extends DObject {
    public boolean canRead(Principal client) { return false; }
    public boolean canWrite(Principal client) { return true; }
  }

  static class Public extends DObject {
    public boolean canRead(Principal client) { return true; }
    public boolean canWrite(Principal client) { return true; }
  }
}

