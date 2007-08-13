package tree;

import java.util.Random;

public class Main {
  public static void main(String[] args) {
    Tree tree = null;
    atomic {
      tree = new Tree@0L(1L);
    }

    Random random = new Random@0L();

    int cycle = 0;
    while (true) {
      for (int i = 0; i < 50; i++) {
	atomic {
	  int toInsert = random.nextInt();
	  tree.insertIterative(toInsert);
	}
      }

      for (int i = 0; i < 50; i++) {
	atomic {
	  int toFind = random.nextInt();
	  tree.lookup(toFind);
	}
      }

      System.out.println("cycle " + (cycle++));
    }
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
