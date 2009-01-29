package fabil.extension;

import polyglot.ast.Ext_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabil.visit.*;

public class FabILExt_c extends Ext_c implements FabILExt {

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

  /**
   * Returns the Fabric extension object associated with the given node.
   */
  public static FabILExt ext(Node n) {
    return (FabILExt) n.ext().ext();
  }
}
