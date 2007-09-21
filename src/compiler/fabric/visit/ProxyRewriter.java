package fabric.visit;

import fabric.ExtensionInfo;
import fabric.ast.FabricNodeFactory;
import fabric.extension.FabricExt;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Node;
import polyglot.qq.QQ;
import polyglot.visit.NodeVisitor;

/**
 * Rewrites the <code>atomic</code> construct.
 */
public class ProxyRewriter extends NodeVisitor {
  protected QQ qq;
  protected FabricNodeFactory nf;
  protected FabricTypeSystem ts;

  public ProxyRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
    this.nf = (FabricNodeFactory) extInfo.nodeFactory();
    this.ts = extInfo.typeSystem();
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

  public FabricNodeFactory nodeFactory() {
    return nf;
  }

  public FabricTypeSystem typeSystem() {
    return ts;
  }
}
