package fabil.extension;

import polyglot.ast.ArrayAccess;
import polyglot.ast.ArrayAccessAssign;
import polyglot.ast.Expr;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class ArrayAccessAssignExt_c extends ExprExt_c {

  @Override
  public Expr rewriteProxiesOverrideImpl(ProxyRewriter rewriter) {
    ArrayAccessAssign assign = node();
    ArrayAccess left = (ArrayAccess) assign.left();
    Expr array = left.array();
    
    // Only rewrite Fabric arrays.
    FabILTypeSystem ts = rewriter.typeSystem();
    if (!ts.isFabricType(array.type())) return null;

    array = (Expr) left.visitChild(array, rewriter);
    Expr index = (Expr) left.visitChild(left.index(), rewriter);
    Expr right = (Expr) assign.visitChild(assign.right(), rewriter);

    return rewriter.qq().parseExpr("%E.set(%E, %E)", array, index, right);
  }

  @Override
  public ArrayAccessAssign node() {
    return (ArrayAccessAssign) super.node();
  }

}
