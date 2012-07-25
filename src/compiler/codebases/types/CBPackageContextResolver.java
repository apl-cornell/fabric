package codebases.types;

import polyglot.types.ClassType;
import polyglot.types.Named;
import polyglot.types.NoClassException;
import polyglot.types.Package;
import polyglot.types.PackageContextResolver;
import polyglot.types.Resolver;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.StringUtil;
import fabric.common.FabricLocation;

public class CBPackageContextResolver extends PackageContextResolver {
  protected FabricLocation ns;

  public CBPackageContextResolver(CodebaseTypeSystem ts, Package p) {
    super(ts, p);
    this.ns = ((CBPackage) p).namespace();
  }

  /**
   * The system resolver.
   */
  @Override
  public Resolver outer() {
    return ((CodebaseTypeSystem) ts).namespaceResolver(ns);
  }

  /**
   * Find a type object by name.
   */
  @Override
  public Named find(String name, ClassType accessor) throws SemanticException {
    if (!StringUtil.isNameShort(name)) {
      throw new InternalCompilerError("Cannot lookup qualified name " + name);
    }

    Named n = null;

    try {
      n =
          ((CodebaseTypeSystem) ts).namespaceResolver(ns).find(
              p.fullName() + "." + name);
    } catch (NoClassException e) {
      // Rethrow if some _other_ class or package was not found.
      if (!e.getClassName().equals(p.fullName() + "." + name)) {
        throw e;
      }
    }

    if (n == null) {
      n = ((CodebaseTypeSystem) ts).createPackage(ns, p, name);
    }

    if (!canAccess(n, accessor)) {
      throw new SemanticException("Cannot access " + n + " from " + accessor
          + ".");
    }

    return n;
  }
}
