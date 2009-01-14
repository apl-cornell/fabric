package fabil.extension;

import polyglot.ast.Ext_c;
import polyglot.ast.Node;
import fabil.visit.*;

public class FabILExt_c extends Ext_c implements FabILExt {
  
  public Node assignLabels(LabelAssigner la) {
    return node();
  }
  
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
  
  /** Return the Fabric extension object associated with a node. */
  public static FabILExt ext(Node n) {
    return (FabILExt) n.ext().ext();
  }
}
