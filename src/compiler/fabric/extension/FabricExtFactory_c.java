package fabric.extension;

import fabric.extension.*;
import polyglot.ast.AbstractExtFactory_c;
import polyglot.ast.Ext;

/**
 * Factory for Fabric extension nodes.
 */
public class FabricExtFactory_c extends AbstractExtFactory_c {
  public Ext extAtomic() {
    return new AtomicExt_c();
  }
}
