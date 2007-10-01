package fabric.extension;

import java.util.ArrayList;
import java.util.List;

import fabric.types.FabricTypeSystem;
import fabric.visit.ProxyRewriter;
import polyglot.ast.ArrayInit;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.Type;
import polyglot.util.Position;

public class ArrayInitExt_c extends LocatedExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.LocatedExt_c#location(polyglot.ast.Expr)
   */
  @Override
  public ArrayInit location(Expr location) {
    return (ArrayInit) super.location(location);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.FabricExt_c#rewriteProxies(fabric.visit.ProxyRewriter)
   */
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    // TODO Auto-generated method stub
    return super.rewriteProxies(pr);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.FabricExt_c#rewriteProxiesOverride(fabric.visit.ProxyRewriter)
   */
  @Override
  public Node rewriteProxiesOverride(ProxyRewriter rewriter) {
    NodeFactory nf = rewriter.nodeFactory();
    QQ qq = rewriter.qq();
    FabricTypeSystem ts = rewriter.typeSystem();

    ArrayInit arrayInit = node();
    if (location == null) location = qq.parseExpr("$getCore()");

    List<Expr> newElements = new ArrayList<Expr>(arrayInit.elements().size());
    for (Object e : arrayInit.elements()) {
      if (e instanceof ArrayInit) {
        ArrayInit ai = (ArrayInit) e;
        LocatedExt_c ext = (LocatedExt_c) ai.ext();
        newElements.add((Expr) ext.location(location));
      } else {
        newElements.add((Expr) e);
      }
    }

    arrayInit = arrayInit.elements(newElements);
    arrayInit = (ArrayInit) arrayInit.visitChildren(rewriter);

    Type oldBase = arrayInit.type().toArray().base();
    ClassType newType = ts.fArrayImplOf(oldBase);
    Type newBase = oldBase.isPrimitive() ? oldBase : ts.Object();
    Expr init =
        nf.NewArray(Position.compilerGenerated(), nf.CanonicalTypeNode(Position
            .compilerGenerated(), newBase), 1, arrayInit);
    return qq.parseExpr("new %T(%E, %E)", newType, location, init);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public ArrayInit node() {
    return (ArrayInit) super.node();
  }

}
