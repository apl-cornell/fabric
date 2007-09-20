package fabric.extension;

import polyglot.ast.Ext_c;
import polyglot.ast.Node;
import fabric.visit.AtomicRewriter;
import fabric.visit.ProxyRewriter;

public class FabricExt_c extends Ext_c implements FabricExt {

  public Node rewriteAtomic(AtomicRewriter ar) {
    return node();
  }

  public Node rewriteProxies(ProxyRewriter pr) {
    return node();
  }
}
