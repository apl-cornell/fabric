package fabil.extension;

import polyglot.ast.Ext_c;
import polyglot.ast.Node;
import fabil.visit.AtomicRewriter;
import fabil.visit.LocationAssigner;
import fabil.visit.ProxyRewriter;
import fabil.visit.ThreadRewriter;

public class FabILExt_c extends Ext_c implements FabILExt {
  
  public Node assignLocations(LocationAssigner la) {
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
}
