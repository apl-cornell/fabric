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
package fabil.extension;

import polyglot.ast.Ext_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabil.visit.AtomicRewriter;
import fabil.visit.LabelAssigner;
import fabil.visit.LocationAssigner;
import fabil.visit.ProxyRewriter;
import fabil.visit.RemoteCallRewriter;
import fabil.visit.StaticInitializerCollector;
import fabil.visit.ThreadRewriter;

public class FabILExt_c extends Ext_c implements FabILExt {

  @Override
  public Node collectStaticInitializers(StaticInitializerCollector sc) {
    return node();
  }

  /**
   * @throws SemanticException
   *           when a label is missing and no default can be assigned.
   */
  @Override
  public Node assignLabels(LabelAssigner la) throws SemanticException {
    return node();
  }

  /**
   * @throws SemanticException
   */
  @Override
  public Node assignLocations(LocationAssigner la) throws SemanticException {
    return node();
  }

  @Override
  public Node rewriteProxiesOverride(ProxyRewriter rewriter) {
    return null;
  }

  @Override
  public Node rewriteAtomic(AtomicRewriter ar) {
    return node();
  }

  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    return node();
  }

  @Override
  public Node rewriteThreads(ThreadRewriter tr) {
    return node();
  }

  @Override
  public Node rewriteRemoteCalls(RemoteCallRewriter rr) {
    return node();
  }

  /**
   * Returns the Fabric extension object associated with the given node.
   */
  public static FabILExt ext(Node n) {
    return (FabILExt) n.ext().ext();
  }
}
