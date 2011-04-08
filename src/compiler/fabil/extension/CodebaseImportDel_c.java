package fabil.extension;

import java.net.URI;

import polyglot.ast.Import;
import polyglot.ast.JL_c;
import polyglot.ast.Node;
import polyglot.types.Named;
import polyglot.types.NoClassException;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.StringUtil;
import polyglot.visit.TypeChecker;
import fabil.frontend.CodebaseSource;
import fabil.types.CodebaseContext;
import fabil.types.CodebaseTypeSystem;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;

public class CodebaseImportDel_c extends JL_c {

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
    Named nt = null;
    if (ts.localTypesOnly() || uri.isAbsolute() 
        || ts.isPlatformType(name)) {
      nt = ts.forName(name);
    } else {
      Codebase codebase = context.currentCodebase();
      CodebaseSource source = context.currentSource();
      FClass fclass = codebase.resolveClassName(name);
      if (fclass == null) {
        // For local source, build codebase lazily
        // -- this probably should only miss for
        //    unused imports...
        if (!source.isRemote()) {
          nt = ts.forName(name);
          ts.addRemoteFClass(codebase, nt);          
        }
      } 
      else {
        String prefix = SysUtil.codebasePrefix(fclass.getCodebase());
        nt = ts.forName(prefix + name);
      }
    }

    // And the type must be accessible.
    if (nt instanceof Type) {
      Type t = (Type) nt;
      if (t.isClass()) {
        tc.typeSystem().classAccessibleFromPackage(t.toClass(),
            tc.context().package_());
      }
    }
    if (nt == null) throw new NoClassException(name);
    
    return n;
  }
}
