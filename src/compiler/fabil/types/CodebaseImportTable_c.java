package fabil.types;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import fabil.frontend.CodebaseSource;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import polyglot.main.Report;
import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.util.StringUtil;

public class CodebaseImportTable_c extends ImportTable implements
    CodebaseImportTable {
  private static final Object NOT_FOUND = "NOT FOUND";

  protected Codebase codebase;
  protected CodebaseSource source;

  public CodebaseImportTable_c(TypeSystem ts, Package pkg, CodebaseSource source) {
    super(ts, pkg, source.name());
    this.source = source;
    this.codebase = source.codebase();
  }

  public CodebaseImportTable_c(TypeSystem ts, Package pkg) {
    super(ts, pkg);
  }

  /**
   * Find a type by name, searching the import table.
   */
  @Override
  public Named find(String name) throws SemanticException {
    if (Report.should_report(TOPICS, 2))
      Report.report(2, this + ".find(" + name + ")");

    /* First add any lazy imports. */
    lazyImport();
    CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;
    if (!StringUtil.isNameShort(name)) {
      // The name was long.
      return ts.systemResolver().find(
          cbts.absoluteName(codebase, name, source.isRemote()));
    }
    // The class name is short.
    // First see if we have a mapping already.
    Object res = map.get(name);

    if (res != null) {
      if (res == NOT_FOUND) {
        throw new NoClassException(name, sourcePos);
      }
      return (Named) res;
    }

    try {
      if (pkg != null) {
        // check if the current package defines it.
        // If so, this takes priority over the package imports (or
        // "type-import-on-demand" declarations as they are called in
        // the JLS), so even if another package defines the same name,
        // there is no conflict. See Section 6.5.2 of JLS, 2nd Ed.
        Named n = findInPkg(name, pkg.fullName());
        if (n != null) {
          if (Report.should_report(TOPICS, 3))
            Report.report(3, this + ".find(" + name
                + "): found in current package");

          // Memoize the result.
          map.put(name, n);
          return n;
        }
      }

      List imports = new ArrayList(packageImports.size() + 5);

      imports.addAll(ts.defaultPackageImports());
      imports.addAll(packageImports);

      // It wasn't a ClassImport. Maybe it was a PackageImport?
      Named resolved = null;
      for (Iterator iter = imports.iterator(); iter.hasNext();) {
        String pkgName = (String) iter.next();
        Named n = findInPkg(name, pkgName);
        if (n != null) {
          if (resolved == null) {
            // This is the first occurrence of name we've found
            // in a package import.
            // Record it, and keep going, to see if there
            // are any conflicts.
            resolved = n;
          } else {
            // This is the 2nd occurrence of name we've found
            // in an imported package.
            // That's bad.
            throw new SemanticException("Reference to \"" + name
                + "\" is ambiguous; both " + resolved.fullName() + " and "
                + n.fullName() + " match.");
          }
        }
      }

      if (resolved == null) {
        // The name was short, but not in any imported class or package.
        // Check the null package.
        if (source.isRemote()) {
          FClass fclass = codebase.resolveClassName(name);
          if (fclass != null)
            resolved =
                ts.systemResolver().find(
                    cbts.absoluteName(codebase, name, true));
        } else resolved = ts.systemResolver().find(name);

        if (!isVisibleFrom(resolved, "")) {
          // Not visible.
          throw new NoClassException(name, sourcePos);
        }
      }

      // Memoize the result.
      if (Report.should_report(TOPICS, 3))
        Report.report(3,
            this + ".find(" + name + "): found as " + resolved.fullName());
      map.put(name, resolved);
      return resolved;
    } catch (NoClassException e) {
      // memoize the no class exception
      if (Report.should_report(TOPICS, 3))
        Report.report(3, this + ".find(" + name + "): didn't find it");
      map.put(name, NOT_FOUND);
      throw e;
    }
  }

  @Override
  protected Named cachedFind(String name) throws SemanticException {
    CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;
      
    URI uri = URI.create(name);
    if (uri.isAbsolute() || cbts.isPlatformType(name) || !source.isRemote()) {
      return super.cachedFind(name);
    }
    
    FClass fclass = codebase.resolveClassName(name);
    if (fclass == null) {
      return null;
    }
    
    String prefix = SysUtil.codebasePrefix(fclass.getCodebase());
    return super.cachedFind(prefix + name);
  }

  @Override
  protected Named findInPkg(String name, String pkgName)
      throws SemanticException {
    // HACK Ignore java.lang.Object so that fabric.lang.Object takes priority.
    if ("Object".equals(name) && "java.lang".equals(pkgName)) return null;

    CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;
    // Platform types and local source may use unqualified names for resolution
    // at the fabric layer
    if (cbts.isPlatformType(pkgName) || !source.isRemote()) {
      return super.findInPkg(name, pkgName);
    } else {
      FClass fcls = codebase.resolveClassName(pkgName + "." + name);
      if (fcls == null) return null;
      String prefix = SysUtil.codebasePrefix(fcls.getCodebase());
      return super.findInPkg(name, prefix + pkgName);
    }
  }

  @Override
  protected boolean isVisibleFrom(Named n, String pkgName) {
    // Codebases do not affect package visibility.
    URI uri = URI.create(pkgName);
    if (n instanceof CodebaseClassType && uri.isAbsolute()) {
      Codebase cb = ((CodebaseClassType) n).codebase();
      String cbPart = SysUtil.codebasePrefix(cb);
      boolean b = super.isVisibleFrom(n, pkgName.substring(cbPart.length()));
      return b;
    } else {
      boolean b = super.isVisibleFrom(n, pkgName);
      return b;
    }
  }

  @Override
  protected void lazyImport() throws SemanticException {
    // XXX Basically copied from super class.
    if (lazyImports.isEmpty()) {
      return;
    }

    CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;

    for (int i = 0; i < lazyImports.size(); i++) {
      String longName = (String) lazyImports.get(i);

      try {
        if (Report.should_report(TOPICS, 2))
          Report.report(2, this + ": import " + longName);

        URI uri = URI.create(longName);
        Named t;
        if (uri.isAbsolute() || cbts.isPlatformType(longName)
            || !source.isRemote()) {
          t = ts.systemResolver().find(longName);
        } else {
          FClass fcls = codebase.resolveClassName(longName);
          if (fcls == null) {
            throw new SemanticException("Codebase " + codebase
                + " has no entry for " + longName);
          }
          String prefix = SysUtil.codebasePrefix(fcls.getCodebase());
          t = ts.systemResolver().find(prefix + longName);
        }

        String shortName = StringUtil.getShortNameComponent(longName);

        map.put(shortName, t);

      } catch (SemanticException e) {
        Position pos = e.position();
        if (pos == null) pos = (Position) lazyImportPositions.get(i);
        if (pos == null) pos = sourcePos;
        throw new SemanticException(pos, e);
      }
    }

    lazyImports = new ArrayList();
    lazyImportPositions = new ArrayList();
  }

  public CodebaseSource source() {
    return source;
  }

  protected static final Collection TOPICS = CollectionUtil.list(Report.types,
      Report.resolver, Report.imports);

}
