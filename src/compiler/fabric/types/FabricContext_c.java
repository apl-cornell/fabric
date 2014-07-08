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
package fabric.types;

import java.net.URI;
import java.util.Collection;

import jif.types.JifContext_c;
import jif.types.JifTypeSystem;
import polyglot.ast.Expr;
import polyglot.main.Report;
import polyglot.types.Context;
import polyglot.types.LocalInstance;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.types.VarInstance;
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import codebases.types.CBImportTable;
import codebases.types.CodebaseTypeSystem;

public class FabricContext_c extends JifContext_c implements FabricContext {
  private static final Collection<String> TOPICS = CollectionUtil.list(
      Report.types, Report.context);

  protected Expr location;

  protected FabricContext_c(JifTypeSystem ts, TypeSystem jlts) {
    super(ts, jlts);
  }

  @Override
  protected VarInstance findStaticPrincipal(String name) {
    if (isOuter()) return null;
    // Principals are masquerading as classes.   Find the class
    // and pull the principal out of the class.  Ick.
    FabricTypeSystem ts = (FabricTypeSystem) this.ts;
    Named n;
    try {
      // Look for the principal only in class files.
      String className = "fabric.principals." + name;
      n = ts.namespaceResolver(namespace()).find(className);
    } catch (SemanticException e) {
      return null;
    }

    if (n instanceof Type) {
      Type t = (Type) n;
      if (t.isClass()) {
        if (ts.isSubtype(t.toClass(), ts.PrincipalClass())) {
          Position pos = Position.compilerGenerated();
          return ts.principalInstance(pos, ts.externalPrincipal(pos, name));
        }
      }
    }
    return null;
  }

  @Override
  public Named find(String name) throws SemanticException {
    if (Report.should_report(TOPICS, 3))
      Report.report(3, "find-type " + name + " in " + this);

    if (isOuter())
      return ((CodebaseTypeSystem) ts).namespaceResolver(namespace())
          .find(name);
    if (isSource()) return it.find(name);

    Named type = findInThisScope(name);

    if (type != null) {
      if (Report.should_report(TOPICS, 3))
        Report.report(3, "find " + name + " -> " + type);
      return type;
    }

    if (outer != null) {
      return outer.find(name);
    }

    throw new SemanticException("Type " + name + " not found.");
  }

  @Override
  public LocalInstance findLocal(String name) throws SemanticException {
    if (name.equals("worker$") || name.equals("worker$'")) {
      return ((FabricTypeSystem) typeSystem()).workerLocalInstance();
    } else if (name.endsWith("'")) {
      // XXX HACK!
      return super.findLocal(name.substring(0, name.length() - 1));
    }
    return super.findLocal(name);
  }

  @Override
  public Expr location() {
    return location;
  }

  @Override
  public Context pushLocation(Expr location) {
    FabricContext_c v = (FabricContext_c) push();
    v.location = location;
    return v;
  }

  @Override
  public URI namespace() {
    if (isOuter()) throw new InternalCompilerError("No namespace!");
    return ((CBImportTable) it).namespace();
  }

  @Override
  public URI resolveCodebaseName(String name) {
    return ((CBImportTable) it).resolveCodebaseName(name);
  }

}
