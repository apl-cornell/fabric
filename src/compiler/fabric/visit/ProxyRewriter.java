package fabric.visit;

import fabric.ExtensionInfo;
import fabric.extension.FabricExt;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.qq.QQ;
import polyglot.visit.NodeVisitor;

/**
 * Rewrites the <code>atomic</code> construct.
 */
public class ProxyRewriter extends NodeVisitor {
  protected QQ qq;
  protected NodeFactory nf;

  public ProxyRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo.outputExtInfo());
    this.nf = extInfo.outputExtInfo().nodeFactory();
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.NodeVisitor#leave(polyglot.ast.Node, polyglot.ast.Node,
   *      polyglot.visit.NodeVisitor)
   */
  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    return ((FabricExt) n.ext()).rewriteProxies(this);
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
}
