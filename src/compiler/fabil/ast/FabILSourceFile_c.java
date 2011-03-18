package fabil.ast;

import java.util.List;

import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile_c;
import polyglot.types.Context;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.TypeBuilder;
import fabil.frontend.CodebaseSource;
import fabil.types.FabILContext;
import fabric.lang.Codebase;
import fabric.visit.CodebaseTypeBuilder;


/**
 * An extension of SourceFiles that supports codebases.
 * @author owen
 */
public class FabILSourceFile_c extends SourceFile_c implements FabILSourceFile {
  protected Codebase codebase;

  public FabILSourceFile_c(Position pos, PackageNode package1, List imports,
      List decls) {
    super(pos, package1, imports, decls);
  }
  
  @Override
  public Context enterScope(Context c) {
    
    FabILContext A =  (FabILContext) c;
    //push the codebase onto the context stack
    if(codebase != null) {
      A = (FabILContext) A.pushCodebase(codebase);
    }    
    return super.enterScope(A);
  }
  
  /** Set the codebase of the source file. */
  public FabILSourceFile codebase(Codebase cb) {
    FabILSourceFile_c n = (FabILSourceFile_c) copy();
    n.codebase = cb;
    return n;
  }
  
  /** Set the codebase of the source file. */
  public Codebase codebase() {
    return codebase;
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
