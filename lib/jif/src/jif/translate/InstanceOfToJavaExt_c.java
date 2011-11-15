package jif.translate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jif.extension.JifInstanceOfDel;
import jif.types.JifPolyType;
import jif.types.JifSubst;
import jif.types.JifSubstType;
import jif.types.ParamInstance;
import polyglot.ast.Instanceof;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.visit.NodeVisitor;

public class InstanceOfToJavaExt_c extends ToJavaExt_c {
    private Type compareType;

    public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
        Instanceof io = (Instanceof)this.node();
        this.compareType = io.compareType().type();
        return super.toJavaEnter(rw);
    }

    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Instanceof io = (Instanceof)this.node();
        if (!((JifInstanceOfDel)io.del()).isToSubstJifClass()) {
            return rw.java_nf().Instanceof(io.position(), io.expr(), io.compareType());
        }

        List args = new ArrayList();

        // add all the actual param expressions to args
        JifSubstType t = (JifSubstType)compareType;
        JifSubst subst = (JifSubst)t.subst();
        JifPolyType base = (JifPolyType)t.base();
        for (Iterator iter = base.params().iterator(); iter.hasNext(); ) {
            ParamInstance pi = (ParamInstance)iter.next();
            args.add(rw.paramToJava(subst.get(pi)));
        }

        // add the actual expression being cast.
        args.add(io.expr());

        String jifImplClass = ((JifSubstType)compareType).fullName();
        if (((JifSubstType)compareType).flags().isInterface()) {
            jifImplClass = ClassDeclToJavaExt_c.interfaceClassImplName(jifImplClass);
        }
        return rw.qq().parseExpr(jifImplClass + "." + ClassDeclToJavaExt_c.INSTANCEOF_METHOD_NAME + "(%LE)", (Object)args);
    }
}
