package codebases.ast;

import java.net.URI;

import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.Node_c;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.TypeChecker;
import codebases.types.CodebaseContext;
import codebases.types.CodebaseTypeSystem;

public class CodebaseDecl_c extends Node_c implements CodebaseDecl {
  protected Id name;

  public CodebaseDecl_c(Position pos, Id name) {
    super(pos);
    this.name = name;
  }

  @Override
  public Id name() {
    return name;
  }

  /** Check that imported classes and packages exist. */
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    CodebaseContext ctx = (CodebaseContext) tc.context();
    CodebaseTypeSystem ts = (CodebaseTypeSystem) tc.typeSystem();
    URI ns = ctx.namespace();
    URI cb = ts.namespaceResolver(ns).resolveCodebaseName(name.id());
    
    // If the name doesn't exist, a semantic error should have been thrown.
    if (cb == null) {
      throw new InternalCompilerError("Codebase name " + name
          + " resolved to null.");
    }
    
    return this;
  }

}
