/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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
package fabric.types;

import jif.types.Assertion;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.hierarchy.LabelEnv;
import jif.types.label.AccessPath;
import jif.types.label.ConfPolicy;
import jif.types.label.IntegPolicy;
import jif.types.label.Label;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import codebases.frontend.CodebaseSource;
import codebases.types.CodebaseTypeSystem;
import fabric.ast.RemoteWorkerGetter;
import fabric.ast.Store;

public interface FabricTypeSystem extends JifTypeSystem, CodebaseTypeSystem {
  ClassType FObject();

  ClassType RemoteWorker();

  ClassType Worker();

  ClassType Store();

  ClassType DelegatingPrincipal();

  /**
   * Constructs a principal for the local worker. Remote workers directly use
   * <code>DynamicPrincipal</code>.
   * 
   * @param pos
   * @return
   */
  Principal workerLocalPrincipal(Position pos);

  WorkerLocalInstance workerLocalInstance();

  FabricDefaultSignature fabricDefaultSignature();

  FabricFieldInstance fabricFieldInstance(Position pos,
      ReferenceType container, Flags flags, Type type, ConfPolicy accessLabel,
      String name);

  Type strip(Type type);

  ConfPolicy representableConfProjection(Label L);

  IntegPolicy representableIntegProjection(Label L);

  /**
   * Returns true if type extends fabric.lang.Object
   */
  boolean isPersistent(Type type);

  /**
   * Returns true if type does not extend fabric.lang.Object
   */
  public boolean isTransient(Type type);

  /**
   * Checks whether <code>type</code> is a Fabric class, that is, inherits
   * <code>fabric.lang.Object</code>.
   * Returns false if <code>type</code> is an interface.
   */
  boolean isFabricClass(Type type);

  /**
   * Checks whether <code>type</code> is a Fabric interface,
   * and inherits <code>fabric.lang.Object</code>.
   */
  boolean isFabricInterface(Type type);

  boolean isFabricArray(Type type);

  FabricArrayType toFabricArray(Type type);

  FabricArrayType fabricArrayOf(Position pos, Type t);

  FabricArrayType fabricArrayOf(Position pos, Type t, int dims);

  /** Returns the join of L1 and L2 as per the trust ordering */
  Label tjoin(Label L1, Label L2);

  /** Returns the meet of L1 and L2 as per the trust ordering */
  Label tmeet(Label L1, Label L2);

  /** Compares L1 and L2 as per the trust ordering */
  boolean tleq(LabelEnv env, Label L1, Label L2);

  fabric.lang.security.Label sourceUpdateLabel(CodebaseSource src);

  fabric.lang.security.ConfPolicy sourceAccessPolicy(CodebaseSource src);

  /**
   * Returns true if label contains a {this} label.
   */
  boolean containsThisLabel(Label label);

  /**
   * Returns true if assertion contains a {this} label.
   */
  boolean containsThisLabel(Assertion assertion);

  /**
   * Returns true if assertion contains a argument label.
   */
  boolean containsArgLabel(Assertion as);

  /**
   * Returns true if label contains a argument label.
   */
  boolean containsArgLabel(Label label);

  /**
   * Returns a label with c joined with a top integrity component.
   */
  Label toLabel(ConfPolicy c);

  /**
   * @param label
   * @return
   */
  Label replaceProjections(Label label);

  /**
   * @param expr
   * @param context
   * @param pos
   * @return
   * @throws SemanticException
   */
  Principal storePrincipal(Store store, FabricContext context, Position pos)
      throws SemanticException;

  /**
   * @param worker
   * @param context
   * @param pos
   * @return
   * @throws SemanticException
   */
  Principal remoteWorkerPrincipal(RemoteWorkerGetter worker,
      FabricContext context, Position pos) throws SemanticException;

  /**
   * @param ref
   * @param context
   * @return
   * @throws SemanticException
   */
  AccessPath storeAccessPathFor(Expr ref, JifContext context)
      throws SemanticException;

}
