package fabric.extension;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.types.ArrayType;
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
    
    // Only rewrite array types.
    if (!type.isArray()) return tn;
    
    // Only rewrite arrays of primitives or fabric.lang.Objects.
    FabricTypeSystem ts = pr.typeSystem();
    ArrayType at = type.toArray();
    Type base = at.ultimateBase();
    if (!base.isPrimitive() && !ts.isFabric(base)) return tn;
    
    NodeFactory nf = pr.nodeFactory();
    return nf.CanonicalTypeNode(Position.compilerGenerated(), ts.toFArray(at));
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
