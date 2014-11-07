package codebases.ast;

import java.net.URI;

import polyglot.ast.Import;
import polyglot.ast.JLDel_c;
import polyglot.ast.Node;
import polyglot.main.Options;
import polyglot.types.ClassType;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.CodeWriter;
import polyglot.util.StringUtil;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.TypeChecker;
import codebases.types.CodebaseContext;
import codebases.types.CodebaseTypeSystem;
import codebases.visit.CodebaseTranslator;

public class CodebaseImportDel_c extends JLDel_c {
  protected URI ns;
  protected ClassType ct;

  /** Check that imported classes and packages exist. */
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    Import im = (Import) node();
    CodebaseContext ctx = (CodebaseContext) tc.context();
    ns = ctx.namespace();
    CodebaseTypeSystem ts = (CodebaseTypeSystem) tc.typeSystem();

    if (im.kind() == Import.TYPE_IMPORT_ON_DEMAND
        && ts.packageExists(ns, im.name())) {
      return im;
    }

    // Must be importing a class, either as p.C, or as p.C.*
    Named nt = lookupImport(ts);

    // And the type must be accessible.
    if (nt instanceof Type) {
      Type t = (Type) nt;
      if (t.isClass()) {
        tc.typeSystem().classAccessibleFromPackage(t.toClass(),
            tc.context().package_());
      }
    }
    ct = (ClassType) nt;

    return im;
  }

  /**
   * Codebase version of ts.forName(). Also ensures the first component of the
   * imported name is either a package or a codebase alias.
   */
  public Named lookupImport(CodebaseTypeSystem ts) throws SemanticException {
    final Import im = (Import) node();

    // The first component of the type name must be a package or a codebase
    // alias.
    String pkgName = StringUtil.getFirstComponent(im.name());

    URI import_ns = ts.namespaceResolver(ns).resolveCodebaseName(pkgName);
    if (import_ns != null) {
      // The type must exist in import_ns
      return ts.forName(import_ns, StringUtil.removeFirstComponent(im.name()));
    }

    // Not an alias. Must be a package.
    // Emulate base behaviour.
    if (!ts.packageExists(ns, pkgName)) {
      throw new SemanticException("Package \"" + pkgName + "\" not found.",
          im.position());
    }

    // The type must exist.
    return ts.forName(ns, im.name());

  }

  /** Write the import to an output file. */
  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
    if (!Options.global.fully_qualified_names
        && (tr instanceof CodebaseTranslator)) {
      Import im = (Import) node();
      CodebaseTranslator cbtr = (CodebaseTranslator) tr;
      w.write("import ");

      if (im.kind() == Import.TYPE_IMPORT_ON_DEMAND) {
        w.write(cbtr.namespaceToJavaPackagePrefix(ns));
        w.write(im.name());
        w.write(".*");
      } else {
        w.write(ct.translate(null));
      }

      w.write(";");
      w.newline(0);
    } else {
      super.prettyPrint(w, tr);
    }
  }

}
