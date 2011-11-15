package jif.ast;

import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;

public interface JifExtFactory extends ExtFactory, JifStmtExtFactory {
    Ext extInstTypeNode();
    Ext extLabeledTypeNode();
    Ext extAmbNewArray();
    Ext extAmbParamTypeOrAccess();
    Ext extJoinLabelNode();
    Ext extMeetLabelNode();
    Ext extPolicyNode();
    Ext extAmbDynamicLabelNode();
    Ext extAmbVarLabelNode();
    Ext extAmbThisLabelNode();
    Ext extCanonicalLabelNode();
    Ext extParamNode();
    Ext extLabelNode();
    Ext extPrincipalNode();
    Ext extAmbPrincipalNode();
    Ext extCanonicalPrincipalNode();
    Ext extAmbParam();
    Ext extParamDecl();
    Ext extConstraintNode();
    Ext extCanonicalConstraintNode();
    Ext extAuthConstraintNode();
    Ext extAutoEndorseConstraintNode();
    Ext extCallerConstraintNode();
    Ext extActsForConstraintNode();
    Ext extLabelLeAssertionNode();
    Ext extDeclassifyStmt();
    Ext extDeclassifyExpr();
    Ext extEndorseStmt();
    Ext extCheckedEndorseStmt();
    Ext extEndorseExpr();
    Ext extNewLabel();
    Ext extLabelExpr();
    Ext extPrincipalExpr();
}
