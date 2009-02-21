package fabric.ast;

import fabric.extension.AbortJifExt_c;
import fabric.extension.AtomicJifExt_c;
import fabric.extension.RetryJifExt_c;
import fabric.translate.AbortToFabilExt_c;
import fabric.translate.AtomicToFabilExt_c;
import fabric.translate.CallToFabilExt_c;
import fabric.translate.RetryToFabilExt_c;
import polyglot.ast.Ext;
import jif.ast.JifExtFactory_c;
import jif.extension.JifCallExt;

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
  
  public Ext extAbort() {
    return new AbortJifExt_c(new AbortToFabilExt_c());
  }
  
  public Ext extRetry() {
    return new RetryJifExt_c(new RetryToFabilExt_c());
  }
  
  @Override
  public Ext extCallImpl() {
    return new JifCallExt(new CallToFabilExt_c());
  }
}
