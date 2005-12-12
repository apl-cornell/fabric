import diaspora.common.*;
import diaspora.client.*;
import java.net.*;
import java.util.*;

public class HelloWorld {
  public static void main(String[] args) throws Throwable {
    Core core =
      new Core(Collections.singletonList(new InetSocketAddress("localhost", 
	      3372)));
    Client client = new Client(core, 30, 3);

    // Attempt to read an (hopefully-invalid) OID just to see what happens.
    System.out.println();
    System.out.println("Attempting to read OID 0");
    try {
      System.out.println("Got " + client.readOID(0L));
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to write an (hopefully-invalid) OID just to see what happens.
    System.out.println();
    System.out.println("Attempting to write OID 0");
    try {
      if (client.writeOID(0L, new DString("moo")))
	System.out.println("Succeeded");
      else
	System.out.println("Failed");
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Get some OIDs.
    System.out.println();
    System.out.println("Obtaining new OIDs...");
    long[] oids = client.getNewOIDs();
    System.out.println("Got " + oids.length + " leases:");
    for (int i = 0; i < oids.length; i++) System.out.println(oids[i]);
  }

  static class DString extends DObject {
    public String value;

    public DString(String s) {
      this.value = s;
    }
  }
}

