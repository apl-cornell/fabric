package fabric.types;

import java.net.URI;
import java.util.Collection;

import codebases.types.CBImportTable;
import codebases.types.CodebaseTypeSystem;

import jif.types.JifContext_c;
import jif.types.JifTypeSystem;
import jif.types.label.Label;

import polyglot.ast.Expr;
import polyglot.main.Report;
import polyglot.types.CodeInstance;
import polyglot.types.Context;
import polyglot.types.Context_c;
import polyglot.types.LocalInstance;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.types.VarInstance;
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

public class FabricContext_c extends JifContext_c implements FabricContext {
  private static final Collection<String> TOPICS = CollectionUtil.list(
      Report.types, Report.context);

  protected Expr location;

  protected Label conflictLab; //meet of conflict labels of accesses up to this point.

  protected Label beginConflictBound; //Begin conflict label of current method.

  protected Label endConflictBound; //End conflict label of the current method.

  @Override
  public Label conflictLabel() {
    return conflictLab;
  }

  @Override
  public Label beginConflictBound() {
    return beginConflictBound;
  }

  @Override
  public Label endConflictBound() {
    return endConflictBound;
  }

  @Override
  public void setConflictLabel(Label conflictLab) {
    this.conflictLab = conflictLab;
    // Bit of a hack to get the conflict label to propogate as far up as the
    // code level.
    FabricContext_c cur = this;
    while (cur != null && !cur.isCode()) {
      cur = (FabricContext_c) cur.outer;
      if (cur != null) {
        cur.conflictLab = conflictLab;
      }
    }
  }

  @Override
  public void setBeginConflictBound(Label conflictLab) {
    this.beginConflictBound = conflictLab;
  }

  @Override
  public void setEndConflictBound(Label endConflict) {
    this.endConflictBound = endConflict;
  }

  protected FabricContext_c(JifTypeSystem ts, TypeSystem jlts) {
    super(ts, jlts);
    Position cg = Position.compilerGenerated();
    this.conflictLab = ts.topLabel(cg);
    this.beginConflictBound = ts.topLabel(cg);
    this.endConflictBound = ts.pairLabel(cg, ts.bottomConfPolicy(cg), ts.topIntegPolicy(cg));
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
