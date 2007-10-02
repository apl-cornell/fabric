package fabric.extension;

import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.Type;
import fabric.visit.ProxyRewriter;

public class FieldExt_c extends FabricExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.FabricExt_c#rewriteProxies(fabric.visit.ProxyRewriter)
   */
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    Field field = node();
    Receiver target = field.target();
    Type targetType = target.type();

    // TODO Need to handle static fields.

    // Only rewrite field accesses on Fabric objects.
    if (!pr.typeSystem().isFabric(targetType)) return super.rewriteProxies(pr);
    
    return pr.qq()
        .parseExpr("%E.get$" + field.id().id() + "()", field.target());
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public Field node() {
    return (Field) super.node();
  }

}
