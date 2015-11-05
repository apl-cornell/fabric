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
   * Join of confidentiality policies of accesses so far
   */
  Label accessedConf();

  /**
   * Begin access label of the current method
   */
  Label accessedConfBound();

  /**
   * Begin access label of the current method
   */
  Label endConfBound();

  /**
   * Set join of confidentiality policies of accesses so far
   */
  void setAccessedConf(Label accessedConf);

  /**
   * Set begin access label of the current method
   */
  void setAccessedConfBound(Label accessedConf);

  /**
   * Set begin access label of the current method
   */
  void setEndConfBound(Label endConf);
}
