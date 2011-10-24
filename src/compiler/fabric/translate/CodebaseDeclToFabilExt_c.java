package fabric.translate;

import codebases.ast.CodebaseDecl;
import fabil.ast.FabILNodeFactory;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt_c;

public class CodebaseDeclToFabilExt_c extends ToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    FabILNodeFactory nf = (FabILNodeFactory) rw.java_nf();
    CodebaseDecl cd = (CodebaseDecl) node();
    return nf.CodebaseDecl(cd.position(), cd.name());
  }

}
