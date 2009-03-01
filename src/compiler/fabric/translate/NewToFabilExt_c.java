package fabric.translate;

import java.util.*;

import fabil.ast.FabILNodeFactory;
import fabric.ast.FabricUtil;
import fabric.extension.NewExt_c;
import fabric.types.FabricClassType;
import fabric.types.FabricParsedClassType;
import fabric.types.FabricTypeSystem;

import polyglot.ast.Expr;
import polyglot.ast.New;
import polyglot.types.ClassType;
import polyglot.types.SemanticException;
import jif.translate.JifToJavaRewriter;
import jif.translate.NewToJavaExt_c;
import jif.types.JifPolyType;
import jif.types.JifSubst;
import jif.types.JifSubstType;
import jif.types.ParamInstance;
import jif.types.label.Label;

public class NewToFabilExt_c extends NewToJavaExt_c {
  @Override
  public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
    New n = (New) node();
    FabricClassType ct = (FabricClassType)objectType.toClass();

    FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();
    FabricTypeSystem ts = (FabricTypeSystem)rw.jif_ts();

    NewExt_c ext = (NewExt_c)FabricUtil.fabricExt(n);
    Expr loc = ext.location();
    
    Label fieldLabel = ct.defaultFieldLabel();
    Expr labelExpr = null;
    if (fieldLabel != null) {
      labelExpr = rw.labelToJava(fieldLabel);
    }
    
    if (! rw.jif_ts().isParamsRuntimeRep(ct) || (ct instanceof JifSubstType && !rw.jif_ts().isParamsRuntimeRep(((JifSubstType)ct).base()))) {
        // only rewrite creation of classes where params are runtime represented.
        n = nf.New(n.position(), n.qualifier(), n.objectType(),
                   labelExpr, loc,
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
                             labelExpr, loc,
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
