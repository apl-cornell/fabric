package fabil.extension;

import polyglot.ast.Node;
import polyglot.ast.Return;
import polyglot.qq.QQ;
import fabil.visit.MemoizedMethodRewriter;

public class ReturnExt_c extends FabILExt_c {

  @Override
  public Node rewriteMemoizedMethods(MemoizedMethodRewriter mmr) {
    Return r = node();
    if (mmr.rewriteReturns()) {
      QQ qq = mmr.qq();
      return r.expr(qq.parseExpr("(%T) $memoCache.endMemoRecord(%E)",
            mmr.methodReturnType().type(), r.expr()));
    }
    return node();
  }

  @Override
  public Return node() {
    return (Return) super.node();
  }
}
