package fabil.extension;

import fabil.visit.AtomicRewriter;
import fabil.visit.LabelAssigner;
import fabil.visit.LocationAssigner;
import fabil.visit.ProxyRewriter;
import fabil.visit.RemoteCallChecker;
import fabil.visit.RemoteCallRewriter;
import fabil.visit.StaticInitializerCollector;
import fabil.visit.ThreadRewriter;

import polyglot.ast.Ext_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

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
  public Node checkRemoteCalls(RemoteCallChecker rc) throws SemanticException {
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
