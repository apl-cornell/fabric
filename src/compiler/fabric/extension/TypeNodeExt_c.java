package fabric.extension;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.types.Type;
import polyglot.util.Position;
import fabric.types.FabricTypeSystem;
import fabric.visit.ProxyRewriter;

public class TypeNodeExt_c extends FabricExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.FabricExt_c#rewriteProxies(fabric.visit.ProxyRewriter)
   */
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    TypeNode tn = node();
    Type type = tn.type();
    if (!type.isArray()) return tn;
    
    NodeFactory nf = pr.nodeFactory();
    FabricTypeSystem ts = pr.typeSystem();
    return nf.CanonicalTypeNode(Position.compilerGenerated(), ts.toFArray(type.toArray()));
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public TypeNode node() {
    return (TypeNode) super.node();
  }

}
