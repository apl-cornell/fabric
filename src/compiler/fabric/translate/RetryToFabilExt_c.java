package fabric.translate;

import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabil.ast.FabILNodeFactory;
import fabric.ast.RetryStmt;

public class RetryToFabilExt_c extends ToJavaExt_c {
  /**
   * @throws SemanticException
   */
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    RetryStmt retry = (RetryStmt) node();
    return ((FabILNodeFactory) rw.java_nf()).RetryStmt(retry.position());
  }
}
