package codebases.types;

import java.net.URI;
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

  public PathResolver(ExtensionInfo extInfo, URI ns,
      List<NamespaceResolver> path) {
    this(extInfo, ns, path, Collections.<String, URI> emptyMap());
  }
  
  public PathResolver(ExtensionInfo extInfo, URI ns,
      List<NamespaceResolver> path, Map<String, URI> aliases) {
    super(extInfo, ns, null);
    if(path.contains(null))
      throw new NullPointerException("Null resolver in path!");
    this.path = path;
    this.aliases = aliases;
  }

  @Override
  public Importable findImpl(String name) throws SemanticException {
    for (NamespaceResolver nr : path)
      try {
        return nr.find(name);
      } catch (NoClassException e) { } 
    
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
    if(ns == null)
      throw new SemanticException("Unknown codebase name: " + name);
    return ns;
  }

  @Override
  public boolean loadRawClasses(boolean use) {
    boolean old = false;
    for (NamespaceResolver nr : path)
      old |= nr.loadRawClasses(use);
    return old;
  }

  @Override
  public boolean loadEncodedClasses(boolean use) {
    boolean old = false;
    for (NamespaceResolver nr : path)
      old |= nr.loadEncodedClasses(use);
    return old;
  }

  @Override
  public boolean loadSource(boolean use) {
    boolean old = false;
    for (NamespaceResolver nr : path)
      old |= nr.loadSource(use);
    return old;
  }

}
