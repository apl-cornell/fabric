package fabric.extension;

import fabric.types.FabricParsedClassType;
import jif.ast.JifClassDecl;
import jif.extension.JifClassDeclExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifClassType;
import jif.types.JifContext;
import jif.types.JifParsedPolyType;
import jif.types.JifTypeSystem;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.PrincipalConstraint;
import jif.types.label.Label;
import jif.types.label.ProviderLabel;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.ast.ClassBody;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class FabricClassDeclExt extends JifClassDeclExt {

  public FabricClassDeclExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    
    JifClassDecl n = (JifClassDecl) node();
    FabricParsedClassType pct = (FabricParsedClassType) n.type();
    Label singleFieldLabel = pct.singleFieldLabel();
    Label singleAccessLabel = pct.singleAccessLabel();

    JifTypeSystem jts = lc.typeSystem();
    JifContext A = lc.jifContext();
    A = (JifContext)A.pushClass(n.type(), n.type());
    A = n.addParamsToContext(A);
    A = n.addAuthorityToContext(A);

    A.setCurrentCodePCBound(jts.notTaken());

    final JifParsedPolyType ct = (JifParsedPolyType) n.type();

    // let the label checker know that we are about to enter a class body
    lc.enteringClassDecl(ct);

    // TODO: Revisit this null check
    if (singleFieldLabel != null && singleAccessLabel != null) {
      lc.constrain(new NamedLabel("object label", singleFieldLabel),
          LabelConstraint.LEQ, 
          new NamedLabel("access label", singleAccessLabel),
          A.labelEnv(), n.position(),
          new ConstraintMessage() {
        public String msg() {
          return "The access label of a class should be at least as restrictive" +
          " as its object label";
        }
      }
      );
    }
    
    // construct a principal that represents the authority of ct
    final Principal authPrincipal = lc.jifTypeSystem().conjunctivePrincipal(ct.position(), ct.authority());

    // Check the authority of the class against the superclass.
    if (ct.superType() instanceof JifClassType) {
        final JifClassType superType = (JifClassType) ct.superType();         
        
        for (final Principal pl : superType.authority()) {
            // authPrincipal must actfor pl i.e., at least one
            // of the principals that have authorized ct must act for pl.
            lc.constrain(authPrincipal, 
                         PrincipalConstraint.ACTSFOR, 
                        pl, 
                       A.labelEnv(),
                       n.position(),
                       new ConstraintMessage() {
                @Override
                public String msg() {
                    return "Superclass " + superType + " requires " + ct + " to " +
                    "have the authority of principal " + pl;
                }
                @Override
                public String detailMsg() {
                    return "The class " + superType + " has the authority of the " +
                    "principal " + pl + ". To extend this class, " + ct + 
                    " must also have the authority of " + pl + ".";
                }
            });             
            
        }
    }
    
    A = (JifContext) n.del().enterScope(A);
    lc = lc.context(A);

    final ProviderLabel provider = ct.provider();
    NamedLabel namedProvider =
            new NamedLabel(provider.toString(), "provider of "
                    + provider.classType().fullName(), provider);
    lc.constrain(namedProvider, authPrincipal, A.labelEnv(), n.position(),
            new ConstraintMessage() {
                @Override
                public String msg() {
                    return provider + " must act for " + authPrincipal;
                }

                @Override
                public String detailMsg() {
                    return provider + " is the provider of " + ct
                            + " but does not have authority to act for "
                            + authPrincipal;
                }
            });
                    
    // label check class conformance
    labelCheckClassConformance(ct,lc);

    // label check the body
    ClassBody body = (ClassBody) lc.labelCheck(n.body());

    // let the label checker know that we have left the class body
    n = lc.leavingClassDecl((JifClassDecl)n.body(body));
    return n;
  }
  
}
