package fabric.translate;

import codebases.ast.CodebaseNode;
import fabil.ast.FabILNodeFactory;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt_c;

public class CodebaseNodeToFabilExt_c extends ToJavaExt_c {

  @SuppressWarnings("unused")
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    FabILNodeFactory nf = (FabILNodeFactory) rw.java_nf();
    CodebaseNode cd = (CodebaseNode) node();
    return nf.CodebaseNode(cd.position(), cd.namespace(), cd.alias(),
        cd.externalNamespace());
  }
}
