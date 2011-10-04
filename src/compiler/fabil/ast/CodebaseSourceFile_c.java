package fabil.ast;

import java.util.List;

import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile_c;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.TypeBuilder;
import fabil.frontend.CodebaseSource;
import fabric.lang.Codebase;
import fabric.visit.CodebaseTypeBuilder;

/**
 * An extension of SourceFiles that supports codebases.
 * @author owen
 */
public class CodebaseSourceFile_c extends SourceFile_c implements FabILSourceFile {
  protected Codebase codebase;

  @SuppressWarnings("unchecked")
  public CodebaseSourceFile_c(Position pos, PackageNode package1, List imports,
      List decls) {
    super(pos, package1, imports, decls);
  }
  
  /** Set the codebase of the source file. */
  public Codebase codebase() {
    return ((CodebaseSource) source).codebase();
  }

  public boolean isRemote() {
    return ((CodebaseSource) codebase).isRemote();
  }  

  @Override
  public TypeBuilder buildTypesEnter(TypeBuilder tb) throws SemanticException {
     tb = ((CodebaseTypeBuilder) tb).pushSource((CodebaseSource) source);
     return (TypeBuilder) super.buildTypesEnter(tb);
  }
}
