package fabil.extension;

import java.util.ArrayList;
import java.util.List;

import polyglot.ast.ArrayInit;
import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.qq.QQ;
import polyglot.types.Type;
import polyglot.util.Position;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class ArrayInitExt_c extends LocatedExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.LocatedExt_c#location(polyglot.ast.Expr)
   */
  @Override
  public ArrayInit location(Expr location) {
    return (ArrayInit) super.location(location);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ExprExt_c#rewriteProxiesOverrideImpl(fabil.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesOverrideImpl(ProxyRewriter rewriter) {
    NodeFactory nf = rewriter.nodeFactory();
    QQ qq = rewriter.qq();
    FabILTypeSystem ts = rewriter.typeSystem();

    ArrayInit arrayInit = node();
    Expr location = location();

    List<Expr> newElements = new ArrayList<Expr>(arrayInit.elements().size());
    for (Object e : arrayInit.elements()) {
      if (e instanceof ArrayInit) {
        ArrayInit ai = (ArrayInit) e;
        LocatedExt_c ext = (LocatedExt_c) ai.ext();
        newElements.add(ext.location(location));
      } else {
        newElements.add((Expr) e);
      }
    }

    arrayInit = arrayInit.elements(newElements);
    arrayInit = (ArrayInit) arrayInit.visitChildren(rewriter);

    Type oldBase = arrayInit.type().toArray().base();
    Type newBase = oldBase.isPrimitive() ? oldBase : ts.FObject();
    Expr init =
        nf.NewArray(Position.compilerGenerated(), nf.CanonicalTypeNode(Position
            .compilerGenerated(), newBase), 1, arrayInit);
    
    // XXX Need a real label.
    return qq.parseExpr(
        "fabric.lang.arrays.internal.Compat.convert(%E, null, %E)", location,
        init);
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
