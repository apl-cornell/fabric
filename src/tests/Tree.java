import generated.DTree;
import generated.DInt;
import diaspora.client.Client;
import diaspora.client.Core;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Random;

public class Tree {
  public static void main(String[] args) throws Exception {
    if (args.length != 4) {
      System.out.println("Usage: " + Tree.class.getName()
          + " keystore passwd truststore passwd");
      System.exit(1);
    }

    KeyStore keyStore = KeyStore.getInstance("JKS");
    char[] passwd = args[1].toCharArray();
    keyStore.load(new FileInputStream(args[0]), passwd);

    KeyStore trustStore = KeyStore.getInstance("JKS");
    trustStore.load(new FileInputStream(args[2]), args[3].toCharArray());

    Client.initialize(keyStore, passwd, trustStore, 1000, 50, 2, 3);
    Client client = Client.getClient();
    
    Core node_core  = client.getCore(0);
    Core value_core = client.getCore(1);

    DTree tree = new DTree(value_core, node_core);
    Random random = new Random();

    for (int cycle = 0;; cycle++) {
      for (int i = 0; i < 50; i++) {
        DInt to_insert = new DInt(value_core);
        to_insert.set_value(random.nextInt());
        tree.insert_iterative(to_insert);
      }

      for (int i = 0; i < 50; i++) {
        DInt to_find = new DInt(node_core);
        to_find.set_value(random.nextInt());
        tree.lookup(to_find);
      }
      System.out.println("cycle " + cycle);
    }
  }
}
