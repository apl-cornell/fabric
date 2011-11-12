package codebases.types;

import java.net.URI;

import codebases.frontend.ExtensionInfo;
import fabric.common.NSUtil;
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
    //always ignore source mod time
    this.ignore_mod_times = true;
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

  @Override
  public URI resolveCodebaseNameImpl(String name) {
    Codebase cb = codebase.resolveCodebaseName(name);
    if (cb == null)
      return null;
    return NSUtil.namespace(cb);
  }
  
  @Override
  public Label label() {
    return codebase.get$label();
  }
}
