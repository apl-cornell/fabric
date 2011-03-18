package fabil.extension;

import java.net.URI;

import polyglot.ast.Import;
import polyglot.ast.JL_c;
import polyglot.ast.Node;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.StringUtil;
import polyglot.visit.TypeChecker;
import fabil.types.CodebaseContext;
import fabil.types.CodebaseTypeSystem;

public class FabILImportDel_c extends JL_c {

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    // XXX Copied from Import_c with some tweaks.
    Import n = (Import) node();
    String name = n.name();

    if (n.kind() == Import.PACKAGE && tc.typeSystem().packageExists(name)) {
      return n;
    }

    // Must be importing a class, either as p.C, or as p.C.*

    // The first component of the type name must be a package.
    String pkgName = StringUtil.getFirstComponent(name);

    if (!tc.typeSystem().packageExists(pkgName)) {
      throw new SemanticException("Package \"" + pkgName + "\" not found.",
          n.position());
    }

    // The type must exist.
    CodebaseTypeSystem ts = (CodebaseTypeSystem) tc.typeSystem();
    CodebaseContext context = (CodebaseContext) tc.context();
    URI uri = URI.create(name);
    Named nt;
    if (uri.isAbsolute() || ts.isPlatformType(name)
        || !context.currentSource().isRemote()) {
      nt = ts.forName(name);
    } else {
      String absName = ts.absoluteName(context.currentCodebase(), name, true);
      nt = ts.forName(absName);
    }

    // And the type must be accessible.
    if (nt instanceof Type) {
      Type t = (Type) nt;
      if (t.isClass()) {
        tc.typeSystem().classAccessibleFromPackage(t.toClass(),
            tc.context().package_());
      }
    }

    return n;
  }
}
