package fabric.extension;

import polyglot.ast.ArrayAccess;
import polyglot.ast.Node;
import fabric.visit.ProxyRewriter;

public class ArrayAccessExt_c extends FabricExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.FabricExt_c#rewriteProxies(fabric.visit.ProxyRewriter)
   */
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    ArrayAccess aa = node();
    return pr.qq().parseExpr("(%T) %E.get(%E)", aa.type(), aa.array(),
        aa.index());
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public ArrayAccess node() {
    return (ArrayAccess) super.node();
  }

}
