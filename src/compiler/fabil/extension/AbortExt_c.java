package fabil.extension;

import polyglot.ast.Node;
import fabil.visit.AtomicRewriter;

public class AbortExt_c extends FabILExt_c {
  @Override
  public Node rewriteAtomic(AtomicRewriter ar) {
    return ar.qq().parseStmt("throw new fabric.worker.UserAbortException();");
  }
}
