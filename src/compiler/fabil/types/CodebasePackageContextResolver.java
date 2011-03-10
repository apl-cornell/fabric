package fabil.types;

import fabil.Codebases;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import polyglot.types.ClassType;
import polyglot.types.Named;
import polyglot.types.NoClassException;
import polyglot.types.Package;
import polyglot.types.PackageContextResolver;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.util.StringUtil;

public class CodebasePackageContextResolver extends PackageContextResolver {

  public CodebasePackageContextResolver(TypeSystem ts, Package p) {
    super(ts, p);
  }

  /**
   * Find a type object by name.
   */
  @Override
  public Named find(String name, ClassType accessor) throws SemanticException {
      if (! StringUtil.isNameShort(name)) {
          throw new InternalCompilerError(
              "Cannot lookup qualified name " + name);
      }
      
      Named n = null;
      String fqName;
      if(accessor instanceof Codebases) {
        Codebase cb = ((Codebases) accessor).codebase();
        fqName = SysUtil.codebasePrefix(cb) + p.fullName() + "." + name;
      }
      else
        fqName = p.fullName() + "." + name;
      
      try {
          System.out.println("PKG CONTEXT: " + fqName + ": classtype: " + accessor.getClass());
            n = ts.systemResolver().find(fqName);
      }
      catch (NoClassException e) {
          // Rethrow if some _other_ class or package was not found.
          if (!e.getClassName().equals(fqName)) {
              throw e;
          }
      }

      if (n == null) {
        System.out.println("creating package: " + p);
          n = ts.createPackage(p, name);
      }
      
      if (! canAccess(n, accessor)) {
          throw new SemanticException("Cannot access " + n + " from " + accessor + ".");
      }
      
      return n;
  }

}
