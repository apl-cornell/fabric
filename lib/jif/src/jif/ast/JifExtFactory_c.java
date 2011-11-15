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
public class JifExtFactory_c extends AbstractJifExtFactory_c 
{
    public JifExtFactory_c() {
        super();
    }

    public JifExtFactory_c(ExtFactory nextExtFactory) {
        super(nextExtFactory);
    }

    protected Ext extNodeImpl() {
        return new Jif_c(new CannotToJavaExt_c());
    }

    protected Ext extExprImpl() {
        return new JifExprExt(new CannotToJavaExt_c());
    }
    
     protected Ext extIdImpl() {
       return new Jif_c(new IdToJavaExt_c());
     }

    /**
     * This method returns a vanilla Jif extensions (Jif_c) with a
     * CannotToJavaExt_c for the ToJavaExt.
     */
    protected Ext extCannotToJavaImpl() {
        return new Jif_c(new CannotToJavaExt_c());
    }

    protected Ext extAmbExprImpl() {
        return extCannotToJavaImpl();
    }

    protected Ext extAmbPrefixImpl() {
        return extCannotToJavaImpl();
    }

    protected Ext extAmbQualifierNodeImpl() {
        return extCannotToJavaImpl();
    }

    protected Ext extAmbReceiverImpl() {
        return extCannotToJavaImpl();
    }

    protected Ext extAmbTypeNodeImpl() {
        return extCannotToJavaImpl();
    }

    protected Ext extArrayAccessImpl() {
        return new JifArrayAccessExt(new ArrayAccessToJavaExt_c());
    }

    protected Ext extArrayInitImpl() {
        return new JifArrayInitExt(new ArrayInitToJavaExt_c());
    }

    protected Ext extLocalAssignImpl() {
        return new JifLocalAssignExt(new LocalAssignToJavaExt_c());
    }
    protected Ext extFieldAssignImpl() {
        return new JifFieldAssignExt(new FieldAssignToJavaExt_c());
    }
    protected Ext extArrayAccessAssignImpl() {
        return new JifArrayAccessAssignExt(new ArrayAccessAssignToJavaExt_c());
    }

    protected Ext extBinaryImpl() {
        return new JifBinaryExt(new BinaryToJavaExt_c());
    }

    protected Ext extBlockImpl() {
        return new JifBlockExt(new BlockToJavaExt_c());
    }

    protected Ext extSwitchBlockImpl() {
        return new JifBlockExt(new SwitchBlockToJavaExt_c());
    }

    protected Ext extBranchImpl() {
        return new JifBranchExt(new BranchToJavaExt_c());
    }

    protected Ext extCallImpl() {
        return new JifCallExt(new CallToJavaExt_c());
    }

    protected Ext extCaseImpl() {
        return new JifCaseExt(new CaseToJavaExt_c());
    }

    protected Ext extCastImpl() {
        return new JifCastExt(new CastToJavaExt_c());
    }
    protected Ext extCatchImpl() {
        return new Jif_c(new CatchToJavaExt_c());
    }

    protected Ext extClassBodyImpl() {
        return new JifClassBodyExt(new ClassBodyToJavaExt_c());
    }

    protected Ext extClassDeclImpl() {
        return new JifClassDeclExt(new ClassDeclToJavaExt_c());
    }

    protected Ext extConditionalImpl() {
        return new JifConditionalExt(new ConditionalToJavaExt_c());
    }

    protected Ext extConstructorCallImpl() {
        return new JifConstructorCallExt(new ConstructorCallToJavaExt_c());
    }

    protected Ext extConstructorDeclImpl() {
        return new JifConstructorDeclExt(new ConstructorDeclToJavaExt_c());
    }

    protected Ext extFieldDeclImpl() {
        return new JifFieldDeclExt_c(new FieldDeclToJavaExt_c());
    }

    protected Ext extDoImpl() {
            return new JifDoExt(new DoToJavaExt_c());
    }

    protected Ext extEmptyImpl() {
        return new JifEmptyExt(new EmptyToJavaExt_c());
    }

    protected Ext extEvalImpl() {
        return new JifEvalExt(new EvalToJavaExt_c());
    }

    protected Ext extFieldImpl() {
        return new JifFieldExt(new FieldToJavaExt_c());
    }

    protected Ext extForImpl() {
            return new JifForExt(new ForToJavaExt_c());
    }

    protected Ext extFormalImpl() {
        return new JifFormalExt(new FormalToJavaExt_c());
    }

    protected Ext extIfImpl() {
        return new JifIfExt(new IfToJavaExt_c());
    }

    protected Ext extImportImpl() {
        return new Jif_c(new ImportToJavaExt_c());
    }

