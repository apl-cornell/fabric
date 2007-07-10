import generated.DTree;
import generated.DInt;

import java.util.Random;

import diaspora.client.Client;
import diaspora.client.Core;
import diaspora.client.TransactionManager;

public class Tree {
  public static void main(String[] args) throws Exception {
    Client client = Client.getClient();
    Core   core0  = client.getCore(0);
    Core   core1  = client.getCore(1);
    TransactionManager tx = TransactionManager.INSTANCE;
    
    tx.startTransaction();
    DTree tree = new DTree(core1, core0);
    tx.commitTransaction();
    Random random = new Random();
    
    for (int cycle = 0;; cycle++) {
      for (int i = 0; i < 50; i++) {
        tx.startTransaction();
        DInt to_insert = new DInt(core1);
        to_insert.set_value(random.nextInt());
        tree.insert_iterative(to_insert);
        tx.commitTransaction();
      }

      for (int i = 0; i < 50; i++) {
        tx.startTransaction();
        DInt to_find = new DInt(core0);
        to_find.set_value(random.nextInt());
        tree.lookup(to_find);
        tx.commitTransaction();
      }
      System.out.println("cycle " + cycle);
    }
  }
 
}
