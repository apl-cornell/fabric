package jif.ast;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import jif.extension.LabelTypeCheckUtil;
import jif.types.*;
import jif.types.label.Label;
import jif.types.label.VarLabel;
import jif.types.principal.VarPrincipal;
import jif.visit.JifTypeChecker;
import polyglot.ast.CanonicalTypeNode_c;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.TypeChecker;


/**
 * A <code>JifCanonicalTypeNode</code> is a type node for a canonical type in Polyj.
 */
public class JifCanonicalTypeNode_c extends CanonicalTypeNode_c implements JifCanonicalTypeNode {
    public JifCanonicalTypeNode_c(Position pos, Type type) {
        super(pos, type);
    }

    public boolean isDisambiguated() {
        return true;
    }    
    
    protected Type typeCheck(Type t, TypeChecker tc) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem) t.typeSystem();
        if (ts.isLabeled(t)) {
            Label labelPart = ts.labelOfType(t);
            Type typePart = ts.unlabel(t);
            Type newTypePart = typeCheck(typePart, tc);
            if (newTypePart == typePart) return t;
            return ts.labeledType(t.position(), newTypePart, labelPart);
        }

        if (t.isArray()) {
            ArrayType at = t.toArray();
            Type newBaseType = typeCheck(at.base(), tc);
            if (newBaseType == at.base()) return t;
            return at.base(newBaseType);
        }

        if (t instanceof JifPolyType && !((JifPolyType)t).params().isEmpty()) {
            // the type is missing parameters
            
            JifPolyType jpt = (JifPolyType)t;
            
            JifTypeChecker jtc = (JifTypeChecker)tc;
            boolean inferred = false;
            if (jtc.inferClassParameters()) {
                inferred = true;
                
                // infer the class parameters by parameterizing the type with
                // label variables.
                Map varSubst = new LinkedHashMap();

                for (Iterator iter = jpt.params().iterator(); iter.hasNext();) {
                    ParamInstance pi = (ParamInstance)iter.next();
                    if (pi.isLabel()) {
                        VarLabel v = ts.freshLabelVariable(t.position(), pi.name()+"_inferred", "Inferred label parameter");
                        // mark the var label as needing to be runtime representable.
                        v.setMustRuntimeRepresentable();
                        varSubst.put(pi, v);
                    }
                    else {
                        VarPrincipal v = ts.freshPrincipalVariable(t.position(), pi.name()+"_inferred", "Inferred principal parameter");
                        // mark the var label as needing to be runtime representable.
                        v.setMustRuntimeRepresentable();
                        varSubst.put(pi, v);                        
                    }
                }                
                t = (ClassType)ts.subst(jpt, varSubst);                
            }
            
            if (!inferred) {
                throw new SemanticDetailedException(
                        "Parameterized type " + t + " is uninstantiated",
                        "The type " + t + " is a parameterized type, " +
                                        "and must be provided with parameters " +
                                        "to instantiate it. Jif prevents the use of " +
                                        "uninstantiated parameterized types.",
                                            position());
            }
            return t;
        }
        return t;
        
    }
    
    
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        if (!this.type().isCanonical()) {
            // type should be canonical by the time we start typechecking.
            throw new InternalCompilerError(this.type() + " is not canonical.", this.position);
        }

        TypeNode tn = (TypeNode) super.typeCheck(tc);
        
        JifTypeSystem ts = (JifTypeSystem) tn.type().typeSystem();
        Type t = typeCheck(tn.type(), tc);
        
        if (t != tn.type()) {
            // update the typenode.
            tn = tn.type(t);
        }

        // typecheck the type, make sure principal parameters are instantiated 
        // with principals, label parameters with labels.
        LabelTypeCheckUtil ltcu = ((JifTypeSystem)tc.typeSystem()).labelTypeCheckUtil(); 
        ltcu.typeCheckType(tc, t);
        
        return tn;
        
    }
}