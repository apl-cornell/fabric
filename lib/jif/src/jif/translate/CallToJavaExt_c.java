package jif.translate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jif.types.*;
import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.types.ArrayType;
import polyglot.types.MethodInstance;
import polyglot.types.SemanticException;

public class CallToJavaExt_c extends ExprToJavaExt_c {
    public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
        JifTypeSystem ts = rw.jif_ts();
        Call n = (Call) node();

        if (n.name().equals("clone") && n.methodInstance().container().isArray()) {
            ArrayType at = n.methodInstance().container().toArray();
            if (at.base().isArray()) {
                return rw.qq().parseExpr("(%T)" + ts.RuntimePackageName()
                    + ".Runtime.arrayDeepClone(%E)",
                    rw.typeToJava(at, at.position()), n.target());                
            }
            return rw.qq().parseExpr("(%T)%E.clone()", rw.typeToJava(at, at.position()), n.target());
        }
        
        List args = new ArrayList();
        
        MethodInstance mi = n.methodInstance();
        // for static methods of Jif classes, add args for the params of the class
        if (mi.flags().isStatic() && 
                mi.container() instanceof JifSubstType && 
                rw.jif_ts().isParamsRuntimeRep(((JifSubstType)mi.container()).base())) {
            JifSubstType t = (JifSubstType)mi.container();
            JifSubst subst = (JifSubst)t.subst();
            JifPolyType base = (JifPolyType)t.base();
            for (Iterator iter = base.params().iterator(); iter.hasNext(); ) {
                ParamInstance pi = (ParamInstance)iter.next();
                args.add(rw.paramToJava(subst.get(pi)));            
            }
        }
        args.addAll(n.arguments());
                
        n = rw.java_nf().Call(n.position(), n.target(), n.id(), args);
        return n;
    }
}
