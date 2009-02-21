package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.ast.AbortStmt;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt_c;

public class AbortToFabilExt_c extends ToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    AbortStmt abort = (AbortStmt)node();
    return ((FabILNodeFactory) rw.java_nf()).AbortStmt(abort.position());
  }
}
