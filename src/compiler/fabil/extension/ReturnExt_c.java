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
      return qq.parseStmt("{ $memoCache.endMemoRecord(%E); return; }", r.expr());
    }
    return node();
  }

  @Override
  public Return node() {
    return (Return) super.node();
  }
}
