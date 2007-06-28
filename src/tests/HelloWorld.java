import diaspora.common.*;
import diaspora.client.*;
import java.io.FileInputStream;
import java.util.*;
import java.security.KeyStore;
import javax.security.auth.x500.X500Principal;
import generated.DInt;

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

    core = Core.get_core( 0 );
    
    for (int onum = 0; onum < 2; onum++) {
      Policy policy =
          onum == 0 ? new DLMPolicy(new X500Principal(
              "cn=client0,ou=Diaspora,o=Cornell University,l=Ithaca,st=NY,"
                  + "c=US")) : new DLMPolicy();

      // Attempt to read.
      System.out.println();
      System.out.println("Attempting to read OID " + onum);
      try {
        System.out.println("Got " + client.readOID(0L, onum));
      } catch (AccessError e) {
        System.out.println("Access error: " + e.getMessage());
      }

      // Attempt to write.
      int curInt = rand.nextInt();
      System.out.println();
      System.out.println("Attempting to write " + curInt + " to OID " + onum);
      try {
        if (client.writeOID(0L, onum, makeTestObj(policy, curInt)))
          System.out.println("Succeeded");
        else System.out.println("Failed");
      } catch (AccessError e) {
        System.out.println("Access error: " + e.getMessage());
      }

      // Attempt to read.
      System.out.println();
      System.out.println("Attempting to read OID " + onum);
      try {
        System.out.println("Got " + client.readOID(0L, onum));
      } catch (AccessError e) {
        System.out.println("Access error: " + e.getMessage());
      }
      // Attempt to insert.
      curInt = rand.nextInt();
      System.out.println();
      System.out.println("Attempting to insert " + curInt + " at OID " + onum);
      try {
        if (client.insertOID(0L, onum, makeTestObj(policy, curInt)))
          System.out.println("Succeeded");
        else System.out.println("Failed");
      } catch (AccessError e) {
        System.out.println("Access error: " + e.getMessage());
      }

      // Attempt to read.
      System.out.println();
      System.out.println("Attempting to read OID " + onum);
      try {
        System.out.println("Got " + client.readOID(0L, onum));
      } catch (AccessError e) {
        System.out.println("Access error: " + e.getMessage());
      }
    }

    // Get some OIDs.
    System.out.println();
    System.out.println("Obtaining new object numbers...");
    long[] oNums = client.getNewONums(0);
    System.out.println("Got " + oNums.length + " leases:");
    for (int i = 0; i < oNums.length; i++)
      System.out.println(oNums[i]);
  }
  
  private static Core core; 
  
  static DObject makeTestObj(Policy policy, int val) {
    DInt result = new DInt( core, policy );
    result.set_value( val );
    return result;
  }
}
