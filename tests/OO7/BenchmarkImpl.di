// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//
// $Id: BenchmarkImpl.di,v 1.3 2007-09-04 20:30:21 jed Exp $

package OO7;

import diaspora.util.*;
import java.util.Random;
import java.util.Hashtable;

import diaspora.client.Core;

public class BenchmarkImpl implements Benchmark {
  // data base parameters
  private final static int fTest1Conn = 0;
  private final static int fTest3Conn = 1;
  private final static int fTiny = 2;
  private final static int fSmall = 3;

  private final static int[] fNumAtomicPerComp = { 20, 20, 20, 20 };
  private final static int[] fConnPerAtomic = { 1, 3, 3, 3 };
  private final static int[] fDocumentSize = { 20, 20, 20, 2000 };
  private final static int[] fManualSize = { 1000, 1000, 1000, 100000 };
  private final static int[] fNumCompPerModule = { 5, 5, 50, 500 };
  private final static int[] fNumAssmPerAssm = { 3, 3, 3, 3 };
  private final static int[] fNumAssmLevels = { 3, 3, 7, 7 };
  private final static int[] fNumCompPerAssm = { 3, 3, 3, 3 };
  private final static int[] fNumModules = { 1, 1, 1, 1 };

  final static boolean verbose = false;

  static Random theRandom = null;

  int theScale = 0;

  long theOid = 0;

  Module theModule = null;

  public static void main(String[] args) {
    if (args.length == 0) {
      printUsage();
      System.exit(1);
    } else {
      if (args.length == 1 && args[1] == "query") {
        printUsage();
        System.exit(1);
      }
    }

    try {
      long start = System.currentTimeMillis();

      if (args[0].equals("query")) {
        if (args[1].equals("traversal")) {
          // XXX assume benchmark object exists at OID 7.
          // XXX should really be looking up a human-readable name in a name service 
          Benchmark anBenchmark =
              (Benchmark) diaspora.client.Client.getClient().getCore(0).readObject(7);
          anBenchmark.traversalQuery();
        } else {
          if (args[1].equals("match")) {
            // XXX assume benchmark object exists at OID 7.
            // XXX should really be looking up a human-readable name in a name service 
            Benchmark anBenchmark =
              (Benchmark) diaspora.client.Client.getClient().getCore(0).readObject(7);
            anBenchmark.matchQuery();
          }
        }
      } else {
        if (args[0].equals("create")) {
          int scale = -1;
          if (args[1].equals("test3Conn")) {
            scale = fTest3Conn;
          } else if (args[1].equals("test1Conn")) {
            scale = fTest1Conn;
          } else if (args[1].equals("tiny")) {
            scale = fTiny;
          } else if (args[1].equals("small")) {
            scale = fSmall;
          } else {
            System.out.println("Invalid scale");
            System.exit(1);
          }
          diaspora.client.Core core = diaspora.client.Client.getClient().getCore(0); 
          Benchmark anBenchmark =
            new BenchmarkImpl@core();
          anBenchmark.create(scale);
        }
      }

      System.out.println("time: " + (System.currentTimeMillis() - start)
          + "msec");

    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
    }
  }

  static void printUsage() {
    System.out.println("usage: BanchmarkImpl (create|query) [options]");
    System.out.println("    create options:");
    System.out.println("        size        - (tiny|small|large)");
    System.out.println("    query options:");
    System.out.println("        type        - (traversal|match)");
  }

  static int getRandomInt(int lower, int upper) {
    if (theRandom == null) {
      theRandom = new Random();
    }

    int rVal;
    do {
      rVal = theRandom.nextInt();
      rVal %= upper;
      // System.out.println("rVal: " + rVal + " lower: " + lower + " upper: " +
      // upper);
    } while (rVal < lower || rVal >= upper);
    return rVal;
  }

  protected long getAtomicPartOid() {
    return theOid++;
  }

  public void create(int anScale) throws Exception {
    theScale = anScale;
    theModule = createModule();
  }

  public void traversalQuery() throws Exception {
    Hashtable table = new Hashtable();
    long time = System.currentTimeMillis();
    traversal(theModule.designRoot(), table);
    time = (System.currentTimeMillis() - time);
    System.out.println("Millis: " + time);
  }

  protected void traversal(Assembly anAssembly, Hashtable aTable)
      throws Exception {
    if (anAssembly.getClass() == Class.forName("BaseAssemblyImpl_Proxy")) {
      // System.out.println( "Base Assembly Class: " );
      BaseAssembly baseAssembly = (BaseAssembly) anAssembly;
      Iterator compIterator = baseAssembly.componentsShar().iterator();
      while (compIterator.next() != null) {
        CompositePart compositePart =
            (CompositePart) compIterator.object();
        dfs(compositePart);
      }
    } else {
      ComplexAssembly complexAssembly = (ComplexAssembly) anAssembly;
      Iterator aIterator = complexAssembly.subAssemblies().iterator();
      while (aIterator.next() != null) {
        traversal((Assembly) aIterator.object(), aTable);
      }
    }
  }

  protected void dfs(CompositePart aPart) throws Exception {
    Hashtable table = new Hashtable();
    dfsVisit(aPart.rootPart(), table);
    // System.out.println( "AtomicParts visited: " + table.size() );
  }

