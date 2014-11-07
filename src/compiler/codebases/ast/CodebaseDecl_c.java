package codebases.ast;

import java.net.URI;

import polyglot.ast.Ext;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.Node_c;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.TypeBuilder;
import codebases.types.CBImportTable;
import codebases.types.CodebaseTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class CodebaseDecl_c extends Node_c implements CodebaseDecl {
  protected Id name;

  @Deprecated
  public CodebaseDecl_c(Position pos, Id name) {
    this(pos, name, null);
  }

  public CodebaseDecl_c(Position pos, Id name, Ext ext) {
    super(pos, ext);
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
      throw new SemanticException("Unknown codebase \"" + name + "\"",
          position());
    }

    return this;
  }
}
