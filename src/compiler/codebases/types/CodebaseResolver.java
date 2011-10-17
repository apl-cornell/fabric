package codebases.types;

import java.net.URI;

import polyglot.types.SemanticException;

import codebases.frontend.ExtensionInfo;
import fabil.SimpleResolver;
import fabric.common.NSUtil;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.lang.security.Label;

public class CodebaseResolver extends SimpleResolver implements
    NamespaceResolver {
  protected Codebase codebase;

  public CodebaseResolver(ExtensionInfo extInfo, URI namespace) {
    super(extInfo, namespace, null);
    this.load_raw = false;
    this.load_enc = true;
    this.load_src = true;
    this.codebase = NSUtil.fetch_codebase(namespace);
  }

  @Override
  public Codebase codebase() {
    return codebase;
  }
  
  @Override
  public boolean loadRawClasses(boolean use) {
    return false;
  }

  @Override
  public boolean loadEncodedClasses(boolean use) {
    return true;
  }

  @Override
  public boolean loadSource(boolean use) {
    return true;
  }

  @SuppressWarnings("unused")
  @Override
  public URI resolveCodebaseName(String name) throws SemanticException {
    return SysUtil.oid(codebase.resolveCodebaseName(name));
  }
  
  @Override
  public Label label() {
    return codebase.get$label();
  }
}
