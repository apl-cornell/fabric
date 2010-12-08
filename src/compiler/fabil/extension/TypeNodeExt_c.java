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
    NodeFactory nf = pr.nodeFactory();
    Type nType;
    if (!ts.isPureFabricType(at))
      nType = ts.arrayOf(at.base());
    else
      nType = ts.toFabricRuntimeArray(at);
    return nf.CanonicalTypeNode(Position.compilerGenerated(), nType);
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
