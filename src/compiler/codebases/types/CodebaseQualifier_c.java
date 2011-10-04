package codebases.types;

import java.net.URI;

import polyglot.types.Package;
import polyglot.types.Package_c;
import polyglot.types.Resolver;
import polyglot.types.TypeSystem;
import fabric.lang.Codebase;


public class CodebaseQualifier_c extends Package_c implements CodebaseQualifier {

  protected URI uri;
  protected Codebase codebase;

  /** Used for deserializing types. */
  protected CodebaseQualifier_c() {
  }

  public CodebaseQualifier_c(TypeSystem ts, Codebase codebase) {
    this(ts, null, null, codebase);
  }

  public CodebaseQualifier_c(TypeSystem ts, String name, Codebase codebase) {
    this(ts, null, name, codebase);
  }

  public CodebaseQualifier_c(TypeSystem ts, Package prefix, String name,
      Codebase codebase) {
    super(ts, prefix, name);
    this.codebase = codebase;
    this.uri =
        URI.create("fab://" + codebase.$getStore().name() + "/"
            + codebase.$getOnum());
  }

  @Override
  public Codebase codebase() {
    return codebase;
  }

  @Override
  public Resolver resolver() {
    return ((CodebaseTypeSystem)ts).namespaceResolver(uri);
  }

  @Override
  public boolean packageEqualsImpl(Package p) {
    if (name.equals(p.name())) {
      if (prefix == null)
        return p.prefix() == null;
      else return ts.packageEquals(prefix, p.prefix());
    }
    return false;
  }

  @Override
  public String fullName() {
    if (fullname == null) {
      fullname = prefix() == null ? name : prefix().fullName() + "." + name;
    }
    return fullname;
  }

}
