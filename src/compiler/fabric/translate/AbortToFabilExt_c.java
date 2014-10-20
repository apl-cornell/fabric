package fabric.translate;

import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabil.ast.FabILNodeFactory;
import fabric.ast.AbortStmt;

public class AbortToFabilExt_c extends ToJavaExt_c {
  /**
   * @throws SemanticException
   */
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    AbortStmt abort = (AbortStmt) node();
    return ((FabILNodeFactory) rw.java_nf()).AbortStmt(abort.position());
  }
}
