package fabric.types;

import polyglot.types.*;
import polyglot.types.Package;

public class FabricImportTable extends ImportTable {

  public FabricImportTable(TypeSystem ts, Package pkg, String src) {
    super(ts, pkg, src);
  }

  public FabricImportTable(TypeSystem ts, Package pkg) {
    super(ts, pkg);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.types.ImportTable#findInPkg(java.lang.String,
   *      java.lang.String)
   */
  @Override
  protected Named findInPkg(String name, String pkgName)
      throws SemanticException {
    // HACK Ignore java.lang.Object so that fabric.lang.Object takes priority.
    if ("Object".equals(name) && "java.lang".equals(pkgName)) return null;
    
    return super.findInPkg(name, pkgName);
  }

}
