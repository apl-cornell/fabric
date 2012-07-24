package fabric.translate;

import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import codebases.ast.CodebaseNode;
import fabil.ast.FabILNodeFactory;

public class CodebaseNodeToFabilExt_c extends ToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    FabILNodeFactory nf = (FabILNodeFactory) rw.java_nf();
    CodebaseNode cd = (CodebaseNode) node();
    return nf.CodebaseNode(cd.position(), cd.namespace(), cd.alias(),
        cd.externalNamespace());
  }
}
