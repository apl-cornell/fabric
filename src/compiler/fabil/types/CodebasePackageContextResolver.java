package fabil.types;

import polyglot.types.ClassType;
import polyglot.types.Named;
import polyglot.types.NoClassException;
import polyglot.types.PackageContextResolver;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.util.StringUtil;
import fabil.frontend.CodebaseSource;
import fabil.frontend.RemoteSource;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;

public class CodebasePackageContextResolver extends PackageContextResolver {

  public CodebasePackageContextResolver(TypeSystem ts, CodebasePackage p) {
    super(ts, p);
  }

  /**
   * This resolver is used during disambiguation to resolve names
   * component-by-component. To support codebases, package instances contain 
   * a reference to the source they appear in. Given a package prefix, p, 
   * a short name is disambiguated as follows:
   *    p is a platform package or offline     --> SR
   *    otherwise                              --> CB
   *            if p appears in local source   --> SR, add to CB
   */
  @Override
  public Named find(String name, ClassType accessor) throws SemanticException {
    if (!StringUtil.isNameShort(name)) {
      throw new InternalCompilerError("Cannot lookup qualified name " + name);
    }
    Named n = null;
    CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;
    if (cbts.localTypesOnly() || SysUtil.isPlatformType(p)) 
      return super.find(name, accessor);

    CodebaseSource cs = ((CodebasePackage) p).source();
    Codebase cb = cs.codebase();

    String fullName = p.fullName() + "." + name;
    FClass fclass = cb.resolveClassName(fullName);
    if (fclass == null) {
      // For local source, build codebase lazily
      if(!cs.isRemote()) {
        n = super.find(name, accessor);
        cbts.addRemoteFClass(cb, n);
        return n;
      }
    }
    
    if(fclass != null) {
      String prefix = SysUtil.codebasePrefix(fclass.getCodebase());
      try {
        n = ts.systemResolver().find(prefix + fullName);
      } catch (NoClassException e) {
        // Rethrow if some _other_ class or package was not found.
        if (!e.getClassName().equals(prefix + fullName)) {
          throw e;
        }
      }
    }
    
    if (n == null) {
      n = ts.createPackage(p, name);
    }

    if (!canAccess(n, accessor)) {
      throw new SemanticException("Cannot access " + n + " from " + accessor
          + ".");
    }

    return n;
  }

}
