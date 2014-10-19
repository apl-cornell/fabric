package codebases.ast;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import polyglot.ast.Ext;
import polyglot.ast.Import;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile_c;
import polyglot.ast.TopLevelDecl;
import polyglot.types.ImportTable;
import polyglot.types.Named;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.util.CollectionUtil;
import polyglot.util.ListUtil;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeBuilder;
import polyglot.visit.TypeChecker;
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

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    // Override base type-checking behaviour to take namespaces into account.
    // XXX This code was mostly copied from SourceFile_c.

    Set<String> names = new HashSet<>();
    boolean hasPublic = false;

    for (TopLevelDecl d : decls) {
      String s = d.name();

      if (names.contains(s)) {
        throw new SemanticException("Duplicate declaration: \"" + s + "\".",
            d.position());
      }

      names.add(s);

      if (d.flags().isPublic()) {
        if (hasPublic) {
          throw new SemanticException(
              "The source contains more than one public declaration.",
              d.position());
        }

        hasPublic = true;
      }
    }

    CodebaseTypeSystem ts = (CodebaseTypeSystem) tc.typeSystem();
    Map<String, Named> importedTypes = new HashMap<>();

    for (Import i : imports) {
      if (i.kind() != Import.SINGLE_TYPE) continue;

      String s = i.name();
      CodebaseImportDel_c del = (CodebaseImportDel_c) i.del();
      Named named = del.lookupImport(ts);
      String name = named.name();

      // See JLS 2nd Ed. | 7.5.1.

      // If two single-type-import declarations in the same compilation
      // unit attempts to import types with the same simple name, then a
      // compile-time error occurs, unless the two types are the same
      // type.
      if (importedTypes.containsKey(name)) {
        Named importedType = importedTypes.get(name);
        if (!ts.equals(named, importedType)) {
          throw new SemanticException(name
              + " is already defined in a single-type import as type "
              + importedType + ".", i.position());
        }
      } else importedTypes.put(name, named);

      // If another top level type with the same simple name is otherwise
      // declared in the current compilation unit except by a
      // type-import-on-demand declaration, then a compile-time-error
      // occurs.
      if (names.contains(name)) {
        throw new SemanticException("The import " + s
            + " conflicts with a type defined in the same file.", i.position());
      }

    }

    return this;

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
