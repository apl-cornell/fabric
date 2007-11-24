package fabric;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * A quick and dirty hack for generating signature templates.
 */
public class MakeSignature {
  public static void main(String[] args) throws ClassNotFoundException {
    if (args.length == 0) {
      System.err.println("Usage: MakeSignature <classname>");
      System.exit(-1);
    }

    Class<?> c = Class.forName(args[0]);
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
    System.out.println("}");
  }
}
