/**
 * A test harness for some of the classes in diaspora.util.
 */

import diaspora.util.*;
import fabric.client.*;

public class UtilTest {
  public static void main(String[] args) {
    Core core = Client.getClient().getCore(0);
    Map map;
    atomic {
      map = new HashMap@core();
      map.put("1", "6");
      map.put("8", "7");
      map.put("8", "1");
      map.put("0", "5");
      map.put("9", "2");
      map.put("7", "7");
      map.put("2", "5");
      map.put("3", "0");
      map.put("5", "2");
      map.put("6", "9");
      map.put("4", "8");
      map.put("2", "4");
      map.put("5", "3");
    }

    atomic {
      System.out.println(map.get("3"));
      System.out.println(map.get("8"));
      System.out.println(map.get("9"));
      System.out.println(map.get("5"));
      System.out.println(map.get("2"));
      System.out.println(map.get("0"));
      System.out.println(map.get("1"));
      System.out.println(map.get("7"));
      System.out.println(map.get("4"));
      System.out.println(map.get("6"));
    }
  }
}

