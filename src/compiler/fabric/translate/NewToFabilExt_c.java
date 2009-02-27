package fabric.translate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fabil.ast.FabILNodeFactory;
import fabric.extension.NewExt_c;

import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.New;
import polyglot.types.ClassType;
import polyglot.types.SemanticException;
import jif.translate.JifToJavaRewriter;
import jif.translate.NewToJavaExt_c;
import jif.types.JifPolyType;
import jif.types.JifSubst;
import jif.types.JifSubstType;
import jif.types.ParamInstance;

public class NewToFabilExt_c extends NewToJavaExt_c {
  public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
    New n = (New) node();
    ClassType ct = objectType.toClass();

    FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();

    Ext jifExt = n.ext();
    NewExt_c ext = (NewExt_c)jifExt.ext();
    Expr loc = ext.location();
    
    // TODO pass on the field label.
    
    if (! rw.jif_ts().isParamsRuntimeRep(ct) || (ct instanceof JifSubstType && !rw.jif_ts().isParamsRuntimeRep(((JifSubstType)ct).base()))) {
        // only rewrite creation of classes where params are runtime represented.
        n = nf.New(n.position(), n.qualifier(), n.objectType(),
                   null, loc,
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
        String name = ClassDeclToFabilExt_c.jifConstructorTranslatedName(ct);
        New newExpr = nf.New(n.position(), n.qualifier(), n.objectType(), 
                             null, loc,
                             paramargs);
        return rw.qq().parseExpr("%E.%s(%LE)",
                                 newExpr, name, n.arguments());
//        return rw.qq().parseExpr("new %T@%E(%LE).%s(%LE)",
//            n.objectType(), loc, paramargs, name, n.arguments());
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
