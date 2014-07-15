package codebases.types;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import polyglot.main.Report;
import polyglot.types.Importable;
import polyglot.types.LazyClassInitializer;
import polyglot.types.NoClassException;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import codebases.frontend.ExtensionInfo;
import fabric.common.NSUtil;
import fabric.lang.Codebase;
import fabric.lang.security.Label;

public class CodebaseResolver extends SimpleResolver implements
    NamespaceResolver {
  private static final Collection<String> TOPICS;
  static {
    TOPICS = new ArrayList<>(2);
    TOPICS.add(Report.types);
    TOPICS.add(Report.resolver);
  }

  protected Codebase codebase;
  protected NamespaceResolver platform;

  public CodebaseResolver(ExtensionInfo extInfo, URI namespace) {
    super(extInfo, namespace, null);
    this.load_raw = false;
    this.load_enc = true;
    this.load_src = true;
    this.codebase = NSUtil.fetch_codebase(namespace);
    // always ignore source mod time
    this.ignore_mod_times = true;
    this.platform = extInfo.typeSystem().platformResolver();
  }

  @Override
  public Importable findImpl(String name) throws SemanticException {
    // Codebases may never resolve platform types, so always resolve against
    // the platformResolver first.
    ParsedClassType ct = null;
    try {
      ct = (ParsedClassType) platform.find(name);
    } catch (NoClassException e) {
    }
    if (ct == null) {
      return super.findImpl(name);
    }

    LazyClassInitializer init = (LazyClassInitializer) ct.initializer();
    // Is this type from a raw class?
    if (init.fromClassFile()) {
      if (Report.should_report(TOPICS, 4))
        Report.report(3, "[" + namespace + "] "
            + "NamespaceResolver_c: found raw class for: " + name);
    }
    return ct;
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
    if (cb == null) return null;
    return NSUtil.namespace(cb);
  }

  @Override
  public Label label() {
    return codebase.get$$updateLabel();
  }
}
