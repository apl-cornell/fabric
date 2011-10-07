package codebases.types;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import polyglot.types.Importable;
import polyglot.types.NoClassException;
import polyglot.types.SemanticException;
import codebases.frontend.ExtensionInfo;

public class PathResolver extends NamespaceResolver_c implements
    NamespaceResolver {
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

  @Override
  public Importable findImpl(String name) throws SemanticException {
    if (!load_raw) {
      for (NamespaceResolver nr : path)
        nr.loadRawClasses(false);

      for (NamespaceResolver nr : path)
        try {
          return nr.find(name);
        } catch (NoClassException e) {
        }
    } else {
      List<Boolean> backup = new ArrayList<Boolean>(path.size());
      try {
        // Try loading an encoded class or source file first.
        for (NamespaceResolver nr : path)
          backup.add(nr.loadRawClasses(false));

        for (NamespaceResolver nr : path)
          try {
            return nr.find(name);
          } catch (NoClassException e) {
          }

        // Now try raw classes.
        for (NamespaceResolver nr : path)
          nr.loadRawClasses(true);

        for (NamespaceResolver nr : path)
          try {
            return nr.find(name);
          } catch (NoClassException e) {
          }
      } finally {
        // Restore original settings.
        for (int i = 0; i < path.size(); i++) {
          path.get(i).loadRawClasses(backup.get(i));
        }
      }
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
   * Specify whether to use raw class files to resolve names for all
   * namespaces in this path.
   * 
   * @return previous value
   */
  @Override
  public boolean loadRawClasses(boolean use) {
    // XXX: This is a little gross: polyglot's SourceClassResolver always
    // prefers source over raw classfiles.  
    // This means we should try to load an encodedClass or source from the path
    // first, but fall back to a raw class if they are allowed.      
    boolean old = load_raw;
    load_raw = use;
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
   * Specify whether to use source files to resolve names for all
   * namespaces in this path.
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

}
