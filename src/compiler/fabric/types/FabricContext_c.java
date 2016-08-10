package fabric.types;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import codebases.types.CBImportTable;
import codebases.types.CodebaseTypeSystem;

import jif.types.JifContext_c;
import jif.types.JifTypeSystem;
import jif.types.label.Label;

import polyglot.ast.Branch;
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

public class FabricContext_c extends JifContext_c implements FabricContext {
  private static final Collection<String> TOPICS = CollectionUtil.list(
      Report.types, Report.context);

  protected Expr location;

  protected Label conflictLab; //meet of conflict labels of accesses up to this point.

  protected Label beginConflictBound; //Begin conflict label of current method.

  protected Label endConflictBound; //End conflict label of the current method.

  /**
   * Map from JifContext_c.Key (pairs of Branch.Kind and String) to Labels.
   */
  protected Map<Key, Label> conflictGotos;

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

  /**
   * Stage started flag.
   */
  protected boolean stageStarted;

  /**
   * Do we need to stage the next access, regardless of what the current
   * conflict label is?
   *
   * This flag helps us handle issues with staging after something like an if
   * statement which might or might not have started the stage it finishes in.
   */
  @Override
  public boolean stageStarted() {
    return stageStarted;
  }

  /**
   * Update the stage started flag.  This only affects the current context (so
   * if we leave the current block, it will reset to false.
   */
  @Override
  public void setStageStarted(boolean flag) {
    stageStarted = flag;
  }

  @Override
  public void setConflictLabel(Label conflictLab) {
    this.conflictLab = conflictLab;
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
    this.conflictLab = ts.bottomLabel(cg);
    this.beginConflictBound = ts.bottomLabel(cg);
    this.endConflictBound = ts.noComponentsLabel();
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

  @Override
  public Label gotoConflictLabel(Branch.Kind kind, String label) {
    if (conflictGotos == null) return null;
    return conflictGotos.get(new Key(kind, label));
  }

  @Override
  public void gotoConflictLabel(Branch.Kind kind, String label, Label L) {
    if (conflictGotos == null) conflictGotos = new HashMap<>();
    conflictGotos.put(new Key(kind, label), L);
  }

  @Override
  public FabricContext_c copy() {
    FabricContext_c ctxt = (FabricContext_c) super.copy();
    if (conflictGotos != null) {
      ctxt.conflictGotos = new HashMap<>(conflictGotos);
    }
    return ctxt;
  }
}
