package codebases.ast;

import java.net.URI;

import polyglot.ast.Node_c;
import polyglot.types.Qualifier;
import polyglot.util.Position;

public class CodebaseNode_c extends Node_c implements CodebaseNode {
  protected URI namespace;
  public CodebaseNode_c(Position pos, URI namespace) {
    super(pos);
    this.namespace  = namespace;
  }

  @Override
  public Qualifier qualifier() {
    return null;
  }

  @Override
  public URI namespace() {
    return namespace;
  }
}
