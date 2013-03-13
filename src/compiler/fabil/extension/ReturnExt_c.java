package fabil.extension;

import polyglot.ast.Node;
import polyglot.ast.Return;
import polyglot.qq.QQ;

import fabil.visit.MemoizedMethodRewriter;

public class ReturnExt_c extends FabILExt_c {
  @Override
  public Node rewriteMemoizedMethods(MemoizedMethodRewriter mmr) {
    Return rtn = node();
    if (mmr.inMemoizedMethod()) {
      QQ qq = mmr.qq();
      return qq.parseStmt("{\n"
          + "  $result = %E;\n"
          + "  fabric.worker.transaction.TransactionManager.getInstance().setSemanticWarrantyValue((fabric.lang.Object._Impl) $result.fetch());\n"
          + "  return $result;\n"
          + "}", rtn.expr());
    }
    return rtn;
  }

  @Override
  public Return node() {
    return (Return) super.node();
  }
}
