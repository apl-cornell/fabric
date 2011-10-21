package codebases.types;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import polyglot.main.Report;
import polyglot.types.Importable;
import polyglot.types.LazyClassInitializer;
import polyglot.types.NoClassException;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import codebases.frontend.ExtensionInfo;
import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;
import fabric.worker.Store;

public class PathResolver extends NamespaceResolver_c implements
    NamespaceResolver {
  private static final Collection<String> TOPICS;
  static {
    TOPICS = new ArrayList<String>(2);
    TOPICS.add(Report.types);
    TOPICS.add(Report.resolver);
  }

  protected final List<NamespaceResolver> path;
  protected final Map<String, URI> aliases;
  protected boolean load_raw = false;
  protected boolean load_enc = false;
  protected boolean load_src = false;

  public PathResolver(ExtensionInfo extInfo, URI ns,
      List<NamespaceResolver> path) {
    this(extInfo, ns, path, Collections.<String, URI> emptyMap());
  }

  public PathResolver(ExtensionInfo extInfo, URI ns,
      List<NamespaceResolver> path, Map<String, URI> aliases) {
    super(extInfo, ns, null);
    if (path.contains(null))
      throw new NullPointerException("Null resolver in path!");
    this.path = path;
    this.aliases = aliases;
  }

  /**
   * Searches for a type for name in path in order. Following polyglot, there is
   * some subtlety to how resources are loaded from disk based on whether
   * source, encoded classes, or raw classes are permitted. * If loading source
   * and encoded classes:
   *
   * - The first source file or encoded class returned by a resolver the path
   *    will be loaded. If a source file and encoded class occur in the same
   *    location, typically the encoded class is returned if it is newer than
   *    the source file. See SimpleResolver for more details.
 
   * - If raw classes are allowed and no source or encoded class returned by
   *    *any* resolver, the first raw class returned by a resolver is loaded. This
   *    emulates polyglot's SourceClassResolver logic. Here, any ParsedClassType
   *    whose LazyClassInitializer returns true for fromClassFile() is considered
   *    to be from a raw class file.
   */
  @Override
  public Importable findImpl(String name) throws SemanticException {
    Importable from_raw = null;
    for (NamespaceResolver nr : path) {
      try {
        ParsedClassType ct = (ParsedClassType) nr.find(name);
        LazyClassInitializer init = (LazyClassInitializer) ct.initializer();

        // Is this type from a raw class?
        if (init.fromClassFile()) {
          if (Report.should_report(TOPICS, 4))
            Report.report(3,  "[" + namespace + "] " +"NamespaceResolver_c: found raw class for: " + name);

          // Is this the *first* raw class we've seen?
          if (from_raw == null) from_raw = ct;
          continue;
        }
        return ct;

      } catch (NoClassException e) {
      }
    }
    if (from_raw != null) {
      if (Report.should_report(TOPICS, 3))
        Report.report(3,  "[" + namespace + "] " +"NamespaceResolver_c: using raw class for: " + name);

      return from_raw;
    }

    throw new NoClassException(name);
  }

  @Override
  public boolean packageExistsImpl(String name) {
    for (NamespaceResolver nr : path)
      if (nr.packageExists(name)) return true;
    return false;
  }

  @Override
  public URI resolveCodebaseName(String name) throws SemanticException {
    URI ns = aliases.get(name);
    if (ns == null)
      throw new SemanticException("Unknown codebase name: " + name);
    return ns;
  }

  /**
   * Specify whether to use raw class files to resolve names for all namespaces
   * in this path.
   * 
   * @return previous value
   */
  @Override
  public boolean loadRawClasses(boolean use) {
    boolean nw = false;
    for (NamespaceResolver nr : path)
      nw |= nr.loadRawClasses(use);
    boolean old = load_raw;
    load_raw = nw;
    return old;
  }

  /**
   * Specify whether to use encoded class files to resolve names for all
   * namespaces in this path.
   * 
   * @return previous value
   */
  @Override
  public boolean loadEncodedClasses(boolean use) {
    boolean nw = false;
    for (NamespaceResolver nr : path)
      nw |= nr.loadEncodedClasses(use);
    boolean old = load_enc;
    load_enc = nw;
    return old;
  }

  /**
   * Specify whether to use source files to resolve names for all namespaces in
   * this path.
   * 
   * @return previous value
   */
  @Override
  public boolean loadSource(boolean use) {
    boolean nw = false;
    for (NamespaceResolver nr : path)
      nw |= nr.loadSource(use);
    boolean old = load_src;
    load_src = nw;
    return old;
  }

  @Override 
  public Label label() {
    if (extInfo.platformNamespace().equals(namespace)) {
      return LabelUtil._Impl.toLabel(extInfo.destinationStore(),
          LabelUtil._Impl.topInteg());
    }
    Store s = extInfo.destinationStore();
    Label join = LabelUtil._Impl.noComponents();
    for (NamespaceResolver nr : path) {
      join = LabelUtil._Impl.join(s, join, nr.label());
    }
    //TODO: Should we actually return worker meet join?
    return join;
  }
}
