package codebases.types;

import static fabric.common.FabricLocationFactory.getLocation;
import codebases.frontend.ExtensionInfo;
import fabric.common.FabricLocation;
import fabric.common.NSUtil;
import fabric.lang.Codebase;
import fabric.lang.security.Label;

public class CodebaseResolver extends SimpleResolver implements
    NamespaceResolver {
  protected Codebase codebase;

  public CodebaseResolver(ExtensionInfo extInfo, FabricLocation namespace) {
    super(extInfo, namespace, null);
    this.load_raw = false;
    this.load_enc = true;
    this.load_src = true;
    this.codebase = namespace.getCodebase();
    // always ignore source mod time
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
  public FabricLocation resolveCodebaseNameImpl(String name) {
    Codebase cb = codebase.resolveCodebaseName(name);
    if (cb == null) return null;
    return getLocation(false, NSUtil.namespace(cb));
  }

  @Override
  public Label label() {
    return codebase.get$$updateLabel();
  }
}
