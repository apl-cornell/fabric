package bolt.ast;

import polyglot.ast.Expr;
import polyglot.util.CodeWriter;
import polyglot.util.SerialVersionUID;
import polyglot.visit.PrettyPrinter;

/**
 * This class mainly exists so that {@link NewLabel}s can be identified as being
 * located elements by examining their {@link BoltExt}.
 */
public class NewLabelExt extends BoltLocatedElementExt {

  private static final long serialVersionUID = SerialVersionUID.generate();

  public NewLabelExt() {
    this(null);
  }

  public NewLabelExt(Expr location) {
    super(location);
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
    super.prettyPrint(w, pp);
    superLang().prettyPrint(node(), w, pp);
  }
}
