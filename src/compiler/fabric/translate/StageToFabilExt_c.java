package fabric.translate;

import fabil.ast.FabILNodeFactory;

import fabric.ast.StageStmt;

import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt_c;

import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class StageToFabilExt_c extends ToJavaExt_c {
  /**
   * @throws SemanticException
   */
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    StageStmt stage = (StageStmt) node();
    return ((FabILNodeFactory) rw.java_nf()).StageStmt(stage.position());
  }
}
