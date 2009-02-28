package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import fabric.ast.FabricUtil;
import fabric.extension.NewLabelExt_c;
import polyglot.ast.*;
import polyglot.types.SemanticException;
import jif.translate.JifToJavaRewriter;
import jif.translate.NewLabelToJavaExt_c;

public class NewLabelToFabilExt_c extends NewLabelToJavaExt_c {
  @SuppressWarnings("unchecked")
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    NewLabelExt_c ext = (NewLabelExt_c)FabricUtil.fabricExt(node());
    Expr loc = ext.location();
    
    Node n = super.toJava(rw);
    
    if (loc != null && n instanceof Call) {
      // Add loc as the first parameter.
      Call c = (Call)n;
      List<Expr> newArgs = new ArrayList<Expr>(c.arguments().size() + 1);
      newArgs.add(loc);
      newArgs.addAll(c.arguments());
      c = (Call)c.arguments(newArgs);
      return c;
    }
    
    return n;
  }
}
