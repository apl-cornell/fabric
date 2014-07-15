package fabil;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOpt;

/**
 * A quick and dirty hack for generating signature templates.
 */
public class MakeSignature {

  private Options opts;
  private PrintWriter out;

  public static void main(String[] args) throws Exception {
    new MakeSignature().run(args);
  }

  private void run(String[] args) throws Exception {
    getOpts(args);

    for (String c : opts.classes) {
      printUnit(c);
    }
  }

  private String normalize(String s) {
    if (s == null) {
      return s;
    }

    if (s.endsWith("/") || s.endsWith("\\")) {
      return s.substring(0, s.length() - 1);
    }

    return s;
  }

  private void printUnit(String clazz) throws Exception {
    Class<?> c = Class.forName(clazz);

    if (opts.out == null) {
      out = new PrintWriter(System.out, true);
    } else {
      String fn =
          opts.out + "/" + c.getCanonicalName().replace('.', '/') + ".fab";
      String dir = fn.substring(0, fn.lastIndexOf('/'));
      new File(dir).mkdirs();
      out = new PrintWriter(new FileWriter(fn), true);
    }

    Package p = c.getPackage();

    if (p != null) {
      out.println("package " + p.getName() + ";");
    }

    printClass(c);
  }

  private void printClass(Class<?> c) {
    out.println(Modifier.toString(c.getModifiers())
        + (c.isInterface() ? "" : " class") + " " + c.getSimpleName());

    // Print superclass.
    Class<?> superClass = c.getSuperclass();

    if (superClass != null && !superClass.equals(Object.class)) {
      out.println("    extends " + superClass.getCanonicalName());
    }

    // Print interfaces.
    boolean firstIface = true;

    if (opts.java) {
      out.print("    " + (c.isInterface() ? "extends" : "implements")
          + " fabric.lang.JavaInlineable");
      firstIface = false;
    }

    for (Class<?> iface : c.getInterfaces()) {
      if (firstIface) {
        firstIface = false;
        out.print("    " + (c.isInterface() ? "extends" : "implements") + " ");
      } else out.print(", ");
      out.print(iface.getCanonicalName());
    }

    // Start body.
    out.println("{");

    if (opts.fields) {
      printFields(c);
    }

    printConstructors(c);
    printMethods(c);

    if (opts.inners) {
      printInners(c);
    }

    out.println("}");
  }

  private boolean visible(int mod) {
    return Modifier.isPublic(mod) ? true
        : Modifier.isProtected(mod) ? opts.prots
            : Modifier.isPrivate(mod) ? opts.privs : opts.packs;
  }

  private void printFields(Class<?> c) {
    for (Field f : c.getDeclaredFields()) {
      int mod =
          f.getModifiers()
          & ~(Modifier.VOLATILE | Modifier.TRANSIENT | Modifier.FINAL);

      if (visible(mod)) {
        out.println("  " + Modifier.toString(mod) + " "
            + f.getType().getCanonicalName() + " " + f.getName() + ";");
      }
    }
  }

  private void printConstructors(Class<?> c) {
    for (Constructor<?> constr : c.getDeclaredConstructors()) {
      int mod = constr.getModifiers();

      if (visible(mod)) {
        out.print("  " + Modifier.toString(mod) + " " + c.getSimpleName() + "(");
        int argNum = 0;
        for (Class<?> param : constr.getParameterTypes()) {
          if (argNum > 0) out.print(", ");
          out.print(param.getCanonicalName() + " arg" + (argNum++));
        }
        out.println(") {}");
      }
    }
  }

  private void printMethods(Class<?> c) {
    for (Method m : c.getDeclaredMethods()) {
      int mod = m.getModifiers() & ~(Modifier.VOLATILE | Modifier.TRANSIENT);

      if (visible(mod)) {
        out.print("  " + Modifier.toString(mod));
        if (!Modifier.isNative(mod) && !Modifier.isAbstract(mod))
          out.print(" native");
        out.print(" " + m.getReturnType().getCanonicalName() + " "
            + m.getName() + "(");
        int argNum = 0;
        for (Class<?> param : m.getParameterTypes()) {
          if (argNum > 0) out.print(", ");
          out.print(param.getCanonicalName() + " arg" + (argNum++));
        }
        out.println(");");
      }
    }
  }

  private void printInners(Class<?> c) {
    for (Class<?> cc : c.getDeclaredClasses()) {
      int mod = cc.getModifiers();

      if (visible(mod)) {
        printClass(cc);
      }
    }
  }

  private void printUsage() {
    System.err.println("Usage: MakeSignature [options] <classname> ...");
    System.err.println("  Options:");
    System.err.println("    -d <path>  output path");
    System.err.println("    -j         Java inlineable class");
    System.err.println("    -f         output fields");
    System.err.println("    -p         output protected members");
    System.err.println("    -r         output private members");
    System.err.println("    -a         output package members");
    System.err.println("    -i         output inner members");
    System.err.println("    -e         same as -fprai");
    System.exit(-1);
  }

  private void getOpts(String[] args) {
    opts = new Options();
    GetOpt o = new GetOpt(args, Options.OPTS);

    try {
      for (int c = o.getNextOption(); c != -1; c = o.getNextOption()) {
        switch (c) {
        case 'd':
          opts.out = o.getOptionArg();
          break;
        case 'j':
          opts.java = true;
          break;
        case 'f':
          opts.fields = true;
          break;
        case 'p':
          opts.prots = true;
          break;
        case 'r':
          opts.privs = true;
          break;
        case 'a':
          opts.packs = true;
          break;
        case 'i':
          opts.inners = true;
          break;
        case 'e':
          opts.fields =
          opts.prots = opts.privs = opts.packs = opts.inners = true;
          break;
        }
      }

      opts.classes = o.getCmdArgs();
      opts.out = normalize(opts.out);
    } catch (Exception e) {
      printUsage();
    }

    if (opts.classes.length == 0) {
      printUsage();
    }
  }

  private static class Options {

    public static final String OPTS = "d:jfpraie";

    public String[] classes;
    public String out;
    public boolean java;
    public boolean fields;
    public boolean prots;
    public boolean privs;
    public boolean packs;
    public boolean inners;

  }

}
