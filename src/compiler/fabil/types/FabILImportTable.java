package fabil.types;

import fabil.Codebases;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
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
    
    return super.findInPkg(name, codebasePrefix + pkgName);
  }

  @Override
  protected boolean isVisibleFrom(Named n, String pkgName) {
    //Codebases do not affect package visibility.
    if (n instanceof Codebases) {
      Codebase cb = ((Codebases) n).codebase();
      String cbPart = SysUtil.codebasePrefix(cb);
      return super.isVisibleFrom(n, pkgName.substring(cbPart.length()));
    } else {
      return super.isVisibleFrom(n, pkgName);
    }
  }
}
