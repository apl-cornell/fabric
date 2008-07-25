package fabil.extension;

import polyglot.ast.Ext_c;
import polyglot.ast.Node;
import fabil.visit.AtomicRewriter;
import fabil.visit.ProxyRewriter;
import fabil.visit.ThreadRewriter;

public class FabricExt_c extends Ext_c implements FabricExt {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.FabricExt#rewriteProxiesOverride(fabric.visit.ProxyRewriter)
   */
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
}
