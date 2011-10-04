package codebases.ast;

import polyglot.ast.Id;
import polyglot.ast.Node_c;
import polyglot.util.Position;

public class CodebaseDecl_c extends Node_c implements CodebaseDecl {
  protected Id name;
 
  public CodebaseDecl_c(Position pos, Id name) {
    super(pos);
    this.name = name;
  }
}
