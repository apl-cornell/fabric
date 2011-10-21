package codebases.ast;

import polyglot.ast.Node_c;
import polyglot.types.Qualifier;
import polyglot.util.Position;
import fabric.lang.Codebase;

public class CodebaseNode_c extends Node_c implements CodebaseNode {

  public CodebaseNode_c(Position pos, Codebase c) {
    super(pos);
  }

  @Override
  public Qualifier qualifier() {
    return null;
  }
}
