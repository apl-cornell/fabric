package fabric.types;

import codebases.types.CodebaseContext;

import jif.types.JifContext;
import jif.types.label.Label;

import polyglot.ast.Branch;
import polyglot.ast.Expr;
import polyglot.types.Context;

public interface FabricContext extends JifContext, CodebaseContext {
  Context pushLocation(Expr location);

  Expr location();

  /**
   * Meet of conflict labels of accesses so far
   */
  Label conflictLabel();

  /**
   * Begin conflict label of the current method
   */
  Label beginConflictBound();

  /**
   * End conflict label of the current method
   */
  Label endConflictBound();

  /**
   * Set meet of conflict labels of accesses so far
   */
  void setConflictLabel(Label conflictLab);

  /**
   * Set begin conflict label of the current method
   */
  void setBeginConflictBound(Label conflictLab);

  /**
   * Set end conflict label of the current method
   */
  void setEndConflictBound(Label endConflict);

  /**
   * Retrieve the <code>Label</code> associated with the current stage when
   * branching to the location <code>label</code>, with the branch kind
   * <code>kind</code>.
   */
  Label gotoConflictLabel(Branch.Kind kind, String label);

  /**
   * Record the <code>Label</code> associated with the current stage when
   * branching to the location <code>label</code>, with the branch kind
   * <code>kind</code>.
   */
  void gotoConflictLabel(Branch.Kind kind, String label, Label L);
}