  protected void dfsVisit(AtomicPart anAtomicPart, Hashtable aTable)
      throws Exception {
    Iterator connIterator = anAtomicPart.from().iterator();
    while (connIterator.next() != null) {
      Connection connection = (Connection) connIterator.object();
      AtomicPart part = connection.to();
      if (!aTable.containsKey(part)) {
        aTable.put(part, part);
        dfsVisit(part, aTable);
      }
    }
  }

  public void matchQuery() throws Exception {
    int atomicParts = fNumAtomicPerComp[theScale] * fNumCompPerModule[theScale];
    long[] oids = new long[1000];
    int i;
    for (i = 0; i < oids.length; ++i) {
      oids[i] = getRandomInt(0, atomicParts);
      // System.out.println( "oids[" + i + "] : " + oids[i] );
    }
    long time = System.currentTimeMillis();
    for (i = 0; i < oids.length; ++i) {
      AtomicPart part =
          (AtomicPart) database().objectForName("AtomicPart" + oids[i]);
      long x = part.x();
    }
    time = (System.currentTimeMillis() - time);
    System.out.println("Millis: " + time);
  }

  protected Module createModule() throws Exception {
    CompositePart[] compositeParts =
        new CompositePart[fNumCompPerModule[theScale]];
    for (int i = 0; i < fNumCompPerModule[theScale]; ++i) {
      compositeParts[i] = createCompositePart();
    }
    Module module =
        (Module) database().createObject(ModuleImpl.class.getName(), 0,
            "Module");
    ComplexAssembly designRoot =
        (ComplexAssembly) createAssembly(module, fNumAssmLevels[theScale],
            compositeParts);
    module.setDesignRoot(designRoot);

    return module;
  }

  protected CompositePart createCompositePart() throws Exception {
    // Document erzeugen
    Document document =
        (Document) database().createObject(
            DocumentImpl.class.getName(), 0);
    // CompositeParterzeugen
    CompositePart compositePart =
        (CompositePart) database().createObject(
            CompositePartImpl.class.getName(), 0);
    if (verbose) {
      System.out.println("CompositePart created");
    }
    compositePart.setDocumentation(document);

    AtomicPart[] atomicParts =
        new AtomicPart[fNumAtomicPerComp[theScale]];
    // AtomicParts erzeugen
    for (int i = 0; i < fNumAtomicPerComp[theScale]; ++i) {
      long oid = getAtomicPartOid();
      atomicParts[i] =
          (AtomicPart) database().createObject(
              AtomicPartImpl.class.getName(), 0, "AtomicPart" + oid);
      if (verbose) {
        System.out.println("AtomicPart: " + oid + " created");
      }
      compositePart.addPart(atomicParts[i]);
      atomicParts[i].setPartOf(compositePart);
    }
    compositePart.setRootPart(atomicParts[0]);

    // AtomicParts miteinander verbinden
    for (int i = 0; i < fNumAtomicPerComp[theScale]; ++i) {
      int next = (i + 1) % fNumAtomicPerComp[theScale];
      Connection connection =
          (Connection) database().createObject(
              ConnectionImpl.class.getName(), 0);
      connection.setFrom(atomicParts[i]);
      atomicParts[i].addFrom(connection);
      connection.setTo(atomicParts[next]);
      atomicParts[next].addTo(connection);
      if (verbose) {
        System.out.println("Connection: from: " + i + " to: " + next);
      }
      for (int j = 0; j < (fConnPerAtomic[theScale] - 1); ++j) {
        next = getRandomInt(0, fNumAtomicPerComp[theScale]);
        connection =
            (Connection) database().createObject(
                ConnectionImpl.class.getName(), 0);
        connection.setFrom(atomicParts[j]);
        atomicParts[j].addFrom(connection);
        connection.setTo(atomicParts[next]);
        atomicParts[next].addTo(connection);
        if (verbose) {
          System.out.println("Connection: from: " + j + " to: " + next);
        }
      }
    }
    return compositePart;
  }

  protected Assembly createAssembly(Module aModule, int aLevel,
      CompositePart[] someCompositeParts) throws Exception {
    if (verbose) {
      System.out.println("level: " + aLevel);
    }
    if (aLevel == 1) {
      BaseAssembly baseAssembly =
          (BaseAssembly) database().createObject(
              BaseAssemblyImpl.class.getName(), 0);
      aModule.addAssembly(baseAssembly);
      for (int j = 0; j < fNumCompPerAssm[theScale]; ++j) {
        int k = getRandomInt(0, fNumCompPerModule[theScale]);
        baseAssembly.addComponentsShar(someCompositeParts[k]);
      }
      return baseAssembly;
    } else {
      ComplexAssembly complexAssembly =
          (ComplexAssembly) database().createObject(
              ComplexAssemblyImpl.class.getName(), 0);
      aModule.addAssembly(complexAssembly);
      for (int i = 0; i < fNumAssmPerAssm[theScale]; ++i) {
        complexAssembly.addSubAssembly(createAssembly(aModule, aLevel - 1,
            someCompositeParts));
      }
      return complexAssembly;
    }
  }
}
