package jif.ast;

import jif.extension.*;
import jif.translate.*;
import polyglot.ast.AbstractExtFactory_c;
import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;

/**
 * This class provides is Jif's Extension factory, creating the appropriate
 * Ext objects as required.
 */
public class AbstractJifExtFactory_c extends AbstractExtFactory_c 
    implements JifExtFactory
{
    public AbstractJifExtFactory_c() {
        super();
    }

    public AbstractJifExtFactory_c(ExtFactory nextExtFactory) {
        super(nextExtFactory);
    }

    public final Ext extInstTypeNode() {
        Ext e = extInstTypeNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extInstTypeNode();
            e = composeExts(e, e2);
        }
        return postExtInstTypeNode(e);
    }

    public final Ext extLabeledTypeNode() {
        Ext e = extLabeledTypeNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extLabeledTypeNode();
            e = composeExts(e, e2);
        }
        return postExtLabeledTypeNode(e);
    }

    public final Ext extAmbNewArray() {
        Ext e = extAmbNewArrayImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extAmbNewArray();
            e = composeExts(e, e2);
        }
        return postExtAmbNewArray(e);
    }

    public final Ext extAmbParamTypeOrAccess() {
        Ext e = extAmbParamTypeOrAccessImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extAmbParamTypeOrAccess();
            e = composeExts(e, e2);
        }
        return postExtAmbParamTypeOrAccess(e);
    }

    public final Ext extJoinLabelNode() {
        Ext e = extJoinLabelNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extJoinLabelNode();
            e = composeExts(e, e2);
        }
        return postExtJoinLabelNode(e);
    }

    public final Ext extMeetLabelNode() {
        Ext e = extMeetLabelNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extMeetLabelNode();
            e = composeExts(e, e2);
        }
        return postExtMeetLabelNode(e);
    }

    public final Ext extPolicyNode() {
        Ext e = extPolicyNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extPolicyNode();
            e = composeExts(e, e2);
        }
        return postExtPolicyNode(e);
    }

    public final Ext extAmbDynamicLabelNode() {
        Ext e = extAmbDynamicLabelNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extAmbDynamicLabelNode();
            e = composeExts(e, e2);
        }
        return postExtAmbDynamicLabelNode(e);
    }

    public final Ext extAmbVarLabelNode() {
        Ext e = extAmbVarLabelNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extAmbVarLabelNode();
            e = composeExts(e, e2);
        }
        return postExtAmbVarLabelNode(e);
    }

    public final Ext extAmbThisLabelNode() {
        Ext e = extAmbThisLabelNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extAmbThisLabelNode();
            e = composeExts(e, e2);
        }
        return postExtAmbThisLabelNode(e);
    }

    public final Ext extCanonicalLabelNode() {
        Ext e = extCanonicalLabelNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extCanonicalLabelNode();
            e = composeExts(e, e2);
        }
        return postExtCanonicalLabelNode(e);
    }

    public final Ext extParamNode() {
        Ext e = extParamNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extParamNode();
            e = composeExts(e, e2);
        }
        return postExtParamNode(e);
    }

    public final Ext extLabelNode() {
        Ext e = extLabelNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extLabelNode();
            e = composeExts(e, e2);
        }
        return postExtLabelNode(e);
    }

    public final Ext extPrincipalNode() {
        Ext e = extPrincipalNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extPrincipalNode();
            e = composeExts(e, e2);
        }
        return postExtPrincipalNode(e);
    }

    public final Ext extAmbPrincipalNode() {
        Ext e = extAmbPrincipalNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extAmbPrincipalNode();
            e = composeExts(e, e2);
        }
        return postExtAmbPrincipalNode(e);
    }


    public final Ext extCanonicalPrincipalNode() {
        Ext e = extCanonicalPrincipalNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extCanonicalPrincipalNode();
            e = composeExts(e, e2);
        }
        return postExtCanonicalPrincipalNode(e);
    }

    public final Ext extAmbParam() {
        Ext e = extAmbParamImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extAmbParam();
            e = composeExts(e, e2);
        }
        return postExtAmbParam(e);
    }

    public final Ext extParamDecl() {
        Ext e = extParamDeclImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extParamDecl();
            e = composeExts(e, e2);
        }
        return postExtParamDecl(e);
    }

    public final Ext extConstraintNode() {
        Ext e = extConstraintNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extConstraintNode();
            e = composeExts(e, e2);
        }
        return postExtConstraintNode(e);
    }

    public final Ext extCanonicalConstraintNode() {
        Ext e = extCanonicalConstraintNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extCanonicalConstraintNode();
            e = composeExts(e, e2);
        }
        return postExtCanonicalConstraintNode(e);
    }

    public final Ext extAuthConstraintNode() {
        Ext e = extAuthConstraintNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extAuthConstraintNode();
            e = composeExts(e, e2);
        }
        return postExtAuthConstraintNode(e);
    }

    public final Ext extAutoEndorseConstraintNode() {
        Ext e = extAutoEndorseConstraintNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extAutoEndorseConstraintNode();
            e = composeExts(e, e2);
        }
        return postExtAutoEndorseConstraintNode(e);
    }

    public final Ext extCallerConstraintNode() {
        Ext e = extCallerConstraintNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extCallerConstraintNode();
            e = composeExts(e, e2);
        }
        return postExtCallerConstraintNode(e);
    }

    public final Ext extActsForConstraintNode() {
        Ext e = extActsForConstraintNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extActsForConstraintNode();
            e = composeExts(e, e2);
        }
        return postExtLabeledTypeNode(e);
    }

    public final Ext extLabelLeAssertionNode() {
        Ext e = extLabelLeAssertionNodeImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extLabelLeAssertionNode();
            e = composeExts(e, e2);
        }
        return postExtLabelLeAssertionNode(e);
    }

    public final Ext extDeclassifyStmt() {
        Ext e = extDeclassifyStmtImpl();
        if (nextExtFactory() != null && 
		nextExtFactory() instanceof JifStmtExtFactory) {
            JifStmtExtFactory nextFac = (JifStmtExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extDeclassifyStmt();
            e = composeExts(e, e2);
        }
        return postExtDeclassifyStmt(e);
    }

    public final Ext extDeclassifyExpr() {
        Ext e = extDeclassifyExprImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extDeclassifyExpr();
            e = composeExts(e, e2);
        }
        return postExtDeclassifyExpr(e);
    }

    public final Ext extEndorseStmt() {
        Ext e = extEndorseStmtImpl();
        if (nextExtFactory() != null && 
        nextExtFactory() instanceof JifStmtExtFactory) {
            JifStmtExtFactory nextFac = (JifStmtExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extEndorseStmt();
            e = composeExts(e, e2);
        }

        return postExtEndorseStmt(e);
    }
    public final Ext extCheckedEndorseStmt() {
        Ext e = extCheckedEndorseStmtImpl();
        if (nextExtFactory() != null && 
        nextExtFactory() instanceof JifStmtExtFactory) {
            JifStmtExtFactory nextFac = (JifStmtExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extCheckedEndorseStmt();
            e = composeExts(e, e2);
        }

        return postExtEndorseStmt(e);
    }

    public final Ext extEndorseExpr() {
        Ext e = extEndorseExprImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extEndorseExpr();
            e = composeExts(e, e2);
        }
        return postExtEndorseExpr(e);
    }

    public final Ext extNewLabel() {
        Ext e = extNewLabelImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extNewLabel();
            e = composeExts(e, e2);
        }
        return postExtNewLabel(e);
    }

    public final Ext extLabelExpr() {
        Ext e = extLabelExprImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extLabelExpr();
            e = composeExts(e, e2);
        }
        return postExtLabelExpr(e);
    }

    public final Ext extPrincipalExpr() {
        Ext e = extPrincipalExprImpl();
        if (nextExtFactory() != null && 
                nextExtFactory() instanceof JifExtFactory) {
            JifExtFactory nextFac = (JifExtFactory) nextExtFactory(); 
            Ext e2 = nextFac.extPrincipalExpr();
            e = composeExts(e, e2);
        }
        return postExtPrincipalExpr(e);
    }


    //----------------------------------------------------------------
    // Jif-specific nodes Impls
    //-----------------------------------------------------------------
    protected Ext extInstTypeNodeImpl() {
        return extTypeNodeImpl();
    }

    protected Ext extLabeledTypeNodeImpl() {
        return extTypeNodeImpl();
    }

    protected Ext extAmbNewArrayImpl() {
        return extExprImpl();
    }

    protected Ext extAmbParamTypeOrAccessImpl() {
        return extNodeImpl();
    }

    protected Ext extJoinLabelNodeImpl() {
        return extLabelNode();
    }

    protected Ext extMeetLabelNodeImpl() {
        return extLabelNode();
    }

    protected Ext extPolicyNodeImpl() {
        return extNode();
    }

    protected Ext extAmbDynamicLabelNodeImpl() {
        return extLabelNode();
    }

    protected Ext extAmbVarLabelNodeImpl() {
        return extLabelNode();
    }

    protected Ext extAmbThisLabelNodeImpl() {
        return extLabelNode();
    }

    protected Ext extCanonicalLabelNodeImpl() {
        return extNodeImpl();
    }

    protected Ext extParamNodeImpl() {
        return extNodeImpl();
    }

    protected Ext extLabelNodeImpl() {
        return extParamNode();
    }

    protected Ext extPrincipalNodeImpl() {
        return extParamNode();
    }

    protected Ext extAmbPrincipalNodeImpl() {
        return extPrincipalNode();
    }


    protected Ext extCanonicalPrincipalNodeImpl() {
        return extNodeImpl();
    }

    protected Ext extAmbParamImpl() {
        return extParamNode();
    }

    protected Ext extParamDeclImpl() {
        return extNodeImpl();
    }

    protected Ext extConstraintNodeImpl() {
        return extNodeImpl();
    }

    protected Ext extCanonicalConstraintNodeImpl() {
        return extConstraintNode();
    }

    protected Ext extAuthConstraintNodeImpl() {
        return extConstraintNode();
    }

    protected Ext extAutoEndorseConstraintNodeImpl() {
        return extConstraintNode();
    }

    protected Ext extCallerConstraintNodeImpl() {
        return extConstraintNode();
    }

    protected Ext extActsForConstraintNodeImpl() {
        return extConstraintNode();
    }

    protected Ext extLabelLeAssertionNodeImpl() {
        return extConstraintNode();
    }
    
    protected Ext extDeclassifyStmtImpl() {
        return extStmtImpl();
    }

    protected Ext extDeclassifyExprImpl() {
        return extExprImpl();
    }

    protected Ext extEndorseStmtImpl() {
        return extStmtImpl();
    }

    protected Ext extCheckedEndorseStmtImpl() {
        return extEndorseStmtImpl();
    }

    protected Ext extEndorseExprImpl() {
        return extExprImpl();
    }

    protected Ext extNewLabelImpl() {
        return extLabelExprImpl();
    }
    protected Ext extLabelExprImpl() {
        return extExprImpl();
    }
    protected Ext extPrincipalExprImpl() {
        return extExprImpl();
    }

    //----------------------------------------------------------------
    // Jif-specific nodes Post methods
    //-----------------------------------------------------------------
    protected Ext postExtInstTypeNode(Ext e) {
        return postExtTypeNode(e);
    }

    protected Ext postExtLabeledTypeNode(Ext e) {
        return postExtTypeNode(e);
    }

    protected Ext postExtAmbNewArray(Ext e) {
        return postExtNode(e);
    }

    protected Ext postExtAmbParamTypeOrAccess(Ext e) {
        return postExtNode(e);
    }

    protected Ext postExtJoinLabelNode(Ext e) {
        return postExtLabelNode(e);
    }

    protected Ext postExtMeetLabelNode(Ext e) {
        return postExtLabelNode(e);
    }

    protected Ext postExtPolicyNode(Ext e) {
        return postExtNode(e);
    }

    protected Ext postExtAmbDynamicLabelNode(Ext e) {
        return postExtLabelNode(e);
    }

    protected Ext postExtAmbVarLabelNode(Ext e) {
        return postExtLabelNode(e);
    }

    protected Ext postExtAmbThisLabelNode(Ext e) {
        return postExtLabelNode(e);
    }

    protected Ext postExtCanonicalLabelNode(Ext e) {
        return postExtLabelNode(e);
    }

    protected Ext postExtParamNode(Ext e) {
        return postExtNode(e);
    }

    protected Ext postExtLabelNode(Ext e) {
        return postExtParamNode(e);
    }

    protected Ext postExtPrincipalNode(Ext e) {
        return postExtParamNode(e);
    }

    protected Ext postExtAmbPrincipalNode(Ext e) {
        return postExtPrincipalNode(e);
    }


    protected Ext postExtCanonicalPrincipalNode(Ext e) {
        return postExtPrincipalNode(e);
    }

    protected Ext postExtAmbParam(Ext e) {
        //return postExtAmbParam(e);
	return postExtNode(e);
    }

    protected Ext postExtParamDecl(Ext e) {
        return postExtNode(e);
    }

    protected Ext postExtConstraintNode(Ext e) {
        return postExtNode(e);
    }

    protected Ext postExtCanonicalConstraintNode(Ext e) {
        return postExtConstraintNode(e);
    }

    protected Ext postExtAuthConstraintNode(Ext e) {
        return postExtConstraintNode(e);
    }

    protected Ext postExtAutoEndorseConstraintNode(Ext e) {
        return postExtConstraintNode(e);
    }

    protected Ext postExtCallerConstraintNode(Ext e) {
        return postExtConstraintNode(e);
    }

    protected Ext postExtActsForConstraintNode(Ext e) {
        return postExtConstraintNode(e);
    }

    protected Ext postExtLabelLeAssertionNode(Ext e) {
        return postExtConstraintNode(e);
    }

    protected Ext postExtDowngradeStmt(Ext e) {
        return postExtStmt(e);
    }

    protected Ext postExtDowngradeExpr(Ext e) {
        return postExtExpr(e);
    }

    protected Ext postExtDeclassifyStmt(Ext e) {
        return postExtDowngradeStmt(e);
    }

    protected Ext postExtDeclassifyExpr(Ext e) {
        return postExtDowngradeExpr(e);
    }

    protected Ext postExtEndorseStmt(Ext e) {
        return postExtDowngradeStmt(e);
    }

    protected Ext postExtEndorseExpr(Ext e) {
        return postExtDowngradeExpr(e);
    }

    protected Ext postExtNewLabel(Ext e) {
        return postExtLabelExpr(e);
    }

    protected Ext postExtLabelExpr(Ext e) {
        return postExtExpr(e);
    }
    protected Ext postExtPrincipalExpr(Ext e) {
        return postExtExpr(e);
    }
}
