package fabric;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * A quick and dirty hack for generating signature templates.
 */
public class MakeSignature {
  public static void main(String[] args) throws ClassNotFoundException {
    boolean java = false;
    String clazz = null;
    
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-j")) {
        java = true;
      } else if (args[i].startsWith("-")) {
        printUsage();
      } else {
        if (clazz == null) {
          clazz = args[i];
        } else {
          printUsage();
        }
      }
    }
    
    if (clazz == null) {
      printUsage();
    }
    
    Class<?> c = Class.forName(clazz);
    Package p = c.getPackage();
    System.out.println("package " + p.getName() + ";");
    System.out.println(Modifier.toString(c.getModifiers()) + " "
        + (c.isInterface() ? "interface" : "class") + " " + c.getSimpleName());

    // Print superclass.
    Class<?> superClass = c.getSuperclass();
    if (!superClass.equals(Object.class)) {
      System.out.println("    extends " + superClass.getCanonicalName());
    }

    // Print interfaces.
    boolean firstIface = true;
    
    if (java) {
      System.out.print("    " + (c.isInterface() ? "extends" : "implements")
          + " fabric.lang.JavaInlineable");
      firstIface = false;
    }
    
    for (Class<?> iface : c.getInterfaces()) {
      if (firstIface) {
        firstIface = false;
        System.out.print("    " + (c.isInterface() ? "extends" : "implements")
            + " ");
      } else System.out.print(", ");
      System.out.print(iface.getCanonicalName());
    }

    // Start body.
    System.out.println("{");
    for (Constructor<?> constr : c.getConstructors()) {
      System.out.print("  " + Modifier.toString(constr.getModifiers()) + " "
          + c.getSimpleName() + "(");
      int argNum = 0;
      for (Class<?> param : constr.getParameterTypes()) {
        if (argNum > 0) System.out.print(", ");
        System.out.print(param.getCanonicalName() + " arg" + (argNum++));
      }
      System.out.println(") {}");
    }

    for (Method m : c.getMethods()) {
      if (m.getDeclaringClass() == c) {  // don't output inherited methods
        int mod = m.getModifiers() & ~(Modifier.VOLATILE | Modifier.TRANSIENT);
        System.out.print("  " + Modifier.toString(mod));
        if (!Modifier.isNative(mod) && !Modifier.isAbstract(mod))
          System.out.print(" native");
        System.out.print(" " + m.getReturnType().getCanonicalName() + " "
            + m.getName() + "(");
        int argNum = 0;
        for (Class<?> param : m.getParameterTypes()) {
          if (argNum > 0) System.out.print(", ");
          System.out.print(param.getCanonicalName() + " arg" + (argNum++));
        }
        System.out.println(");");
      }
    }
    System.out.println("}");
  }
  
  private static void printUsage() {
    System.err.println("Usage: MakeSignature [-j] <classname>");
    System.exit(-1);
  }
}
