package fabil.types;

import java.net.URI;

import fabil.frontend.CodebaseSource;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import polyglot.frontend.ExtensionInfo;
import polyglot.types.ClassType;
import polyglot.types.Named;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.types.SystemResolver;
import polyglot.types.TopLevelResolver;
import polyglot.util.InternalCompilerError;
import polyglot.util.StringUtil;

public class CodebaseSystemResolver extends SystemResolver {

  CodebaseTypeSystem ts;

  public CodebaseSystemResolver(TopLevelResolver inner, ExtensionInfo extInfo) {
    super(inner, extInfo);
    ts = (CodebaseTypeSystem) extInfo.typeSystem();
  }

  @Override
  public void addNamed(String name, Named q) throws SemanticException {
    if (q instanceof ClassType) {
      URI uri = URI.create(name);
      CodebaseClassType ct = (CodebaseClassType) q;
      CodebaseSource cs = (CodebaseSource) ct.fromSource();
      String className = ct.fullName();

      String containerName;
      if (ts.isPlatformType(ct) || !cs.isRemote()) {
        containerName = StringUtil.getPackageComponent(className);
        super.addNamed(name, q);
        boolean remote = (cs == null) ? false : cs.isRemote();
        super.addNamed(ts.absoluteName(ct.codebase(), name, remote), ct);     
      } else {
        String fqname;
        if (uri.isAbsolute()) {
          fqname = name;
        } else {
          fqname = ts.absoluteName(ct.codebase(), name, true);
        }
        //remove the leading fabref for packages
        containerName = StringUtil.getPackageComponent(className);
        super.addNamed(fqname, q);
      }

      // Package names are *not* qualified by codebase
      if (ct.isTopLevel()) {
        Package p = ((ClassType) q).package_();
        cachePackage(p);
        if (p != null && containerName.equals(p.fullName())) {
          super.addNamed(containerName, p);
        }
      } else if (ct.isMember()) {
        if (name.equals(ct.fullName())) {
          // Check that the names match; we could be installing
          // a member class under its class file name, not its Java
          // source full name.
          addNamed(containerName, ct.outer());
        }
      }
      return;
    } else {
      URI uri = URI.create(name);
      if (uri.isAbsolute()) {
        name = name.substring(name.lastIndexOf('/') + 1);
      }
      super.addNamed(name, q);
    }
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
