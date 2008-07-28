/**
 * 
 */
package fabil.extension;

import polyglot.ast.Ext;
import polyglot.ast.Node;
import fabil.visit.AtomicRewriter;
import fabil.visit.LocationAssigner;
import fabil.visit.ProxyRewriter;
import fabil.visit.ThreadRewriter;

/**
 * The interface for all Fabric extension nodes.
 */
public interface FabILExt extends Ext {

  /**
   * Used by LocationAssigner to assign locations to all <code>new</code>
   * expressions.
   */
  public Node assignLocations(LocationAssigner la);

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
   * Used by <code>ThreadRewriter</code> to hook Threads into the client's
   * transaction manager.
   */
  public Node rewriteThreads(ThreadRewriter tr);
}
