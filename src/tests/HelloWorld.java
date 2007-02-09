import diaspora.common.*;
import diaspora.client.*;
import java.io.FileInputStream;
import java.util.*;
import java.security.KeyStore;
import javax.security.auth.x500.X500Principal;

public class HelloWorld {
  public static void main(String[] args) throws Throwable {
    if (args.length != 4) {
      System.out.println("Usage: " + HelloWorld.class.getName()
          + " keystore passwd truststore passwd");
      System.exit(1);
    }

    KeyStore keyStore = KeyStore.getInstance("JKS");
    char[] passwd = args[1].toCharArray();
    keyStore.load(new FileInputStream(args[0]), passwd);

    KeyStore trustStore = KeyStore.getInstance("JKS");
    trustStore.load(new FileInputStream(args[2]), args[3].toCharArray());

    Client client = new Client(keyStore, passwd, trustStore, 1000, 50, 2, 3);
    Random rand = new Random();

    // Attempt to read OID 0.
    System.out.println();
    System.out.println("Attempting to read OID 0");
    try {
      System.out.println("Got " + client.readOID(0L, 0L));
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to write OID 0.
    int curInt = rand.nextInt();
    System.out.println();
    System.out.println("Attempting to write " + curInt + " to OID 0");
    try {
      if (client.writeOID(0L, 0L, new TestObj(curInt)))
        System.out.println("Succeeded");
      else System.out.println("Failed");
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to read OID 0.
    System.out.println();
    System.out.println("Attempting to read OID 0");
    try {
      System.out.println("Got " + client.readOID(0L, 0L));
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }
    // Attempt to insert at OID 0.
    curInt = rand.nextInt();
    System.out.println();
    System.out.println("Attempting to insert " + curInt + " at OID 0");
    try {
      if (client.insertOID(0L, 0L, new TestObj(curInt)))
        System.out.println("Succeeded");
      else System.out.println("Failed");
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Attempt to read OID 0.
    System.out.println();
    System.out.println("Attempting to read OID 0");
    try {
      System.out.println("Got " + client.readOID(0L, 0L));
    } catch (AccessError e) {
      System.out.println("Access error: " + e.getMessage());
    }

    // Get some OIDs.
    System.out.println();
    System.out.println("Obtaining new object numbers...");
    long[] oNums = client.getNewONums(0);
    System.out.println("Got " + oNums.length + " leases:");
    for (int i = 0; i < oNums.length; i++)
      System.out.println(oNums[i]);
  }

  static class TestObj extends DObject {
    int val;

    public TestObj() {
      this(new Random().nextInt());
    }

    public TestObj(int val) {
      super(new DLMPolicy(new X500Principal(
          "cn=client0,ou=Diaspora,o=Cornell University,l=Ithaca,st=NY,c=US")));
      this.val = val;
    }

    public String toString() {
      return new Long(val).toString();
    }
  }
}
