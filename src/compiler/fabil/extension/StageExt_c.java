package fabil.extension;

import fabil.visit.AtomicRewriter;
import polyglot.ast.Node;

public class StageExt_c extends FabILExt_c {

  @Override
  public Node rewriteAtomic(AtomicRewriter ar) {
    return ar.qq().parseStmt(
        "fabric.worker.transaction.TransactionManager.getInstance().stageTransaction();");
  }

}
