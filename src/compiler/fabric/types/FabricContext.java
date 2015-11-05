package fabric.types;

import codebases.types.CodebaseContext;

import jif.types.JifContext;
import jif.types.label.Label;

import polyglot.ast.Expr;
import polyglot.types.Context;

public interface FabricContext extends JifContext, CodebaseContext {
  Context pushLocation(Expr location);

  Expr location();

  /**
   * Join of update labels of accesses so far
   */
  Label accessedLabel();

  /**
   * Begin access label of the current method
   */
  Label accessedLabelBound();

  /**
   * Begin access label of the current method
   */
  Label endAccessBound();

  /**
   * Set join of update labels of accesses so far
   */
  void setAccessedLabel(Label accessedLab);

  /**
   * Set begin access label of the current method
   */
  void setAccessedLabelBound(Label accessedLab);

  /**
   * Set end access label of the current method
   */
  void setEndAccessBound(Label endAccess);
}
