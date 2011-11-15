package jif.types;

import java.util.Iterator;

import jif.ast.JifProcedureDecl;
import jif.types.label.ArgLabel;
import jif.types.label.Label;
import polyglot.ast.*;
import polyglot.types.Type;
import polyglot.util.Position;

public class FixedSignature implements DefaultSignature
{
    JifTypeSystem ts;

    public FixedSignature(JifTypeSystem ts) {
	this.ts = ts;
    }
    
    public Label defaultPCBound(Position pos, String methodName) {
	return ts.topLabel(pos);
    }

    public Label defaultArgBound(Formal f) {	
        return ts.topLabel(f.position());
    }

    public Label defaultReturnLabel(ProcedureDecl pd) {
	Label Lr = ts.bottomLabel();
	
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
        JifTypeSystem jts = (JifTypeSystem)pi.typeSystem();

        for (Iterator i = pi.formalTypes().iterator(); i.hasNext(); ) {
            Type t = (Type)i.next();
            ArgLabel a = (ArgLabel)jts.labelOfType(t);
            Lrv = ts.join(Lrv, a);
        }
	return Lrv;	
    }

    public Label defaultFieldLabel(FieldDecl fd) {
        return ts.noComponentsLabel();
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
