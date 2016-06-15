package fabric.types;

import jif.translate.JifToJavaRewriter;
import jif.translate.PrincipalToJavaExpr_c;
import jif.types.principal.ExternalPrincipal;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

/**
 *
 */
public class FabExternalPrincipalToJavaExpr_c extends PrincipalToJavaExpr_c {

  @Override
  public Expr toJava(Principal principal, JifToJavaRewriter rw,
      Expr thisQualifier) throws SemanticException {
    ExternalPrincipal P = (ExternalPrincipal) principal;
    return rw.qq().parseExpr("fabric.principals.%s.getInstance()", P.name());
  }
}
