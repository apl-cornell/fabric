package fabric.extension;

import fabric.ast.FabricCall;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.extension.JifCallExt;
import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.types.principal.Principal;
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

      JifMethodInstance mi = (JifMethodInstance)c.methodInstance();
      Label entryLabel = mi.pcBound();
      Label returnLabel = mi.returnLabel();
      
      Principal localPrincipal = ts.clientPrincipal(Position.compilerGenerated());
      Principal remotePrincipal = c.remoteClientPrincipal();
      
      lc.constrain(new NamedLabel("{C(rv), client$<-}", 
                                  ts.pairLabel(Position.compilerGenerated(), 
                                               ts.confProjection(returnLabel), 
                                               ts.writerPolicy(Position.compilerGenerated(), 
                                                               localPrincipal, localPrincipal))), 
                   LabelConstraint.LEQ,
                   new NamedLabel("{client$->, I(m)}",
                                  ts.pairLabel(Position.compilerGenerated(), 
                                               ts.readerPolicy(Position.compilerGenerated(), 
                                                               localPrincipal, localPrincipal), 
                                               ts.integProjection(entryLabel))), 
                                  A.labelEnv(), 
                                  c.position(), 
                                  new ConstraintMessage() {
        @Override
        public String msg() {
          return "C(rv) <= {client$->} and {client$<-} <= I(m) for obj.m@c(...)";
        }
  
        @Override
        public String detailMsg() {
          return "C(rv) <= {client$->} and {client$<-} <= I(m) for obj.m@c(...)";
        }
        
        @Override
        public String technicalMsg() {
          return "C(rv) <= {client$->} and {client$<-} <= I(m) for obj.m@c(...)";
        }        
      });
      
      lc.constrain(new NamedLabel("{C(m), c<-}", 
                                  ts.pairLabel(Position.compilerGenerated(), 
                                               ts.confProjection(entryLabel), 
                                               ts.writerPolicy(Position.compilerGenerated(), 
                                                               remotePrincipal, remotePrincipal))), 
                   LabelConstraint.LEQ,
                   new NamedLabel("[c->, I(rv)]",
                                  ts.pairLabel(Position.compilerGenerated(), 
                                               ts.readerPolicy(Position.compilerGenerated(), 
                                                               remotePrincipal, remotePrincipal), 
                                               ts.integProjection(returnLabel))), 
                                  A.labelEnv(), 
                                  c.position(), 
                                  new ConstraintMessage() {
        @Override
        public String msg() {
          return "C(m) <= {c->} and {c<-} <= I(rv) for obj.m@c(...)";
        }
        
        @Override
        public String detailMsg() {
          return "C(m) <= {c->} and {c<-} <= I(rv) for obj.m@c(...)";
        }
        
        @Override
        public String technicalMsg() {
          return "C(m) <= {c->} and {c<-} <= I(rv) for obj.m@c(...)";
        }        
      });
//      lc.constrain(c.remoteClientPrincipal(), 
//                   PrincipalConstraint.ACTSFOR, 
//                   ts.clientPrincipal(c.position()), 
//                   A.labelEnv(), 
//                   c.position(),
//                   new ConstraintMessage() {
//        @Override
//        public String msg() {
//          return "The principal of the remote client must acts for that of the local client.";
//        }
//
//        @Override
//        public String detailMsg() {
//          return "The principal of the remote client must acts for that of the local client.";
//        }
//        
//        @Override
//        public String technicalMsg() {
//          return "c acts for client$ in obj.m@c(...)";
//        }
//      });
    }
    
    return super.labelCheck(lc);
  }
}
