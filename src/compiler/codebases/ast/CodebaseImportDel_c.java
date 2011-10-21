package codebases.ast;

import java.net.URI;

import polyglot.ast.Import;
import polyglot.ast.JL_c;
import polyglot.ast.Node;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.StringUtil;
import polyglot.visit.TypeChecker;
import codebases.types.CodebaseContext;
import codebases.types.CodebaseTypeSystem;

public class CodebaseImportDel_c extends JL_c {

  /** Check that imported classes and packages exist. */
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    Import im = (Import) node();
    CodebaseContext ctx = (CodebaseContext) tc.context();
    URI ns = ctx.namespace();
    CodebaseTypeSystem ts = (CodebaseTypeSystem) tc.typeSystem();
      if (im.kind() == Import.PACKAGE && ts.packageExists(ns , im.name())) {
          return im;
      }

      // Must be importing a class, either as p.C, or as p.C.*

      // The first component of the type name must be a package.
      String pkgName = StringUtil.getFirstComponent(im.name());

      if (! ts.packageExists(ns, pkgName)) {
          throw new SemanticException("Package \"" + pkgName +
              "\" not found.", im.position());
      }

      // The type must exist.
      Named nt = ts.forName(ns, im.name());

      // And the type must be accessible.
      if (nt instanceof Type) {
          Type t = (Type) nt;
          if (t.isClass()) {
              tc.typeSystem().classAccessibleFromPackage(t.toClass(),
                  tc.context().package_());
          }
      }

      return im;
  }  
}
