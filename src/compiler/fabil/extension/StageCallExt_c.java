package fabil.extension;

import fabil.ast.StageCall;
import fabil.visit.AtomicRewriter;

import polyglot.ast.Node;

public class StageCallExt_c extends FabILExt_c {

  @Override
  public Node rewriteAtomic(AtomicRewriter ar) {
    StageCall sc = (StageCall) node();
    return ar.qq().parseExpr(
        "fabric.worker.transaction.TransactionManager.getInstance().stageTransactionExpr(%E, %E)",
        sc.origExpr(), sc.flagExpr());
  }

}
