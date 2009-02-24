package fabric.extension;

import fabric.ast.FabricCall;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import jif.extension.JifCallExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.PrincipalConstraint;
import jif.visit.LabelChecker;

public class CallJifExt_c extends JifCallExt {
  public CallJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }
  
  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    FabricCall c = (FabricCall)node();
    
    if (c.remoteClient() != null) {
      FabricTypeSystem ts = (FabricTypeSystem)lc.typeSystem();
      JifContext A = lc.jifContext(); 
      A = (JifContext)c.del().enterScope(A);
      
      lc.constrain(c.remoteClientPrincipal(), 
                   PrincipalConstraint.ACTSFOR, 
                   ts.clientPrincipal(c.position()), 
                   A.labelEnv(), 
                   c.position(),
                   new ConstraintMessage() {
        @Override
        public String msg() {
          return "The principal of the remote client must acts for that of the local client.";
        }

        @Override
        public String detailMsg() {
          return "The principal of the remote client must acts for that of the local client.";
        }
        
        @Override
        public String technicalMsg() {
          return "c acts for client$ in obj.m@c(...)";
        }
      });
    }
    
    return super.labelCheck(lc);
  }
}
