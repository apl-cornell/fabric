package fabil.types;

import java.net.URI;

import fabil.frontend.CodebaseSource;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import polyglot.types.*;
import polyglot.types.Package;

public class FabILImportTable extends ImportTable implements CodebaseImportTable {
  protected String codebasePrefix;
  protected CodebaseSource source;
  public FabILImportTable(TypeSystem ts, Package pkg, CodebaseSource source) {
    super(ts, pkg, source.name());
    this.source = source;
    this.codebasePrefix = SysUtil.codebasePrefix(source.codebase());
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
    //Platform types and local source may use unqualified names for resolution
    if(cbts.isPlatformType(pkgName) || !source.isRemote())
      return super.findInPkg(name, pkgName);
    else {
      return super.findInPkg(name, codebasePrefix + pkgName);
    }
  }

  @Override
  protected boolean isVisibleFrom(Named n, String pkgName) {
    //Codebases do not affect package visibility.
    URI uri = URI.create(pkgName);
    if (n instanceof CodebaseClassType && uri.isAbsolute()) {
      Codebase cb = ((CodebaseClassType) n).codebase();
      String cbPart = SysUtil.codebasePrefix(cb);
      boolean b = super.isVisibleFrom(n, pkgName.substring(cbPart.length()));
      return b;
    } else {
      boolean b = super.isVisibleFrom(n, pkgName);
      return b;
    }
  }
  
  public CodebaseSource source() {
    return source;
  }
}
