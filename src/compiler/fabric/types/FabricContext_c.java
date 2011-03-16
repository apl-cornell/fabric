package fabric.types;

import jif.types.JifContext_c;
import jif.types.JifTypeSystem;
import polyglot.frontend.Source;
import polyglot.types.Context;
import polyglot.types.Context_c;
import polyglot.types.ImportTable;
import polyglot.types.LocalInstance;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.types.VarInstance;
import fabil.frontend.CodebaseSource;
import fabil.types.CodebaseContext;
import fabil.types.CodebaseImportTable_c;
import fabil.types.CodebaseTypeSystem;
import fabric.lang.Codebase;

public class FabricContext_c extends JifContext_c implements FabricContext {

  protected Codebase codebase;
  protected Source source;

  protected FabricContext_c(JifTypeSystem ts, TypeSystem jlts) {
    super(ts, jlts);
    this.codebase = null;
  }

  @Override
  protected Context_c push() {
    FabricContext_c v = (FabricContext_c) super.push();
    v.codebase = codebase;
    v.source = source;
    return v;
  }

  public Codebase currentCodebase() {
    return codebase;
  }

  /**
   * Push a codebase scope.
   */
  public CodebaseContext pushCodebase(Codebase codebase) {
    FabricContext_c v = (FabricContext_c) push();
    v.kind = OUTER;
    v.codebase = codebase;
    v.inCode = false;
    v.staticContext = false;
    return v;
  }

  public CodebaseSource currentSource() {
    return (CodebaseSource) source;
  }

  public CodebaseContext pushSource(CodebaseSource source) {
    FabricContext_c v = (FabricContext_c) push();
    v.kind = OUTER;
    v.source = (Source) source;
    return v;
  }

  @Override
  public Context pushSource(ImportTable it) {
    FabricContext_c v = (FabricContext_c) super.pushSource(it);
    v.source = (Source) ((CodebaseImportTable_c) it).source();
    return v;
  }

  /**
   * Finds the definition of a particular type.
   */
  @Override
  public Named find(String name) throws SemanticException {
    if (isOuter())
      return ts.systemResolver().find(
          ((CodebaseTypeSystem) ts).absoluteName(codebase, name,
              ((CodebaseSource) source).isRemote()));
    else return super.find(name);
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
  public VarInstance findVariableSilent(String name) {
    return super.findVariableSilent(name);
  }
  
}
