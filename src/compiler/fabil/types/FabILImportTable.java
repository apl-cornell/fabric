package fabil.types;

import fabil.frontend.CodebaseSource;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import polyglot.frontend.Source;
import polyglot.types.*;
import polyglot.types.Package;

public class FabILImportTable extends ImportTable implements CodebaseImportTable {
  protected String codebasePrefix;
  protected Source source;
  public FabILImportTable(TypeSystem ts, Package pkg, Source source) {
    super(ts, pkg, source.name());
    this.source = source;
    this.codebasePrefix = SysUtil.codebasePrefix(((CodebaseSource) source).codebase());
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

    CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;
    if(cbts.isPlatformType(pkgName))
      return super.findInPkg(name, pkgName);
    else
      return super.findInPkg(name, codebasePrefix + pkgName);
  }

  @Override
  protected boolean isVisibleFrom(Named n, String pkgName) {
    //Codebases do not affect package visibility.
    if (n instanceof CodebaseClassType) {
      Codebase cb = ((CodebaseClassType) n).codebase();
      String cbPart = SysUtil.codebasePrefix(cb);
      boolean b = super.isVisibleFrom(n, pkgName.substring(cbPart.length()));
      return b;
    } else {
      boolean b = super.isVisibleFrom(n, pkgName);
      return b;
    }
  }
}
