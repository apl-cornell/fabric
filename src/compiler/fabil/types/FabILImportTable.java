package fabil.types;

import fabil.Codebases;
import fabric.common.SysUtil;
import polyglot.frontend.Source;
import polyglot.types.*;
import polyglot.types.Package;

public class FabILImportTable extends ImportTable {
  protected String codebasePrefix;
  protected Source source;
  public FabILImportTable(TypeSystem ts, Package pkg, Source source) {
    super(ts, pkg, source.name());
    this.source = source;
    this.codebasePrefix = SysUtil.codebasePrefix(((Codebases) source).codebase());
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
    
    Named n = super.findInPkg(name, codebasePrefix + pkgName);
    if(n != null)
      return n;
    else
      return super.findInPkg(name, pkgName);
  }

}
