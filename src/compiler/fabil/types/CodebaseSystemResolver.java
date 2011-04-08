package fabil.types;

import java.net.URI;

import polyglot.frontend.ExtensionInfo;
import polyglot.types.ClassType;
import polyglot.types.Named;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.types.SystemResolver;
import polyglot.types.TopLevelResolver;
import polyglot.util.StringUtil;
import fabil.frontend.CodebaseSource;
import fabric.common.SysUtil;

public class CodebaseSystemResolver extends SystemResolver {

  CodebaseTypeSystem ts;

  public CodebaseSystemResolver(TopLevelResolver inner, ExtensionInfo extInfo) {
    super(inner, extInfo);
    ts = (CodebaseTypeSystem) extInfo.typeSystem();
  }

  @Override
  public void addNamed(String name, Named q) throws SemanticException {
    if (q instanceof ClassType) {
      CodebaseClassType ct = (CodebaseClassType) q;
      CodebaseSource cs = (CodebaseSource) ct.fromSource();
      URI uri = URI.create(name);
      if(uri.isAbsolute())
        throw new SemanticException("Did not expect absolute name: " + name);
      
      // in offline mode and for platform type, add long name (pkg.ClassName)
      if (ts.localTypesOnly() || ts.isPlatformType(ct)) {
        super.addNamed(name, q);
        
      // otherwise, add absolute name and long name
      } 
      else {
        super.addNamed(name, q);
        String prefix = SysUtil.codebasePrefix(cs.codebase());
        super.addNamed(prefix + name, q);
      }
      
      // Package names are *not* qualified by codebase
      String containerName = StringUtil.getPackageComponent(name);
      if (ct.isTopLevel()) {
        Package p = ((ClassType) q).package_();
        cachePackage(p);
        if (p != null && containerName.equals(p.fullName())) {
          super.addNamed(containerName, p);
        }
      } 
      else if (ct.isMember()) {
        if (name.equals(ct.fullName())) {
          addNamed(containerName, ct.outer());
        }
      }
    } 
    else 
      super.addNamed(name, q);
  }

  /**
   * Check if a package exists.
   */
  @Override
  public boolean packageExists(String name) {
    // Packages are not qualified by codebases
    URI uri = URI.create(name);
    if (uri.isAbsolute()) {
      name = name.substring(name.lastIndexOf('/') + 1);
    }
    return super.packageExists(name);
  }
}
