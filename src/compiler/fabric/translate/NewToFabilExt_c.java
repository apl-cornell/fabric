package fabric.translate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jif.translate.JifToJavaRewriter;
import jif.translate.NewToJavaExt_c;
import jif.types.JifPolyType;
import jif.types.JifSubst;
import jif.types.JifSubstType;
import jif.types.ParamInstance;
import jif.types.label.Label;
import polyglot.ast.Expr;
import polyglot.ast.New;
import polyglot.types.SemanticException;
import fabil.ast.FabILNodeFactory;
import fabric.ast.FabricUtil;
import fabric.extension.NewExt_c;
import fabric.types.FabricClassType;
import fabric.types.FabricTypeSystem;
import fabric.visit.FabricToFabilRewriter;

public class NewToFabilExt_c extends NewToJavaExt_c {
  @SuppressWarnings("unchecked")
  @Override
  public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
    boolean sigMode = ((FabricToFabilRewriter) rw).inSignatureMode();
    New n = (New) node();
    FabricClassType ct = (FabricClassType)objectType.toClass();

    FabricTypeSystem ts = (FabricTypeSystem)rw.jif_ts();
    FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();
    FabricToFabilRewriter ffrw = (FabricToFabilRewriter)rw;
    
    Expr loc = null;
    Expr labelExpr = null;
    Expr labelloc = null;
    
    if (ts.isFabricClass(ct)) {
      // For non-fabric classes, there cannot be location or field label.
      NewExt_c ext = (NewExt_c)FabricUtil.fabricExt(n);
      loc = ext.location();
      labelloc = loc;
      
      Label fieldLabel = ct.defaultFieldLabel();
      if (fieldLabel != null && !sigMode) {
        labelExpr = rw.labelToJava(fieldLabel);
        if (labelloc == null) labelloc = nf.StoreGetter(n.position());
//        if (loc != null)
        labelExpr = ffrw.updateLabelLocation(labelExpr, labelloc);
      }
    }
    
    if (! rw.jif_ts().isParamsRuntimeRep(ct) || (ct instanceof JifSubstType && !rw.jif_ts().isParamsRuntimeRep(((JifSubstType)ct).base()))) {
        // only rewrite creation of classes where params are runtime represented.
        n = nf.New(n.position(), n.qualifier(), n.objectType(),
                   labelExpr, loc,
                   n.arguments(), n.body());
        return n;
    }

    List<Expr> paramargs = new ArrayList<Expr>();
    
    if (ct instanceof JifSubstType && rw.jif_ts().isParamsRuntimeRep(((JifSubstType)ct).base())) {
        // add all the actual param expressions to args
        JifSubstType t = (JifSubstType)ct;
        JifSubst subst = (JifSubst)t.subst();
        JifPolyType base = (JifPolyType)t.base();
        for (Iterator<ParamInstance> iter = base.params().iterator(); iter.hasNext();) {
            ParamInstance pi = iter.next();
            paramargs.add(ffrw.paramToJava(subst.get(pi), labelloc));
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
    }
    else {
        // ct represents params at runtime, but is a Java class with a
        // Jif signature.
      List<Expr> allArgs =
          new ArrayList<Expr>(paramargs.size() + n.arguments().size());
        allArgs.addAll(paramargs);
        allArgs.addAll(n.arguments());
        return rw.qq().parseExpr("new %T(%LE)",
                                 n.objectType(), allArgs);
    }
  }
}
