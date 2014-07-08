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
/**
 * 
 */
package fabil.extension;

import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabil.visit.AtomicRewriter;
import fabil.visit.LabelAssigner;
import fabil.visit.LocationAssigner;
import fabil.visit.ProxyRewriter;
import fabil.visit.RemoteCallRewriter;
import fabil.visit.StaticInitializerCollector;
import fabil.visit.ThreadRewriter;

/**
 * The interface for all Fabric extension nodes.
 */
public interface FabILExt extends Ext {

  /**
   * Used by <code>StaticInitializerCollector</code>.
   */
  public Node collectStaticInitializers(StaticInitializerCollector sc);

  /**
   * Used by LabelAssigner to assign labels to all <code>new</code> expressions.
   */
  public Node assignLabels(LabelAssigner la) throws SemanticException;

  /**
   * Used by LocationAssigner to assign locations to all <code>new</code>
   * expressions.
   */
  public Node assignLocations(LocationAssigner la) throws SemanticException;

  /**
   * Used by ProxyRewriter to override the default visitor behaviour.
   * 
   * @see polyglot.visit.NodeVisitor#override(Node)
   */
  public Node rewriteProxiesOverride(ProxyRewriter rewriter);

  /**
   * Used by ProxyRewriter to rewrite references to proxy references.
   */
  public Node rewriteProxies(ProxyRewriter pr);

  /**
   * Used by <code>AtomicRewriter</code> to rewrite the AST to eliminate
   * <code>atomic</code> statements.
   */
  public Node rewriteAtomic(AtomicRewriter ar);

  /**
   * Used by <code>ThreadRewriter</code> to hook Threads into the worker's
   * transaction manager.
   */
  public Node rewriteThreads(ThreadRewriter tr);

  /**
   * Used by <code>RemoteCallRewriter</code> to translate RMIs.
   */
  public Node rewriteRemoteCalls(RemoteCallRewriter rr);
}
