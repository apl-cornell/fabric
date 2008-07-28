package fabil.visit;

import fabil.ExtensionInfo;
import fabil.extension.FabILExt;
import fabil.types.FabILTypeSystem;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Receiver;
import polyglot.qq.QQ;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

/**
 * Rewrites the <code>atomic</code> construct.
 */
public class AtomicRewriter extends NodeVisitor {
  protected QQ qq;
  protected NodeFactory nf;
  protected Receiver    tm;

  public AtomicRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
    this.nf = extInfo.nodeFactory();
    
    FabILTypeSystem ts = extInfo.typeSystem();
    Position CG = Position.compilerGenerated();
    this.tm = nf.Call(CG,
                       nf.CanonicalTypeNode(CG, ts.TransactionManager()),
                       nf.Id(CG, "getInstance"));
  }

  protected FabILExt ext(Node n) {
    return (FabILExt) n.ext();
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.NodeVisitor#leave(polyglot.ast.Node, polyglot.ast.Node,
   *      polyglot.visit.NodeVisitor)
   */
  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    return ext(n).rewriteAtomic(this);
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

  // TODO: move this into atomicExt?
  public Receiver transactionManager() {
    return tm;
  }

}
