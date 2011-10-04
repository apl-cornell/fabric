package codebases.ast;

import fabric.lang.Codebase;
import polyglot.ast.Node_c;
import polyglot.types.Qualifier;
import polyglot.util.Position;

public class CodebaseNode_c extends Node_c implements CodebaseNode {

  public CodebaseNode_c(Position pos, Codebase c) {
    super(pos);
  }

  public Qualifier qualifier() {
    return null;
  }
}
