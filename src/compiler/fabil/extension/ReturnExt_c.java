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
      //Wrap it up for the value to be stored, if it's an inlineable
      //XXX: This isn't currently handling Strings...
      if (mmr.currentMethod().returnType().type().isPrimitive()) {
        return qq.parseStmt("{\n"
            + "  $result = fabric.lang.WrappedJavaInlineable.$wrap((%T) %E);\n"
            + "  fabric.worker.transaction.TransactionManager.getInstance().setSemanticWarrantyValue((fabric.lang.Object) $result.fetch());\n"
            + "  return fabric.lang.WrappedJavaInlineable.$unwrap($result);\n"
            + "}", mmr.currentMethod().returnType(), rtn.expr());
      } else {
        return qq.parseStmt("{\n"
            + "  $result = %E;\n"
            + "  fabric.worker.transaction.TransactionManager.getInstance().setSemanticWarrantyValue((fabric.lang.Object) $result.fetch());\n"
            + "  return fabric.lang.WrappedJavaInlineable.$unwrap($result);\n"
            + "}", rtn.expr());
      }
    }
    return rtn;
  }

  @Override
  public Return node() {
    return (Return) super.node();
  }
}

