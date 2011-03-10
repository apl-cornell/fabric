package fabil.types;

import java.net.URI;

import fabil.Codebases;
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
      ClassType ct = (ClassType) q;

      if(!q.fullName().equals(name)) {
        throw new InternalCompilerError("All system-level class types must be fully qualified : " + q.fullName() + " vs " + name);
      }

      String containerName;
      if(!ts.isPlatformType(ct)) {
        Codebase cb = ((Codebases) ct).codebase();

        String fqname;
        if(uri.isAbsolute()) {
          fqname = name;
          containerName = name.substring(SysUtil.codebasePrefix(cb).length());
        }
        else {
          fqname = SysUtil.codebasePrefix(cb)+name;
          containerName = StringUtil.getPackageComponent(name);
        }
        System.out.println("Adding " + fqname + " for type " + q);
        super.addNamed(fqname, q);
      }
      else {
        containerName = StringUtil.getPackageComponent(name);
        System.out.println("Adding " + name + " for platform type " + q);
        super.addNamed(name, q);
      }
      
      //Package names are *not* qualified by codebase
      if (ct.isTopLevel()) {
          Package p = ((ClassType) q).package_();
          cachePackage(p);
          if (p != null && containerName.equals(p.fullName())) {
            System.out.println("Adding container " + containerName + " for " + q);
              addNamed(containerName, p);
          }
      }
      else if (ct.isMember()) {
        throw new InternalCompilerError("Unexpected member class.");
      }
      return;
    }
    else {
      URI uri = URI.create(name);
      if(uri.isAbsolute()) {
        name = name.substring(name.lastIndexOf('/')+1);
      }      
      super.addNamed(name, q);    
    }
    System.out.println("Adding " + name + " for non-class type " + q);
  }
  /**
   * Check if a package exists.
   */
  @Override
  public boolean packageExists(String name) {
    //Packages are not qualified by codebases
    URI uri = URI.create(name);
    if(uri.isAbsolute()) {
      name = name.substring(name.lastIndexOf('/')+1);
    }      
    return super.packageExists(name);
  }
}
