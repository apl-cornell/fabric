package fabric.visit;

import fabric.ExtensionInfo;
import fabric.ast.FabricNodeFactory;
import fabric.extension.FabricExt;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Node;
import polyglot.qq.QQ;
import polyglot.visit.NodeVisitor;

/**
 * Rewrites Fabric classes into classes that implement
 * <code>fabric.lang.Object</code>.
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
   * @see polyglot.visit.NodeVisitor#override(polyglot.ast.Node)
   */
  @Override
  public Node override(Node n) {
    return ext(n).rewriteProxiesOverride(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.NodeVisitor#leave(polyglot.ast.Node, polyglot.ast.Node,
   *      polyglot.visit.NodeVisitor)
   */
  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    return ext(n).rewriteProxies(this);
  }

  private FabricExt ext(Node n) {
    return (FabricExt) n.ext();
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
