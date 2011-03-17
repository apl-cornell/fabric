package fabric.types;

import jif.translate.LabelToJavaExpr;
import jif.types.JifClassType;
import jif.types.label.ProviderLabel_c;
import fabil.types.CodebaseClassType;
import fabric.frontend.RemoteSource;

public class FabricProviderLabel_c extends ProviderLabel_c {

  public FabricProviderLabel_c(JifClassType classType, LabelToJavaExpr toJava) {
    super(classType, toJava);
    
    // Classes published to Fabric never have trusted providers.
    if (classType instanceof CodebaseClassType) {
      CodebaseClassType cbct = (CodebaseClassType) classType;
      if (cbct.fromSource() instanceof RemoteSource) {
        this.isTrusted = false;
      }
    }
  }

}
