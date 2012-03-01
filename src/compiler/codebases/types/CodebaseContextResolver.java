package codebases.types;

import polyglot.types.Package;
import polyglot.types.PackageContextResolver;
import polyglot.types.TypeSystem;

public class CodebaseContextResolver extends PackageContextResolver {

  public CodebaseContextResolver(TypeSystem ts, Package p) {
    super(ts, p);
  }

}
