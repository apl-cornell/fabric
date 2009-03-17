package fabil.extension;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.types.ArrayType;
import polyglot.types.Type;
import polyglot.util.Position;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class TypeNodeExt_c extends FabILExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.FabILExt_c#rewriteProxies(fabil.visit.ProxyRewriter)
   */
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    TypeNode tn = node();
    Type type = tn.type();
    
    // Only rewrite array types.
    if (!type.isArray()) return tn;
    
    // Only rewrite Fabric arrays.
    FabILTypeSystem ts = pr.typeSystem();
    ArrayType at = type.toArray();
    if (!ts.isPureFabricType(at)) return tn;
    
    NodeFactory nf = pr.nodeFactory();
    return nf.CanonicalTypeNode(Position.compilerGenerated(), ts.toFabricRuntimeArray(at));
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