    protected Ext extInitializerImpl() {
        return new JifInitializerExt(new InitializerToJavaExt_c());
    }

    protected Ext extInstanceofImpl() {
            return new JifInstanceofExt(new InstanceOfToJavaExt_c());
    }

    protected Ext extLabeledImpl() {
        return new JifLabeledExt(new LabeledToJavaExt_c());
    }

    protected Ext extLitImpl() {
        return new JifLiteralExt(new LitToJavaExt_c());
    }

    protected Ext extLocalImpl() {
        return new JifLocalExt(new LocalToJavaExt_c());
    }


    protected Ext extLocalDeclImpl() {
        return new JifLocalDeclExt(new LocalDeclToJavaExt_c());
    }

    protected Ext extMethodDeclImpl() {
        return new JifMethodDeclExt(new MethodDeclToJavaExt_c());
    }

    protected Ext extNewImpl() {
        return new JifNewExt(new NewToJavaExt_c());
    }

    protected Ext extNewArrayImpl() {
        return new JifNewArrayExt(new NewArrayToJavaExt_c());
    }

    protected Ext extReturnImpl() {
        return new JifReturnExt(new ReturnToJavaExt_c());
    }

    protected Ext extSourceFileImpl() {
        return new JifSourceFileExt(new SourceFileToJavaExt_c());
    }

    protected Ext extSpecialImpl() {
        return new JifSpecialExt(new SpecialToJavaExt_c());
    }

    protected Ext extSwitchImpl() {
        return new JifSwitchExt(new SwitchToJavaExt_c());
    }

    protected Ext extSynchronizedImpl() {
        return new JifSynchronizedExt(new SynchronizedToJavaExt_c());
    }

    protected Ext extThrowImpl() {
        return new JifThrowExt(new ThrowToJavaExt_c());
    }

    protected Ext extTryImpl() {
            return new JifTryExt(new TryToJavaExt_c());
    }

    protected Ext extArrayTypeNodeImpl() {
        return extCannotToJavaImpl();
    }

    protected Ext extCanonicalTypeNodeImpl() {
        return new Jif_c(new CanonicalTypeNodeToJavaExt_c());
    }

    protected Ext extPackageNodeImpl() {
        return new Jif_c(new PackageNodeToJavaExt_c());
    }

    protected Ext extUnaryImpl() {
        return new JifUnaryExt(new UnaryToJavaExt_c());
    }

    protected Ext extWhileImpl() {
        return new JifWhileExt(new WhileToJavaExt_c());
    }

    protected Ext extInstTypeNodeImpl() {
        return extCannotToJavaImpl();
    }

    protected Ext extLabeledTypeNodeImpl() {
        return extCannotToJavaImpl();
    }

    protected Ext extAmbNewArrayImpl() {
        return extCannotToJavaImpl();
    }

    protected Ext extAmbParamTypeOrAccessImpl() {
        return extCannotToJavaImpl();
    }

    protected Ext extCanonicalLabelNodeImpl() {
        return new Jif_c(new CanonicalLabelNodeToJavaExt_c());
    }

    protected Ext extParamNodeImpl() {
        return extCannotToJavaImpl();
    }



    protected Ext extCanonicalPrincipalNodeImpl() {
        return new JifPrincipalNodeExt(new CanonicalPrincipalNodeToJavaExt_c());
    }


    protected Ext extParamDeclImpl() {
        return extCannotToJavaImpl();
    }

    protected Ext extConstraintNodeImpl() {
        return extCannotToJavaImpl();
    }
    
    protected Ext extDeclassifyStmtImpl() {
        return new JifDeclassifyStmtExt(new DowngradeStmtToJavaExt_c());
    }

    protected Ext extDeclassifyExprImpl() {
        return new JifDeclassifyExprExt(new DowngradeExprToJavaExt_c());
    }

    protected Ext extEndorseStmtImpl() {
        return new JifEndorseStmtExt(new DowngradeStmtToJavaExt_c());
    }

    protected Ext extCheckedEndorseStmtImpl() {
        return new JifCheckedEndorseStmtExt(new DowngradeStmtToJavaExt_c());
    }

    protected Ext extEndorseExprImpl() {
        return new JifEndorseExprExt(new DowngradeExprToJavaExt_c());
    }

    protected Ext extNewLabelImpl() {
        return new JifLabelExprExt(new NewLabelToJavaExt_c());
    }
    protected Ext extLabelExprImpl() {
        return new JifLabelExprExt(new LabelExprToJavaExt_c());
    }
    protected Ext extPrincipalExprImpl() {
        return new JifPrincipalExprExt(new PrincipalExprToJavaExt_c());
    }
}
