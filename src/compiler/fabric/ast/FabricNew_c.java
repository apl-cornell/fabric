package fabric.ast;

import java.util.List;

import jif.ast.JifNew_c;
import polyglot.ast.ClassBody;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.Term;
import polyglot.ast.Term_c;
import polyglot.ast.TypeNode;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import fabric.extension.FabricExt;
import fabric.extension.LocatedExt_c;

//XXX Should be replaced with extension
@Deprecated
public class FabricNew_c extends JifNew_c {

  @Deprecated
  public FabricNew_c(Position pos, Expr outer, TypeNode tn,
      List<Expr> arguments, ClassBody body) {
    this(pos, outer, tn, arguments, body, null);
  }

  public FabricNew_c(Position pos, Expr outer, TypeNode tn,
      List<Expr> arguments, ClassBody body, Ext ext) {
    super(pos, outer, tn, arguments, body, ext);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    FabricExt fabExt = FabricUtil.fabricExt(this);
    Expr location = ((LocatedExt_c) fabExt).location();
    if (qualifier != null) {
      v.visitCFG(qualifier, objectType, ENTRY);
    }

    Term last = objectType;
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
        v.visitCFG(last, Term_c.<Expr, Expr, Expr> listChild(arguments, null),
            ENTRY);
        v.visitCFGList(arguments, this, EXIT);
      } else {
        v.visitCFG(last, this, EXIT);
      }
    }

    return succs;
  }

}
