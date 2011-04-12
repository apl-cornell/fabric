package fabil.types;

import polyglot.types.Context;
import polyglot.types.Context_c;
import polyglot.types.ImportTable;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import fabil.frontend.CodebaseSource;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;

/**
 * Codebase support for the FabIL typesystem. This class duplicates some of the
 * code in FabricContext_c, but enables FabIL classes to use codebase-relative
 * names.
 */
public class FabILContext_c extends Context_c implements FabILContext {

  protected CodebaseSource source;

  protected FabILContext_c(TypeSystem ts) {
    super(ts);
  }

  @Override
  protected Context_c push() {
    FabILContext_c v = (FabILContext_c) super.push();
    v.source = source;
    return v;
  }

  public Codebase currentCodebase() {
    return source.codebase();
  }

  /**
   * Return the current source
   */
  public CodebaseSource currentSource() {
    return source;
  }

  @Override
  public Context pushSource(ImportTable it) {
    FabILContext_c v = (FabILContext_c) super.pushSource(it);
    v.source = ((CodebaseImportTable) it).source();
    return v;
  }

  /**
   * Finds the definition of a particular type.
   */
  @Override
  public Named find(String name) throws SemanticException {
    if (isOuter()) {
      CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;
      if (cbts.localTypesOnly() || SysUtil.isPlatformType(name)) {
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
}
