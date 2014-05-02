package codebases.ast;

import java.net.URI;
import java.util.List;

import polyglot.ast.Ext;
import polyglot.ast.Import;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile_c;
import polyglot.ast.TopLevelDecl;
import polyglot.types.ImportTable;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.util.CollectionUtil;
import polyglot.util.ListUtil;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeBuilder;
import codebases.frontend.CodebaseSource;
import codebases.types.CodebaseTypeSystem;

/**
 * An extension of SourceFiles that supports codebases.
 */
//XXX Should be replaced with extension
@Deprecated
public class CBSourceFile_c extends SourceFile_c implements CBSourceFile {
  protected List<CodebaseDecl> codebases;

  @Deprecated
  public CBSourceFile_c(Position pos, PackageNode package1,
      List<Import> imports, List<CodebaseDecl> codebaseDecls,
      List<TopLevelDecl> decls) {
    this(pos, package1, imports, codebaseDecls, decls, null);
  }

  public CBSourceFile_c(Position pos, PackageNode package1,
      List<Import> imports, List<CodebaseDecl> codebaseDecls,
      List<TopLevelDecl> decls, Ext ext) {
    super(pos, package1, imports, decls, ext);
    this.codebases = codebaseDecls;
  }

  /**
   * Build type objects for the source file. Set the visitor's import table
   * field before we recurse into the declarations.
   * 
   * @throws SemanticException
   */
  @Override
  public NodeVisitor buildTypesEnter(TypeBuilder tb) throws SemanticException {
    CodebaseTypeSystem ts = (CodebaseTypeSystem) tb.typeSystem();
    Package pkg = null;
    if (package_ != null) {
      pkg = package_.package_();
    }
    URI ns = ((CodebaseSource) source).canonicalNamespace();
    ImportTable it = ts.importTable(source, ns, pkg);
    tb = tb.pushPackage(pkg);
    tb.setImportTable(it);
    return tb;
  }

  /** Visit the children of the source file. */
  @Override
  public Node visitChildren(NodeVisitor v) {
    PackageNode package_ = visitChild(this.package_, v);
    List<CodebaseDecl> codebases = visitList(this.codebases, v);
    List<Import> imports = visitList(this.imports, v);
    List<TopLevelDecl> decls = visitList(this.decls, v);
    return reconstruct(this, package_, codebases, imports, decls);
  }

  /** Reconstruct the source file. */
  protected <N extends CBSourceFile_c> N reconstruct(N n, PackageNode package_,
      List<CodebaseDecl> codebases, List<Import> imports,
      List<TopLevelDecl> decls) {
    n = super.reconstruct(n, package_, imports, decls);
    n = codebaseDecls(n, codebases);
    return n;
  }

  @Override
  public List<CodebaseDecl> codebaseDecls() {
    return codebases;
  }

  @Override
  public CBSourceFile codebaseDecls(List<CodebaseDecl> codebaseDecls) {
    return codebaseDecls(this, codebaseDecls);
  }

  protected <N extends CBSourceFile_c> N codebaseDecls(N n,
      List<CodebaseDecl> codebaseDecls) {
    if (CollectionUtil.equals(n.codebases, codebases)) return n;
    n = copyIfNeeded(n);
    n.codebases = ListUtil.copy(codebases, true);
    return n;
  }

  @Override
  public Node copy(NodeFactory nf) {
    return ((CodebaseNodeFactory) nf).SourceFile(this.position, this.package_,
        this.codebases, this.imports, this.decls).source(this.source);
  }

}
