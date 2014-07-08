/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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
// You can redistribute this software and/or modify it under the terms of
// the Ozone Core License version 1 published by ozone-db.org.
//
// The original code and portions created by Thorsten Fiebig are
// Copyright (C) 2000-@year@ by Thorsten Fiebig. All rights reserved.
// Code portions created by SMB are
// Copyright (C) 1997-@year@ by SMB GmbH. All rights reserved.
//

package OO7;

import java.util.Map;
import java.util.Random;
import java.net.URI;
import java.util.HashMap;

public class Benchmark {
  // database parameters
  public final static int LARGE = 3;
  public final static int MEDIUM = 2;
  public final static int SMALL = 1;
  public final static int TINY = 0;

  // Database parameters TINY SMALL MEDIUM LARGE
  private final static int[] numAtomicPerComp = new int[] { 20, 20, 200, 200 };
  private final int numConnPerAtomic;
  private final static int[] documentSize =
      new int[] { 20, 2000, 20000, 20000 };
  private final static double[] manualSize =
      new double[] { 1e3, 100e3, 1e6, 1e6 };
  private final static int[] numCompPerModule = new int[] { 5, 500, 500, 500 };
  private final static int[] numAssmPerAssm = new int[] { 3, 3, 3, 3 };
  private final static int[] numAssmLevels = new int[] { 3, 7, 7, 7 };
  private final static int[] numCompPerAssm = new int[] { 3, 3, 3, 3 };
  private final static int[] numModules = new int[] { 1, 1, 1, 10 };

  private static final Random rand = new Random();

  int scale = 0;
  int maxId = 10;

  // ////////////////////////////////////////////////////////////////////////////
  // Extents //
  // ////////////////////////////////////////////////////////////////////////////

  // Note: each class is responsible for maintaining its own extents.
  Map/* Integer,AtomicPart */atomicPartsById;
  // Map/*Long,AtomicPart*/ atomicPartsByBuildDate;
  Map/* Integer,CompositePart */compositePartsById;
  // Map/*String,Document*/ documentsByTitle;
  Map/* Integer,Document */documentsById;
  Map/* Integer,BaseAssembly */baseAssembliesById;
  Map/* Integer,ComplexAssembly */complexAssembliesById;
  Map/* Integer,Module */modulesById;

  public Map atomicPartsById() {
    return atomicPartsById;
    // TODO: return Collections.unmodifiableMap(atomicPartsById);
  }

  /*
   * This index isn't implemented because it requires multimaps public Map
   * atomicPartsByBuildDate() { return atomicPartsByBuildDate; // TODO: return
   * Collections.unmodifiableMap(atomicPartsByBuildDate); }
   */

  public Map compositePartsById() {
    return compositePartsById;
    // TODO: return Collections.unmodifiableMap(compositePartsById);
  }

  /*
   * This index isn't implemented because it requires multimaps public Map
   * documentsByTitle() { return documentsByTitle; //TODO: return
   * Collections.unmodifiableMap(documentsByTitle); }
   */

  public Map documentsById() {
    return documentsById;
    // TODO: return Collections.unmodifiableMap(documentsById);
  }

  public Map baseAssembliesById() {
    return baseAssembliesById;
    // TODO: return Collections.unmodifiableMap(baseAssembliesById);
  }

  public Map complexAssembliesById() {
    return complexAssembliesById;
  }

  public Map modulesById() {
    return modulesById;
    // TODO: return Collections.unmodifiableMap(modulesById);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // Database creation //
  // ////////////////////////////////////////////////////////////////////////////

  /**
   * Return an unused identifier.
   */
  int newId() {
    return maxId++;
  }

  /**
   * Create a benchmark of the corresponding size.
   * 
   * @param scale
   *                either TINY, SMALL, MEDIUM or LARGE
   * @param numConnPerAtomic
   *                the fan-out value (either 3, 6 or 9)
   */

  public Benchmark(int scale, int numConnPerAtomic) throws Exception {
    this.scale = scale;
    this.numConnPerAtomic = numConnPerAtomic;

    // Initialize extents
    this.atomicPartsById = new HashMap();
    // this.atomicPartsByBuildDate = new HashMap@partStore();
    this.compositePartsById = new HashMap();
    // this.documentsByTitle = new HashMap@documentStore();
    this.documentsById = new HashMap();
    this.baseAssembliesById = new HashMap();
    this.complexAssembliesById = new HashMap();
    this.modulesById = new HashMap();

    // Create content
    for (int i = 0; i < numModules[scale]; i++)
      createModule();

    System.err.println("Benchmarks:      1");
    System.err.println("Modules:         " + modulesById().size());
    System.err.println("Base Assemblies: " + baseAssembliesById().size());
    System.err.println("Cplx Assemblies: " + complexAssembliesById().size());
    System.err.println("Composite Parts: " + compositePartsById().size());
    System.err.println("Atomic Parts:    " + atomicPartsById().size());
    System.err.println("Documents:       " + documentsById().size());
  }

  protected Module createModule() throws Exception {
    CompositePart[] compositeParts = new CompositePart[numCompPerModule[scale]];
    for (int i = 0; i < numCompPerModule[scale]; ++i)
      compositeParts[i] = createCompositePart();

    Module module = new Module(this);
    ComplexAssembly designRoot =
        (ComplexAssembly) createAssembly(module, numAssmLevels[scale],
            compositeParts);

    module.setDesignRoot(designRoot);
    module.setManual(new Manual((int) manualSize[scale]));

    return module;
  }

  protected CompositePart createCompositePart() throws Exception {
    CompositePart compositePart = new CompositePart(this);
    compositePart.setDocumentation(new Document(this, documentSize[scale]));

    AtomicPart[] parts = new AtomicPart[numAtomicPerComp[scale]];
    for (int i = 0; i < parts.length; ++i) {
      parts[i] = createAtomicPart(compositePart);
      compositePart.addPart(parts[i]);
    }
    compositePart.setRootPart(parts[0]);

    for (int i = 0; i < parts.length; ++i) {
      // Add a connection to i's neighbor
      Connection c = new Connection(parts[i], parts[(i + 1) % parts.length]);

      // add random connections
      for (int j = 0; j < (numConnPerAtomic - 1); ++j) {
        c = new Connection(parts[i], parts[rand.nextInt(parts.length)]);
      }
    }

    return compositePart;
  }

  protected AtomicPart createAtomicPart(CompositePart parent) throws Exception {
    AtomicPart result = new AtomicPart(this);
    result.setPartOf(parent);

    return result;
  }

  protected Assembly createAssembly(Module parent, int level,
      CompositePart[] available) throws Exception {
    if (level == 1) {
      // create base assembly
      BaseAssembly result = new BaseAssembly(this);
      parent.addAssembly(result);

      for (int j = 0; j < numCompPerAssm[scale]; ++j) {
        int k = rand.nextInt(available.length);
        result.addComponentsShar(available[k]);
      }

      return result;
    } else {
      // create complex assembly
      ComplexAssembly result = new ComplexAssembly(this);
      parent.addAssembly(result);

      for (int i = 0; i < numAssmPerAssm[scale]; ++i)
        result.addSubAssembly(createAssembly(parent, level - 1, available));
      return result;
    }
  }

}

/*
 * * vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
 */

