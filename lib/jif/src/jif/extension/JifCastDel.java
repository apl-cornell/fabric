package jif.extension;

import java.util.*;

import jif.types.*;
import jif.visit.JifTypeChecker;
import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;

/** The Jif extension of the <code>Cast</code> node.
 *
 *  @see polyglot.ast.Cast_c
 */
public class JifCastDel extends JifJL_c implements JifPreciseClassDel
{
    public JifCastDel() { }

    private Set exprPreciseClasses = null;
    private boolean isToSubstJifClass = false;

    public boolean isToSubstJifClass() { return this.isToSubstJifClass; }

    public NodeVisitor typeCheckEnter(TypeChecker tc) throws SemanticException {
        JifTypeChecker jtc = (JifTypeChecker)super.typeCheckEnter(tc);
        return jtc.inferClassParameters(true);
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
        // prevent casting to arrays of parameterized types
        Cast c = (Cast)this.node();
        JifTypeSystem ts = (JifTypeSystem)tc.typeSystem();
        Type castType = c.castType().type();

        if (ts.isLabeled(castType)) {
            throw new SemanticException("Cannot cast to a labeled type.", c.position());
        }

        if (!ts.isParamsRuntimeRep(castType)) {
            if ((castType instanceof JifSubstType && !((JifSubstType)castType).actuals().isEmpty()) ||
                (castType instanceof JifPolyType && !((JifPolyType)castType).params().isEmpty()))                    
            throw new SemanticException("Cannot cast to " + castType +
                                        ", since it does " +
                                        "not represent the parameters at runtime.", 
                                        c.position());
        }
        
        if (castType.isArray()) {
            throw new SemanticException("Jif does not currently support casts to arrays.", c.position());
//            while (castType.isArray()) {
//                castType = ts.unlabel(castType.toArray().base());
//            }
//            if (castType instanceof JifSubstType && ((JifSubstType)castType).entries().hasNext()) {
//                throw new SemanticException("Jif does not currently support casts to an array of a parameterized type.", c.position());
//            }
        }

        this.isToSubstJifClass = (castType instanceof JifSubstType && !((JifSubstType)castType).actuals().isEmpty());

        ts.labelTypeCheckUtil().typeCheckType(tc, castType);
        return super.typeCheck(tc);
    }
    
    public List throwTypes(TypeSystem ts) {
        Cast c = (Cast)this.node();

        List ex = new ArrayList(super.throwTypes(ts));
        if (!throwsClassCastException()) {
            ex.remove(ts.ClassCastException());            
        }
        if (c.castType().type() instanceof JifClassType) {
            LabelTypeCheckUtil ltcu = ((JifTypeSystem)ts).labelTypeCheckUtil();
            ex.addAll(ltcu.throwTypes((JifClassType)c.castType().type()));
        }
        return ex;
    }
    
    
    public boolean throwsClassCastException() {
        Cast c = (Cast)this.node();
        Type castType = c.castType().type();
        JifTypeSystem ts = (JifTypeSystem)castType.typeSystem();
        if (exprPreciseClasses != null) {
            for (Iterator iter = exprPreciseClasses.iterator(); iter.hasNext(); ) {
                Type t = (Type)iter.next();
                if (typeCastGuaranteed(ts, castType, t)) {
                    return false;
                }
            }
        }
        if (typeCastGuaranteed(ts, castType, c.expr().type())) {
            return false;
        }
        
        return c.castType().type() instanceof JifClassType;
    }

    /**
     * Will casting from exprType to castType always succeed?
     */
    private static boolean typeCastGuaranteed(JifTypeSystem ts, Type castType, Type exprType) {
        if (ts.equalsNoStrip(castType, exprType)) {
            return true;
        }
        if (castType instanceof JifClassType &&
                SubtypeChecker.polyTypeForClass((JifClassType)castType).params().isEmpty()) {            
            // cast type is not parameterized.
            if (!(exprType instanceof JifClassType) || SubtypeChecker.polyTypeForClass((JifClassType)exprType).params().isEmpty()) {
                // if the expr is definitely a subtype of the 
                // cast type, no class cast exception will be throw.
                if (castType.typeSystem().isSubtype(exprType, castType)) {
                    return true;
                }
            }
        }        
        return false;
    }
    /**
     * 
     */
    public Expr getPreciseClassExpr() {
        return ((Cast)node()).expr();
    }
    /**
     * 
     */
    public void setPreciseClass(Set preciseClasses) {
        this.exprPreciseClasses = preciseClasses;
    }
}
