package codebases.ast;

import java.net.URI;

import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.Node_c;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.TypeBuilder;
import codebases.types.CBImportTable;
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

  @Override
  public Node buildTypes(TypeBuilder tb) throws SemanticException {
    CodebaseTypeSystem ts = (CodebaseTypeSystem) tb.typeSystem();
    CBImportTable it = (CBImportTable) tb.importTable();
    URI ns = it.namespace();
    URI cb = ts.namespaceResolver(ns).resolveCodebaseName(name.id());
    
    // If the name doesn't exist, a semantic error should have been thrown.
    if (cb == null) {
      throw new SemanticException("Unknown codebase \"" + name +"\"", position());
    }
    
    return this;
  }
}
