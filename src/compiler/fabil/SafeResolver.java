package fabil;

import java.net.URI;
import java.util.Arrays;

import polyglot.types.SemanticException;
import codebases.frontend.ExtensionInfo;
import codebases.types.NamespaceResolver;
import codebases.types.PathResolver;
import fabric.lang.Codebase;
import fabric.lang.security.Label;

/**
 * This resolver preempts lookups to inner by first using the platform resolver.
 * This ensures the platform classes cannot be redefined by a codebase.
 * 
 * @author owen
 */
public class SafeResolver extends PathResolver implements NamespaceResolver {
  protected NamespaceResolver inner;

  public SafeResolver(ExtensionInfo extInfo, NamespaceResolver inner) {
    super(extInfo, inner.namespace(), Arrays.asList(new NamespaceResolver[] {
        extInfo.typeSystem().platformResolver(), inner }));
    this.inner = inner;
  }

  @Override
  public URI resolveCodebaseName(String name) throws SemanticException {
    return inner.resolveCodebaseName(name);
  }

  @Override
  public boolean loadRawClasses(boolean use) {
    return inner.loadRawClasses(use);
  }

  @Override
  public boolean loadEncodedClasses(boolean use) {
    return inner.loadEncodedClasses(use);
  }

  @Override
  public boolean loadSource(boolean use) {
    return inner.loadSource(use);
  }

  @Override
  public Label label() {
    return inner.label();
  }

  @Override
  public Codebase codebase() {
    return inner.codebase();
  }

}
