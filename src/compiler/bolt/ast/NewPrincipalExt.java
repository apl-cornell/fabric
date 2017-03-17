package bolt.ast;

import polyglot.ast.Expr;
import polyglot.util.CodeWriter;
import polyglot.util.SerialVersionUID;
import polyglot.visit.PrettyPrinter;

/**
 * This class mainly exists so that {@link NewPrincipal}s can be identified as
 * being located elements by examining their {@link BoltExt}.
 */
public class NewPrincipalExt extends BoltLocatedElementExt {

  private static final long serialVersionUID = SerialVersionUID.generate();

  public NewPrincipalExt() {
    this(null);
  }

  public NewPrincipalExt(Expr location) {
    super(location);
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
    super.prettyPrint(w, pp);
    superLang().prettyPrint(node(), w, pp);
  }
}
