package fabric.ast;

import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt;
import jif.translate.ToJavaExt_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;

/**
 *
 */
public class CannotAccessPolicyToFabilExt_c extends ToJavaExt_c implements
ToJavaExt {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    throw new InternalCompilerError("Cannot translate " + node() + " to FabIL.");
  }
}
