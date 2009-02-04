package fabric.ast;

import fabric.extension.AtomicJifExt_c;
import fabric.translate.AtomicToFabilExt_c;
import polyglot.ast.Ext;
import jif.ast.JifExtFactory_c;

/**
 * This class extends the Jif Extension factory to provide Jif extension objects
 * for atomic sections.
 */
public class FabricJifExtFactory_c extends JifExtFactory_c implements FabricExtFactory {
  public FabricJifExtFactory_c() {
    super();
  }
  
  public FabricJifExtFactory_c(FabricExtFactory next) {
    super(next);
  }

  public Ext extAtomic() {
    return new AtomicJifExt_c(new AtomicToFabilExt_c());
  }
}
