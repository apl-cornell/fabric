package codebases.ast;

import java.util.List;

import polyglot.ast.Import;
import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile_c;
import polyglot.ast.TopLevelDecl;
import polyglot.util.Position;

/**
 * An extension of SourceFiles that supports codebases.
 * 
 * @author owen
 */
public class CBSourceFile_c extends SourceFile_c implements CBSourceFile {
  protected List<CodebaseDecl> codebaseDecls;

  public CBSourceFile_c(Position pos, PackageNode package1,
      List<Import> imports, List<CodebaseDecl> codebaseDecls,
      List<TopLevelDecl> decls) {
    super(pos, package1, imports, decls);
    this.codebaseDecls = codebaseDecls;
  }

  // XXX TODO: VISIT CHILDREN, add codebase names to context!

  @Override
  public List<CodebaseDecl> codebaseDecls() {
    return codebaseDecls;
  }

  @Override
  public CBSourceFile codebaseDecls(List<CodebaseDecl> codebaseDecls) {
    CBSourceFile_c n = (CBSourceFile_c) copy();
    n.codebaseDecls = codebaseDecls;
    return n;
  }
}
