package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.ast.RetryStmt;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt_c;

public class RetryToFabilExt_c extends ToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    RetryStmt retry = (RetryStmt)node();
    return ((FabILNodeFactory) rw.java_nf()).RetryStmt(retry.position());
  }
}
