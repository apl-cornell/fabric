package fabric.types;

import codebases.types.CodebaseContext;

import jif.types.JifContext;
import jif.types.label.ConfPolicy;

import polyglot.ast.Expr;
import polyglot.types.Context;

public interface FabricContext extends JifContext, CodebaseContext {
  Context pushLocation(Expr location);

  Expr location();

  /**
   * Join of confidentiality policies of accesses so far
   */
  ConfPolicy accessedConf();

  /**
   * Begin access label of the current method
   */
  ConfPolicy accessedConfBound();

  /**
   * Set join of confidentiality policies of accesses so far
   */
  void setAccessedConf(ConfPolicy accessedConf);

  /**
   * Set begin access label of the current method
   */
  void setAccessedConfBound(ConfPolicy accessedConf);
}
