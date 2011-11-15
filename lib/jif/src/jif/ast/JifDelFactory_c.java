package jif.ast;

import jif.extension.*;
import polyglot.ast.AbstractDelFactory_c;
import polyglot.ast.JL;

/**
 * Constructs Jif delegates
 **/
public class JifDelFactory_c extends AbstractDelFactory_c implements JifDelFactory {

    protected JifDelFactory_c() {
        super();
    }

    protected JL delAssignImpl() {
        return new JifAssignDel();
    }
    protected JL delArrayAccessImpl() {
        return new JifArrayAccessDel();
    }
    protected JL delArrayAccessAssignImpl() {
        return new JifArrayAccessAssignDel();
    }

    protected JL delArrayInitImpl() {
        return new JifArrayInitDel();
    }

    protected JL delBinaryImpl() {
        return new JifBinaryDel();
    }
    protected JL delCallImpl() {
        return new JifCallDel();
    }
    protected JL delCastImpl() {
        return new JifCastDel();
    }
    protected JL delCatchImpl() {
        return new JifCatchDel();
    }
    protected JL delClassDeclImpl() {
        return new JifClassDeclDel();
    }
    protected JL delFieldDeclImpl() {
        return new JifFieldDeclDel();
    }
    protected JL delFieldImpl() {
        return new JifFieldDel();
    }
    protected JL delFieldAssignImpl() {
        return new JifFieldAssignDel();
    }
    protected JL delFormalImpl() {
        return new JifFormalDel();
    }
    protected JL delIfImpl() {
        return new JifIfDel();
    }
    protected JL delInitializerImpl() {
        return new JifInitializerDel();
    }
    protected JL delInstanceofImpl() {
        return new JifInstanceOfDel();
    }
    protected JL delLocalDeclImpl() {
        return new JifLocalDeclDel();
    }

    protected JL delNewArrayImpl() {
        return new JifNewArrayDel();
    }

    protected JL delThrowImpl() {
        return new JifThrowDel();
    }
    protected JL delTypeNodeImpl() {
        return new JifTypeNodeDel();
    }

    protected JL delConstructorCallImpl() {
        return new JifConstructorCallDel();
    }
    protected JL delMethodDeclImpl() {
        return new JifMethodDeclDel();
    }
    protected JL delConstructorDeclImpl() {
        return new JifProcedureDeclDel();
    }

    public final JL delAmbNewArray() {
    	JL e = delAmbNewArrayImpl();
    	
    	if (nextDelFactory() != null && nextDelFactory() instanceof JifDelFactory) {
    		JL e2 = ((JifDelFactory) nextDelFactory()).delAmbNewArray();
    		e = composeDels(e, e2);
    	}
    	
    	return postDelAmbNewArray(e);
    }
    
    public final JL delLabelExpr() {
        JL e = delLabelExprImpl();

        if (nextDelFactory() != null && nextDelFactory() instanceof JifDelFactory) {
            JL e2 = ((JifDelFactory)nextDelFactory()).delLabelExpr();
            e = composeDels(e, e2);
        }
        return postDelLabelExpr(e);
    }



    public final JL delNewLabel() {
        JL e = delNewLabelImpl();

        if (nextDelFactory() != null && nextDelFactory() instanceof JifDelFactory) {
            JL e2 = ((JifDelFactory)nextDelFactory()).delLabelExpr();
            e = composeDels(e, e2);
        }
        return postDelNewLabel(e);
    }

    protected JL delAmbNewArrayImpl() {
    	return delAmbExprImpl();
    }
    
    protected JL postDelAmbNewArray(JL e) {
    	return postDelAmbExpr(e);
    }
    
    protected JL delLabelExprImpl() {
        return delExprImpl();
    }
    protected JL postDelLabelExpr(JL e) {
        return postDelExpr(e);
    }
    protected JL delNewLabelImpl() {
        return delLabelExprImpl();
    }
    protected JL postDelNewLabel(JL e) {
        return postDelLabelExpr(e);
    }

}
