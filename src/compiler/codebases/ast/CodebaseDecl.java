package codebases.ast;

import polyglot.ast.Id;
import polyglot.ast.Node;

public interface CodebaseDecl extends Node {
  Id name();
}
