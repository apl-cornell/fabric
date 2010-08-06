import java.lang.reflect.Method;

import meta.Class;

public class Main {

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    String className = args[0];
    String[] params  = new String[args.length - 1];
    for (int i = 1; i < args.length; i++)
      params[i-1] = args[i];
    
    java.lang.Class cls = Class.getClass(className).toJavaClass();
    Object o = cls.newInstance();
    Method m = cls.getMethod("main", String[].class);
    m.invoke(o, params);
  }
}
