package fabric.types;

import jif.types.JifContext_c;
import jif.types.JifTypeSystem;
import polyglot.types.Context;
import polyglot.types.Context_c;
import polyglot.types.ImportTable;
import polyglot.types.LocalInstance;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.types.VarInstance;
import fabil.frontend.CodebaseSource;
import fabil.types.CodebaseImportTable_c;
import fabil.types.CodebaseTypeSystem;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;

public class FabricContext_c extends JifContext_c implements FabricContext {

  protected CodebaseSource source;

  protected FabricContext_c(JifTypeSystem ts, TypeSystem jlts) {
    super(ts, jlts);
  }

  @Override
  protected Context_c push() {
    FabricContext_c v = (FabricContext_c) super.push();
    v.source = source;
    return v;
  }

  public Codebase currentCodebase() {
    return source.codebase();
  }

  public CodebaseSource currentSource() {
    return source;
  }

  @Override
  public Context pushSource(ImportTable it) {
    FabricContext_c v = (FabricContext_c) super.pushSource(it);
    v.source = ((CodebaseImportTable_c) it).source();
    return v;
  }

  /**
   * Finds the definition of a particular type.
   */
  @Override
  public Named find(String name) throws SemanticException {
    if (isOuter()) {
      CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;
      if (cbts.localTypesOnly() || cbts.isPlatformType(name)) {
        return super.find(name);
      } 
      else {
        FClass fclass = currentCodebase().resolveClassName(name);
        if (fclass == null) {
          // For local source, build codebase lazily
          if (!source.isRemote()) {
            Named n = super.find(name);
            cbts.addRemoteFClass(currentCodebase(), n);
            return n;
          }
          return null;
        }
        
        String prefix = SysUtil.codebasePrefix(fclass.getCodebase());
        return super.find(prefix + name);
      }
    }
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
