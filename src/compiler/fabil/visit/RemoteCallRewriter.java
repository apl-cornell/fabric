package fabil.visit;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.qq.QQ;
import polyglot.visit.NodeVisitor;
import fabil.ExtensionInfo;
import fabil.extension.FabILExt;
import fabil.types.FabILTypeSystem;

public class RemoteCallRewriter extends NodeVisitor {
  protected QQ qq;
  protected NodeFactory nf;
  protected FabILTypeSystem ts;

  public RemoteCallRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
    this.nf = extInfo.nodeFactory();
    this.ts = extInfo.typeSystem();
  }

  protected FabILExt ext(Node n) {
    return (FabILExt) n.ext();
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    return ext(n).rewriteRemoteCalls(this);
  }

  /**
   * @return the qq
   */
  public QQ qq() {
    return qq;
  }

  public NodeFactory nodeFactory() {
    return nf;
  }

  public FabILTypeSystem typeSystem() {
    return ts;
  }
}
