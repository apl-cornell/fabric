package fabric.types;

import java.util.Collection;

import jif.types.JifContext_c;
import jif.types.JifTypeSystem;
import polyglot.ast.Expr;
import polyglot.main.Report;
import polyglot.types.Context;
import polyglot.types.LocalInstance;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import codebases.types.CBImportTable;
import codebases.types.CodebaseTypeSystem;
import fabric.common.FabricLocation;

public class FabricContext_c extends JifContext_c implements FabricContext {
  private static final Collection<String> TOPICS = CollectionUtil.list(
      Report.types, Report.context);

  protected Expr location;
  protected FabricLocation namespace;

  protected FabricContext_c(JifTypeSystem ts, TypeSystem jlts) {
    super(ts, jlts);
  }


//  @Override
//  protected VarInstance findStaticPrincipal(String name) {
//    // Principals are masquerading as classes.   Find the class
//    // and pull the principal out of the class.  Ick.
//    //
//    // Basically copied from super class and adapted to use
//    // namespaces.  Double Ick.
//    ClassType principal;
//    FabricTypeSystem fabts = (FabricTypeSystem) ts;
//    FabILTypeSystem filts = (FabILTypeSystem) jlts;
//    Resolver fabPlatform = filts.platformResolver();
//
//    try {
//      principal = (ClassType) fabPlatform.find(jifts.PrincipalClassName());
//    }
//    catch (SemanticException e) {
//      throw new InternalCompilerError("Cannot find " + jifts.PrincipalClassName() + " class.", e);
//    }
//
//    Named n;
//    try {
//      // Look for the principal only in class files.
//      String className = "jif.principals." + name;
//      n = jlts.loadedResolver().find(className);
//    } catch (SemanticException e) {
//      return null;
//    }
//
//    if (n instanceof Type) {
//      Type t = (Type) n;
//      if (t.isClass()) {
//
//        if (jlts.isSubtype(t.toClass(), principal)) {
//          return jifts.principalInstance(null,
//              jifts.externalPrincipal(null, name));
//        }
//      }
//    }
//    return null;
//
//  }


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
  public FabricLocation namespace() {
    if (isOuter()) throw new InternalCompilerError("No namespace!");
    return ((CBImportTable) it).namespace();
  }

  @Override
  public FabricLocation resolveCodebaseName(String name) {
    return ((CBImportTable) it).resolveCodebaseName(name);
  }

}
