package arrays;

import fabric.client.Core;

public class ArrayTest {
  public int[]       ints;
  public ArrayTest[] obs;

  public ArrayTest() {
    ints = new int[3];
    for (int i = 0; i < ints.length; i++)
      ints[i] = i;

    obs = new ArrayTest[1];
    obs[0] = this;
  }

  public static void main(String[] args) {
    runTests();
    atomic { runTests(); }
  }

  public static void runTests() {
    ArrayTest a1 = null;
    ArrayTest a2 = null;
    atomic {
      Core core = fabric.client.Client.getClient().getCore(0L);
      a1      = new ArrayTest@core();
      a2      = new ArrayTest@core();
      a2.ints = new int[]@core {5,4,3,2,1};

      int[][] x = new int[][]@core {{1,2}, {3}};
    }

    atomic {
      int[]   temp = a1.ints;
      a1.ints = a2.ints;
      a2.ints = temp;
    }

    atomic {
      ArrayTest[] temp = a1.obs;
      a1.obs = a2.obs;
      a2.obs = temp;
    }

    atomic {
      a1.obs[0] = a1;
    }

    atomic {
      a1.obs[0] = a1.obs[0].obs[0];
    }
  }
}
    

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/

