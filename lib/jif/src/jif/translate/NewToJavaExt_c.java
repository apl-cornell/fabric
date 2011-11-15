package jif.translate;

import java.util.*;

import jif.types.*;
import polyglot.ast.Expr;
import polyglot.ast.New;
import polyglot.types.*;
import polyglot.visit.NodeVisitor;

public class NewToJavaExt_c extends ExprToJavaExt_c {
    protected Type objectType;

    public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
        New n = (New)this.node();
        this.objectType = n.objectType().type();
        return super.toJavaEnter(rw);
    }

    public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
        New n = (New) node();
        ClassType ct = objectType.toClass();

        if (! rw.jif_ts().isParamsRuntimeRep(ct) || (ct instanceof JifSubstType && !rw.jif_ts().isParamsRuntimeRep(((JifSubstType)ct).base()))) {
            // only rewrite creation of classes where params are runtime represented.
            n = rw.java_nf().New(n.position(), n.qualifier(), n.objectType(),
                                n.arguments(), n.body());
            return n;
        }

        List paramargs = new ArrayList();
        
        if (ct instanceof JifSubstType && rw.jif_ts().isParamsRuntimeRep(((JifSubstType)ct).base())) {
            // add all the actual param expressions to args
            JifSubstType t = (JifSubstType)ct;
            JifSubst subst = (JifSubst)t.subst();
            JifPolyType base = (JifPolyType)t.base();
            for (Iterator iter = base.params().iterator(); iter.hasNext(); ) {
                ParamInstance pi = (ParamInstance)iter.next();
                paramargs.add(rw.paramToJava(subst.get(pi)));
            }
        }

        // use the appropriate string for the constructor invocation.
        if (rw.jif_ts().isJifClass(ct)) {            
            String name = ClassDeclToJavaExt_c.constructorTranslatedName(ct);
            return rw.qq().parseExpr("new %T(%LE).%s(%LE)",
                                     n.objectType(), paramargs, name, n.arguments());
        }
        else {
            // ct represents params at runtime, but is a Java class with a
            // Jif signature.
            List allArgs = new ArrayList(paramargs.size() + n.arguments().size());
            allArgs.addAll(paramargs);
            allArgs.addAll(n.arguments());
            return rw.qq().parseExpr("new %T(%LE)",
                                     n.objectType(), allArgs);
        }
    }
}
