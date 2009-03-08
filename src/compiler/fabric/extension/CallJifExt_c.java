package fabric.extension;

import java.util.List;

import fabric.ast.FabricCall;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import jif.extension.JifCallExt;
import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.*;
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
      
      // Fold in the policies of all the parameters.
      // Note that we are calling the original method, rather than the _remote version
      ConfPolicy entryConfPolicy = ts.confProjection(entryLabel);
      IntegPolicy entryIntegPolicy = ts.integProjection(entryLabel);
      for (Type ft : (List<Type>)mi.formalTypes()) {
        Label fl = ts.labelOfType(ft);
        ConfPolicy cp = ts.confProjection(fl);
        IntegPolicy ip = ts.integProjection(fl);
        entryConfPolicy = ts.join(entryConfPolicy, cp);
        entryIntegPolicy = ts.meet(entryIntegPolicy, ip);
      }
      
      lc.constrain(new NamedLabel("{C(rv), *<-client$}", 
                                  ts.pairLabel(Position.compilerGenerated(), 
                                               ts.confProjection(returnLabel), 
                                               ts.writerPolicy(Position.compilerGenerated(), 
                                                               ts.topPrincipal(Position.compilerGenerated()), 
                                                               localPrincipal))), 
                   LabelConstraint.LEQ,
                   new NamedLabel("{*->client$, I(m)}",
                                  ts.pairLabel(Position.compilerGenerated(), 
                                               ts.readerPolicy(Position.compilerGenerated(), 
                                                               ts.topPrincipal(Position.compilerGenerated()), 
                                                               localPrincipal), 
                                               entryIntegPolicy)), 
                   A.labelEnv(), 
                   c.position(), 
                   new ConstraintMessage() {
        @Override
        public String msg() {
          return "C(rv) <= {*->client$} and {*<-client$} <= I(m) for obj.m@c(...)";
        }
  
        @Override
        public String detailMsg() {
          return "C(rv) <= {*->client$} and {*<-client$} <= I(m) for obj.m@c(...)";
        }
        
        @Override
        public String technicalMsg() {
          return "C(rv) <= {*->client$} and {*<-client$} <= I(m) for obj.m@c(...)";
        }        
      });
      
      lc.constrain(new NamedLabel("{C(m), *<-c}", 
                                  ts.pairLabel(Position.compilerGenerated(), 
                                               entryConfPolicy, 
                                               ts.writerPolicy(Position.compilerGenerated(), 
                                                               ts.topPrincipal(Position.compilerGenerated()), 
                                                               remotePrincipal))), 
                   LabelConstraint.LEQ,
                   new NamedLabel("[*->c, I(rv)]",
                                  ts.pairLabel(Position.compilerGenerated(), 
                                               ts.readerPolicy(Position.compilerGenerated(), 
                                                               ts.topPrincipal(Position.compilerGenerated()), 
                                                               remotePrincipal), 
                                               ts.integProjection(returnLabel))), 
                   A.labelEnv(), 
                   c.position(), 
                   new ConstraintMessage() {
        @Override
        public String msg() {
          return "C(m) <= {*->c} and {*<-c} <= I(rv) for obj.m@c(...)";
        }
        
        @Override
        public String detailMsg() {
          return "C(m) <= {*->c} and {*<-c} <= I(rv) for obj.m@c(...)";
        }
        
        @Override
        public String technicalMsg() {
          return "C(m) <= {*->c} and {*<-c} <= I(rv) for obj.m@c(...)";
        }        
      });
      
      for (Assertion as : (List<Assertion>)mi.constraints()) {
        if (as instanceof CallerConstraint) {
          CallerConstraint cc = (CallerConstraint)as;
          for (Principal p : (List<Principal>)cc.principals()) {
            lc.constrain(remotePrincipal, 
                         PrincipalConstraint.ACTSFOR, 
                         p, 
                         A.labelEnv(), 
                         c.position(), 
                         new ConstraintMessage() {
              @Override
              public String msg() {
                return "The principal of the remote client must act for every principal in the caller constraint.";
              }
              
              @Override
              public String detailMsg() {
                return "The principal of the remote client must act for every principal in the caller constraint.";
              }
              
              @Override
              public String technicalMsg() {
                return "c acts for p in obj.m@c(...) with the constraint caller(p)";
              }
            });
          }
        }
      }
    }
    
    return super.labelCheck(lc);
  }
}
