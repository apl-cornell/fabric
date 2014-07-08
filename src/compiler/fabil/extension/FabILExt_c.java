/**
 * Copyright (C) 2010 Fabric project group, Cornell University
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
import fabil.visit.*;

public class FabILExt_c extends Ext_c implements FabILExt {

  public Node collectStaticInitializers(StaticInitializerCollector sc) {
    return node();
  }

  /**
   * @throws SemanticException
   *           when a label is missing and no default can be assigned.
   */
  public Node assignLabels(LabelAssigner la) throws SemanticException {
    return node();
  }

  /**
   * @throws SemanticException
   */
  public Node assignLocations(LocationAssigner la) throws SemanticException {
    return node();
  }

  public Node rewriteProxiesOverride(ProxyRewriter rewriter) {
    return null;
  }

  public Node rewriteAtomic(AtomicRewriter ar) {
    return node();
  }

  public Node rewriteProxies(ProxyRewriter pr) {
    return node();
  }

  public Node rewriteThreads(ThreadRewriter tr) {
    return node();
  }
  
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
