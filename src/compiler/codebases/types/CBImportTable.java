/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package codebases.types;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import polyglot.frontend.Source;
import polyglot.main.Report;
import polyglot.types.ImportTable;
import polyglot.types.Importable;
import polyglot.types.Named;
import polyglot.types.NoClassException;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.StringUtil;
import codebases.frontend.CBJobExt;

public class CBImportTable extends ImportTable {
  // NB: this field 'hides' a private superclass field that is used for the same
  // purpose. However, we override all code that uses the superclass field, so
  // we
  // don't depend on these values being equal.

  private final CodebaseTypeSystem ts;

  protected final URI ns;
  protected final Set<String> aliases;
  protected final Map<String, String> fromExternal;
  protected final CBJobExt jobExt;

  public CBImportTable(CodebaseTypeSystem ts, URI ns, Package pkg, Source source) {
    super(ts, pkg, source.name());
    this.ts = ts;
    this.ns = ns;
    this.aliases = new HashSet<String>();
    this.fromExternal = new HashMap<String, String>();
    //XXX: this is a little sleazy.
    this.jobExt = (CBJobExt) ts.extensionInfo().scheduler().currentJob().ext();
  }

  void addLookup(String name, Named type) {
    map.put(name, type);
    if (type == NOT_FOUND) return;

    if (isExternal(name)) {
      jobExt.addExternalDependency((CodebaseClassType) type, aliasFor(name));
    } else {
      jobExt.addDependency((CodebaseClassType) type);
    }
  }

  // /// The following methods are basically copied from the superclass, but
  // instead of
  // /// calling the toplevel system resolver directly, they use the namespace
  // FabricLocation
  @Override
  protected Named cachedFind(String name) throws SemanticException {
    Object res = map.get(name);

    if (res != null) {
      return (Named) res;
    }

    Named t = ts.namespaceResolver(ns).find(name);
    addLookup(name, t);
    return t;
  }

  @Override
  public Named find(String name) throws SemanticException {
    if (Report.should_report(TOPICS, 2))
      Report.report(2, this + ".find(" + name + ")");

    /* First add any lazy imports. */
    lazyImport();

    if (!StringUtil.isNameShort(name)) {
      // The name was long.
      return ts.namespaceResolver(ns).find(name);
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
          addLookup(name, n);
          return n;
        }
      }

      List<String> imports = new ArrayList<String>(packageImports.size() + 5);

      imports.addAll(ts.defaultPackageImports());
      imports.addAll(packageImports);

      // It wasn't a ClassImport. Maybe it was a PackageImport?
      Named resolved = null;
      for (String pkgName : imports) {
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
        resolved = ts.namespaceResolver(ns).find(name); // may throw exception

        if (!isVisibleFrom(resolved, "")) {
          // Not visible.
          throw new NoClassException(name, sourcePos);
        }
      }

      // Memoize the result.
      if (Report.should_report(TOPICS, 3))
        Report.report(3,
            this + ".find(" + name + "): found as " + resolved.fullName());
      addLookup(name, resolved);
      return resolved;
    } catch (NoClassException e) {
      // memoize the no class exception
      if (Report.should_report(TOPICS, 3))
        Report.report(3, this + ".find(" + name + "): didn't find it");
      addLookup(name, NOT_FOUND);
      throw e;
    }
  }

  /**
   * Add a package import.
   */
  @Override
  public void addPackageImport(String pkgName) {
    // don't add the import if it is a
    String first = StringUtil.getFirstComponent(pkgName);
    if (aliases.contains(first)) {
      throw new InternalCompilerError(
          "Package imports with explicit codebases not yet supported");
    } else {
      super.addPackageImport(pkgName);
    }
  }

  @Override
  protected Named findInPkg(String name, String pkgName)
      throws SemanticException {
    // HACK Ignore java.lang.Object so that fabric.lang.Object takes priority.
    if ("Object".equals(name) && "java.lang".equals(pkgName)) return null;

    String fullName = pkgName + "." + name;
    if (Report.should_report(TOPICS, 2))
      Report.report(2, this + ": findInPkg import " + fullName);

    try {
      Named n = ts.namespaceResolver(ns).find(fullName);

      // Check if the type is visible in this package.
      if (isVisibleFrom(n, pkgName)) {
        return n;
      }
    } catch (NoClassException ex) {
      // Do nothing.
    }
    return null;
  }

  @Override
  protected void lazyImport() throws SemanticException {
    if (lazyImports.isEmpty()) {
      return;
    }

    for (int i = 0; i < lazyImports.size(); i++) {
      try {
        String longName = lazyImports.get(i);
        URI import_ns = ns;
        // Check if this is an explicit codebase import
        String first = StringUtil.getFirstComponent(longName);
        if (aliases.contains(first)) {
          URI u = ts.namespaceResolver(ns).resolveCodebaseName(first);
          if (u == null)
            throw new SemanticException("Unknown codebase \"" + first + "\"");
          import_ns = u;
          longName = StringUtil.removeFirstComponent(longName);
          if (Report.should_report(TOPICS, 2))
            Report.report(2, this + ": importing from external namespace "
                + import_ns);
        }

        if (Report.should_report(TOPICS, 2))
          Report.report(2, this + ": import " + longName);

        Importable t = ts.namespaceResolver(import_ns).find(longName);

        String shortName = StringUtil.getShortNameComponent(longName);
        if (!import_ns.equals(ns)) {
          fromExternal.put(shortName, first);
          jobExt.addExternalDependency((CodebaseClassType) t, first);
        } else {
          jobExt.addDependency((CodebaseClassType) t);
        }

        addLookup(shortName, t);
      } catch (SemanticException e) {
        if (e.position() == null) {
          Position p = lazyImportPositions.get(i);
          if (p == null) p = sourcePos;
          e = new SemanticException(p, e);
        }
        throw e;
      }
    }

    lazyImports = new ArrayList<String>();
    lazyImportPositions = new ArrayList<Position>();
  }

  protected static final Collection<String> TOPICS = CollectionUtil.list(
      Report.types, Report.resolver, Report.imports);

  public URI namespace() {
    return ns;
  }

  public void addCodebaseName(String name, Position position) {
    aliases.add(name);
  }

  public boolean isExternal(String name) {
    if (StringUtil.isNameShort(name))
      return fromExternal.containsKey(name);
    else return aliases.contains(StringUtil.getFirstComponent(name));
  }

  /**
   * Returns the alias used to load type name, or null if name was not loaded
   * via a codebase alias.
   * 
   * @param name
   * @return
   */
  public String aliasFor(String name) {
    String alias;
    if (StringUtil.isNameShort(name))
      alias = fromExternal.get(name);
    else alias = StringUtil.getFirstComponent(name);
    if (aliases.contains(alias))
      return alias;
    else return null;
  }

  public URI resolveCodebaseName(String name) {
    // Only resolve codebase names that were declared in this
    // sourcefile.
    // if (aliases.contains(name)) XXX: ?
    return ts.namespaceResolver(ns).resolveCodebaseName(name);
  }
}
