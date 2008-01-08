// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: Benchmark.java,v 1.1 2008-01-08 18:38:02 mdgeorge Exp $

package OO7;

import java.util.*;
import java.util.Random;
import java.net.URI;

public class Benchmark {
  // data base parameters
  public final static int LARGE  = 3;
  public final static int MEDIUM = 2;
  public final static int SMALL  = 1;
  public final static int TINY   = 0;

  //                            Database paramters                   TINY  SMALL  MEDIUM LARGE
  private final static int[]    numAtomicPerComp = new int[]{ 20,   20,    200,   200    };
  private final        int      numConnPerAtomic;                     
  private final static int[]    documentSize     = new int[]{ 20,   2000,  20000, 20000  };
  private final static double[] manualSize       = new double[]{ 1e3,  100e3, 1e6,   1e6    };
  private final static int[]    numCompPerModule = new int[]{ 5,    500,   500,   500    };
  private final static int[]    numAssmPerAssm   = new int[]{ 3,    3,     3,     3      };
  private final static int[]    numAssmLevels    = new int[]{ 3,    7,     7,     7      };
  private final static int[]    numCompPerAssm   = new int[]{ 3,    3,     3,     3      };
  private final static int[]    numModules       = new int[]{ 1,    1,     1,     10     };

  private final static String database = "OO7 Database";

  private static final Random rand = new Random();

  int scale = 0;
  // TODO: expose extents
  Module module = null;
  HashMap atomicParts = null;

  //////////////////////////////////////////////////////////////////////////////
  // Database traversals                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public void matchQuery() throws Exception {
    // generate a random list of objects to fetch
    int[] oids = new int[1000];
    for (int i = 0; i < oids.length; ++i)
      oids[i] = rand.nextInt(atomicParts.size());

    // access each
    long time = System.currentTimeMillis();
    for (int i = 0; i < oids.length; ++i) {
      AtomicPart part = (AtomicPart) atomicParts.get(new Integer(oids[i]));
      long x = part.x();
    }
    time = (System.currentTimeMillis() - time);
    System.out.println("Millis: " + time);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Database creation                                                        //
  //////////////////////////////////////////////////////////////////////////////

  /** Create a benchmark of the corresponding size.
    *
    * @param scale either TINY, SMALL, MEDIUM or LARGE
    * @param numConnPerAtomic the fan-out value (either 3, 6 or 9)
    */
  public Benchmark(int scale, int numConnPerAtomic) throws Exception {
    this.scale = scale;
    this.numConnPerAtomic = numConnPerAtomic;
    this.atomicParts = new HashMap();
    this.module = createModule();
  }

  protected Module createModule() throws Exception {
    CompositePart[] compositeParts =
        new CompositePart[numCompPerModule[scale]];
    for (int i = 0; i < numCompPerModule[scale]; ++i)
      compositeParts[i] = createCompositePart();

    Module module = new Module();
    ComplexAssembly designRoot =
        (ComplexAssembly) createAssembly(module, numAssmLevels[scale],
            compositeParts);
    module.setDesignRoot(designRoot);

    return module;
  }

  protected CompositePart createCompositePart() throws Exception {
    CompositePart compositePart = new CompositePart();
    compositePart.setDocumentation(new Document());

    AtomicPart[] parts = new AtomicPart[numAtomicPerComp[scale]];
    for (int i = 0; i < parts.length; ++i) {
      parts[i] = createAtomicPart(compositePart);
      compositePart.addPart(parts[i]);
    }
    compositePart.setRootPart(parts[0]);

    for (int i = 0; i < parts.length; ++i) {
      // Add a connection to i's neighbor
      Connection c =
          new Connection(parts[i], parts[(i + 1) % parts.length]);

      // add random connections
      for (int j = 0; j < (numConnPerAtomic - 1); ++j) {
        c = new Connection(parts[i], parts[rand.nextInt(parts.length)]);
      }
    }

    return compositePart;
  }

  protected AtomicPart createAtomicPart(CompositePart parent) throws Exception {
    AtomicPart result = new AtomicPart();
    atomicParts.put(new Integer(atomicParts.size()), result);
    result.setPartOf(parent);
    return result;
  }

  protected Assembly createAssembly(Module parent, int level,
      CompositePart[] available) throws Exception {
    if (level == 1) {
      // create base assembly
      BaseAssembly result = new BaseAssembly();
      parent.addAssembly(result);

      for (int j = 0; j < numCompPerAssm[scale]; ++j) {
        int k = rand.nextInt(available.length);
        result.addComponentsShar(available[k]);
      }

      return result;
    } else {
      // create complex assembly
      ComplexAssembly result = new ComplexAssembly();
      parent.addAssembly(result);

      for (int i = 0; i < numAssmPerAssm[scale]; ++i)
        result.addSubAssembly(createAssembly(parent, level - 1, available));
      return result;
    }
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/

