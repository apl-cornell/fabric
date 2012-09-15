package fabric.ast;

import java.util.List;

import jif.ast.JifNew_c;
import polyglot.ast.ClassBody;
import polyglot.ast.Expr;
import polyglot.ast.Term;
import polyglot.ast.TypeNode;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import fabric.extension.FabricExt;
import fabric.extension.LocatedExt_c;

public class FabricNew_c extends JifNew_c {

  public FabricNew_c(Position pos, TypeNode tn, List<Expr> arguments,
      ClassBody body) {
    super(pos, tn, arguments, body);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    FabricExt fabExt = FabricUtil.fabricExt(this);
    Expr location = ((LocatedExt_c) fabExt).location();
    if (qualifier != null) {
      v.visitCFG(qualifier, tn, ENTRY);
    }

    Term last = tn;
    // if (label != null) {
    // v.visitCFG(last, label, ENTRY);
    // last = label;
    // }

    if (location != null) {
      v.visitCFG(last, location, ENTRY);
      last = location;
    }

    if (body() != null) {
      v.visitCFG(last, listChild(arguments, body()), ENTRY);
      v.visitCFGList(arguments, body(), ENTRY);
      v.visitCFG(body(), this, EXIT);
    } else {
      if (!arguments.isEmpty()) {
        v.visitCFG(last, listChild(arguments, null), ENTRY);
        v.visitCFGList(arguments, this, EXIT);
      } else {
        v.visitCFG(last, this, EXIT);
      }
    }

    return succs;
  }

}
