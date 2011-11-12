package fabric.translate;

import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import codebases.ast.CodebaseDecl;
import fabil.ast.FabILNodeFactory;

public class CodebaseDeclToFabilExt_c extends ToJavaExt_c {

  /**
   * @throws SemanticException  
   */
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    FabILNodeFactory nf = (FabILNodeFactory) rw.java_nf();
    CodebaseDecl cd = (CodebaseDecl) node();
    return nf.CodebaseDecl(cd.position(), cd.name());
  }

}
