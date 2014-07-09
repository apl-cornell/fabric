/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package OO7.traversals;

import OO7.*;

/*
 * Visitor interface for DesignObjects
 */
public abstract class Traversal {
  public abstract void visitBenchmark(Benchmark b);

  public abstract void visitModule(Module m);

  public abstract void visitComplexAssembly(ComplexAssembly ca);

  public abstract void visitBaseAssembly(BaseAssembly ba);

  public abstract void visitAtomicPart(AtomicPart p);

  public abstract void visitCompositePart(CompositePart p);

  /**
   * Handles loading the benchmark object and starting the traversal. This
   * method calls visitBenchmark(args[0])
   */
  public void mainImpl(String[] args) {
    try {

      if (args.length < 2) {
        printUsage();
        System.exit(1);
      }
      
      int scale = -1;
      if (args[0].equalsIgnoreCase("TINY")) scale = Benchmark.TINY;
      else if (args[0].equalsIgnoreCase("SMALL")) scale = Benchmark.SMALL;
      else if (args[0].equalsIgnoreCase("MEDIUM")) scale = Benchmark.MEDIUM;
      else if (args[0].equalsIgnoreCase("LARGE")) scale = Benchmark.LARGE;
      else {
        printUsage();
        System.out.println("Invalid scale");
        System.exit(1);
      }
      
      int fanout = Integer.parseInt(args[1]);

      System.out.println("creating benchmark");
      Benchmark b = new Benchmark(scale, fanout);

      long start = System.currentTimeMillis();
      this.visitBenchmark(b);
      long finish = System.currentTimeMillis();
      System.out.println("elapsed time: " + (finish - start) + "msec");
    } catch (Exception exc) {
      exc.printStackTrace();
      printUsage();
    }
  }

  public void printUsage() {
    System.out.println("usage: " + getClass().getName()
        + " <size> <fanout>");
  }
}

/*
 * * vim: ts=2 sw=2 cindent cino=\:0 et syntax=java
 */
