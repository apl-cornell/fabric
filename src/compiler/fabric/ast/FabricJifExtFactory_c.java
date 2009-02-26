package fabric.ast;

import fabric.extension.*;
import fabric.translate.*;
import polyglot.ast.Ext;
import jif.ast.JifExtFactory_c;
import jif.extension.JifBinaryExt;
import jif.extension.JifMethodDeclExt;
import jif.translate.ClassBodyToJavaExt_c;

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
    return new CallJifExt_c(new CallToFabilExt_c());
  }
  
  public Ext extClient() {
    return new ClientJifExt_c(new ClientToFabilExt_c());
  }
  
  public Ext extRemoteClientGetter() {
    return new RemoteClientGetterJifExt_c(new RemoteClientGetterToFabilExt_c());
  }
  
  @Override
  public Ext extMethodDeclImpl() {
    return new JifMethodDeclExt(new MethodDeclToFabilExt_c());
  }
  
  @Override
  public Ext extBinaryImpl() {
    return new JifBinaryExt(new BinaryToFabilExt_c());
  }
  
  @Override
  public Ext extClassBodyImpl() {
    return new ClassBodyJifExt_c(new ClassBodyToJavaExt_c());
  }
}
