package jif.types;

import java.util.List;

import jif.types.label.Label;
import polyglot.types.ProcedureInstance;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;

/** Jif procedure instance. A wrapper of all the type information 
 *  related to a procedure. 
 */
public interface JifProcedureInstance extends ProcedureInstance
{
    Label pcBound();
    Label returnLabel();
    List constraints();
    void setPCBound(Label startLabel, boolean isDefault);
    void setReturnLabel(Label returnLabel, boolean isDefault);
    void setConstraints(List constraints);

    boolean isDefaultPCBound();
    boolean isDefaultReturnLabel();

    String debugString();

    void subst(VarMap bounds);
    void subst(LabelSubstitution subst) throws SemanticException;
    
    ReferenceType container();
}
