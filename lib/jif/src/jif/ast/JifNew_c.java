package jif.ast;

import java.util.ArrayList;
import java.util.List;

import jif.extension.LabelTypeCheckUtil;
import jif.types.JifClassType;
import jif.types.JifTypeSystem;
import jif.visit.JifTypeChecker;
import polyglot.ast.*;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;

public class JifNew_c extends New_c implements New
{
    public JifNew_c(Position pos, TypeNode tn, List arguments, 
	    ClassBody body) {
	super(pos, null, tn, arguments, body);
    }
    
    
    public NodeVisitor typeCheckEnter(TypeChecker tc) throws SemanticException {
        JifTypeChecker jtc = (JifTypeChecker)super.typeCheckEnter(tc);
        return jtc.inferClassParameters(true);
    }


    public Node typeCheck(TypeChecker tc) throws SemanticException {
	JifNew_c n = (JifNew_c) super.typeCheck(tc);

	Type t = n.tn.type();
        LabelTypeCheckUtil ltcu = ((JifTypeSystem)tc.typeSystem()).labelTypeCheckUtil(); 
        ltcu.typeCheckType(tc, t);
        
        n = (JifNew_c) n.type(t);
	
	return n;
    }
    public List throwTypes(TypeSystem ts) {
        List ex = new ArrayList(super.throwTypes(ts));
        New n = (New)this.node();
        LabelTypeCheckUtil ltcu = ((JifTypeSystem)ts).labelTypeCheckUtil(); 
        
        if (n.objectType().type() instanceof JifClassType) {
            ex.addAll(ltcu.throwTypes((JifClassType)n.objectType().type()));
        }
        return ex;
    }
}
