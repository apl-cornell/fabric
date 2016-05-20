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

  /**
   * Do we need to stage the next access, regardless of what the current
   * conflict label is?
   *
   * This flag helps us handle issues with staging after something like an if
   * statement which might or might not have started the stage it finishes in.
   */
  public boolean stageStarted();

  /**
   * Update the stage started flag.  This only affects the current context (so
   * if we leave the current block, it will reset to false.
   */
  public void setStageStarted(boolean flag);
}
