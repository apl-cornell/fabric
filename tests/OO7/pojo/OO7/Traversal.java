package OO7;

/*
 * Visitor interface for DesignObjects
 */
public abstract class Traversal {
  public abstract void visitBenchmark       (Benchmark       b);
  public abstract void visitModule          (Module          m);
  public abstract void visitComplexAssembly (ComplexAssembly ca);
  public abstract void visitBaseAssembly    (BaseAssembly    ba);
  public abstract void visitAtomicPart      (AtomicPart      p);
  public abstract void visitCompositePart   (CompositePart   p);

  /** Handles loading the benchmark object and starting the traversal.  This
   *  method calls visitBenchmark(args[0]) */
  public void mainImpl(String[] args) {
    if (args.length != 2) {
      printUsage();
      System.exit(1);
    }

    try {

      int scale = -1;
      if (args[0].equalsIgnoreCase("TINY")) {
        scale = Benchmark.TINY;
      } else if (args[0].equalsIgnoreCase("SMALL")) {
        scale = Benchmark.SMALL;
      } else if (args[0].equalsIgnoreCase("MEDIUM")) {
        scale = Benchmark.MEDIUM;
      } else if (args[0].equalsIgnoreCase("LARGE")) {
        scale = Benchmark.LARGE;
      } else {
        printUsage();
        System.out.println("Invalid scale");
        System.exit(1);
      }

      int fanout = Integer.parseInt(args[1]);

      System.out.println( "creating benchmark" );
      long start = System.currentTimeMillis();
      Benchmark bm = new Benchmark(scale, fanout);
      System.out.println("elapsed time: " + (System.currentTimeMillis() - start)
          + "msec");

      System.out.println( "performing traversal" );
      start = System.currentTimeMillis();
      this.visitBenchmark (bm);
      System.out.println("elapsed time: " + (System.currentTimeMillis() - start)
          + "msec");

    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
    }
  }

  public void printUsage() {
    System.out.println("usage: tailor " + getClass().getName() + " <size> <fanout>");
    System.out.println("  creates a OO7 database and runs a traversal over it.");
    System.out.println("");
    System.out.println("  - <size> should be one of \"tiny\", \"small\", \"medium\", or \"large\"");
    System.out.println("  - <fanout> can be any number, but the standard values are 3, 6 and 9");
  }
}

/*
** vim: ts=2 sw=2 cindent cino=\:0 et syntax=java
*/
