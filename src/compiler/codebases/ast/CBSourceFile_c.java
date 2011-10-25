package codebases.ast;

import java.util.List;

import polyglot.ast.Import;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile_c;
import polyglot.ast.TopLevelDecl;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.util.TypedList;
import polyglot.visit.NodeVisitor;

/**
 * An extension of SourceFiles that supports codebases.
 * 
 * @author owen
 */
public class CBSourceFile_c extends SourceFile_c implements CBSourceFile {
  protected List<CodebaseDecl> codebases;

  public CBSourceFile_c(Position pos, PackageNode package1,
      List<Import> imports, List<CodebaseDecl> codebaseDecls,
      List<TopLevelDecl> decls) {
    super(pos, package1, imports, decls);
    this.codebases = codebaseDecls;
  }

  /** Visit the children of the source file. */
  @SuppressWarnings("rawtypes")
  @Override
  public Node visitChildren(NodeVisitor v) {
    PackageNode package_ = (PackageNode) visitChild(this.package_, v);
    List codebases = visitList(this.codebases, v);
    List imports = visitList(this.imports, v);
    List decls = visitList(this.decls, v);
    return reconstruct(package_, codebases, imports, decls);
  }

  /** Reconstruct the source file. */
  @SuppressWarnings("rawtypes")
  protected CBSourceFile_c reconstruct(PackageNode package_, List codebases,
      List imports, List decls) {
    if (package_ != this.package_
        || !CollectionUtil.equals(imports, this.imports)
        || !CollectionUtil.equals(decls, this.decls)
        || !CollectionUtil.equals(codebases, this.codebases)) {
      CBSourceFile_c n = (CBSourceFile_c) copy();
      n.package_ = package_;
      n.imports = TypedList.copyAndCheck(imports, Import.class, true);
      n.decls = TypedList.copyAndCheck(decls, TopLevelDecl.class, true);
      return n;
    }
    return this;
  }

  @Override
  public List<CodebaseDecl> codebaseDecls() {
    return codebases;
  }

  @Override
  public CBSourceFile codebaseDecls(List<CodebaseDecl> codebaseDecls) {
    CBSourceFile_c n = (CBSourceFile_c) copy();
    n.codebases = codebaseDecls;
    return n;
  }

  public Node copy(NodeFactory nf) {
    return ((CodebaseNodeFactory) nf).SourceFile(this.position, this.package_,
        this.codebases, this.imports, this.decls).source(this.source);
  }

}
