package codebases.types;

import fabric.common.FabricLocation;
import polyglot.types.Package;
import polyglot.types.Package_c;
import polyglot.types.Resolver;
import polyglot.types.TypeSystem;

public class CBPackage_c extends Package_c implements CBPackage {
  protected FabricLocation namespace;

  /** Used for deserializing types. */
  protected CBPackage_c() {
  }

  public CBPackage_c(TypeSystem ts, FabricLocation ns) {
    this(ts, ns, null, null);
  }

  public CBPackage_c(TypeSystem ts, FabricLocation ns, String name) {
    this(ts, ns, null, name);
  }

  public CBPackage_c(TypeSystem ts, FabricLocation ns, Package prefix,
      String name) {
    super(ts, prefix, name);
    this.namespace = ns;
  }

  @Override
  public Resolver resolver() {
    if (memberCache == null) {
      memberCache =
          ((CodebaseTypeSystem) ts).createPackageContextResolver(this);
    }
    return memberCache;
  }

  @Override
  public FabricLocation namespace() {
    return namespace;
  }

}
