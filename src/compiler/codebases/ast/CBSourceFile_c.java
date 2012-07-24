package codebases.ast;

import java.util.List;

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
import fabric.common.FabricLocation;

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
    FabricLocation ns = ((CodebaseSource) source).canonicalNamespace();
    ImportTable it = ts.importTable(source, ns, pkg);
    tb = tb.pushPackage(pkg);
    tb.setImportTable(it);
    return tb;
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
      n.imports = ListUtil.copy(imports, true);
      n.decls = ListUtil.copy(decls, true);
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

  @SuppressWarnings("unchecked")
  @Override
  public Node copy(NodeFactory nf) {
    return ((CodebaseNodeFactory) nf).SourceFile(this.position, this.package_,
        this.codebases, this.imports, this.decls).source(this.source);
  }

}
