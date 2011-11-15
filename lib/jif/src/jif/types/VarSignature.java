package jif.types;

import java.util.Iterator;

import jif.ast.JifProcedureDecl;
import jif.types.label.ArgLabel;
import jif.types.label.Label;
import polyglot.ast.*;
import polyglot.types.Type;
import polyglot.util.Position;

public class VarSignature implements DefaultSignature
{
    JifTypeSystem ts;

    public VarSignature(JifTypeSystem ts) {
	this.ts = ts;
    }
    
    public Label defaultPCBound(Position pos, String methodName) {
	return ts.freshLabelVariable(pos, methodName, 
                 "start label for the method " + methodName);
    }

    public Label defaultArgBound(Formal f) {
        String argName = f.name();
	return ts.freshLabelVariable(f.position(), argName,
                    "upper bound for the formal argument " + argName);
    }

    public Label defaultReturnLabel(ProcedureDecl pd) {
	Label Lr = ts.noComponentsLabel();
	
	for (Iterator i = pd.throwTypes().iterator(); i.hasNext(); ) {
	    TypeNode tn = (TypeNode) i.next();
	    Label excLabel = ts.labelOfType(tn.type(), ts.bottomLabel());
	    Lr = ts.join(Lr, excLabel);	
	}	

	return Lr;
    }

    public Label defaultReturnValueLabel(ProcedureDecl pd) {
	JifProcedureDecl jpd = (JifProcedureDecl) pd;

	Label Lrv;
        if (jpd.returnLabel() != null) 
            Lrv = jpd.returnLabel().label();
        else
            Lrv = defaultReturnLabel(pd);
        
        
        JifProcedureInstance pi = (JifProcedureInstance)pd.procedureInstance();
        for (Iterator i = pi.formalTypes().iterator(); i.hasNext(); ) {
            Type t = (Type)i.next();
            ArgLabel a = (ArgLabel)ts.labelOfType(t);
            Lrv = ts.join(Lrv, a);
        }
        
	return Lrv;	
    }

    public Label defaultFieldLabel(FieldDecl fd) {
        return ts.bottomLabel();
    }
    
    public Label defaultArrayBaseLabel(Type baseType) {
        if (baseType.isArray()) {
            // default label is the same label as the ultimate base
            if (ts.isLabeled(baseType.toArray().ultimateBase())) {
                return ts.labelOfType(baseType.toArray().ultimateBase());
            }
        }
        return ts.noComponentsLabel();
    }    
}
