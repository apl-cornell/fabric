import generated.DTree;
import generated.DInt;

import java.util.Random;

public class Tree extends Example {
  public static void main(String[] args) throws Exception {
    new Tree(args).run();
  }
    
  public void run() throws Exception {
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
  
  public Tree(String[] args) throws Exception {
    super(args);
  }
  
}
