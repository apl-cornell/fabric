package fabil.types;

import fabil.Codebases;
import fabric.common.Util;
import polyglot.frontend.Source;
import polyglot.types.*;
import polyglot.types.Package;

public class FabILImportTable extends ImportTable {
  protected String codebasePrefix;
  
  public FabILImportTable(TypeSystem ts, Package pkg, Source src) {
    super(ts, pkg, src.name());
    this.codebasePrefix = Util.codebasePrefix(((Codebases) src).codebase());
  }

  public FabILImportTable(TypeSystem ts, Package pkg) {
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
    
    //If we are looking in the package of this import table, 
    // qualify the name by codebase
    if(pkg.fullName().equals(pkgName))
      return super.findInPkg(name, codebasePrefix + pkgName);
    else
      return super.findInPkg(name, pkgName);
  }

}
