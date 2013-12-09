package jpa2fab.del;

import java.util.Iterator;

import polyglot.ast.Expr;
import polyglot.ast.JLDel;
import polyglot.ast.JLDel_c;
import polyglot.ast.Node_c;
import polyglot.util.CodeWriter;
import polyglot.visit.Translator;
import fabil.ast.FabILCall;

/**
 * 
 */
public class CallDel extends JLDel_c implements JLDel {

  @Override
  public void translate(CodeWriter w, Translator tr) {
    FabILCall call = (FabILCall) node();
    if (call.remoteWorker() == null) {
      super.translate(w, tr);
    } else {
      //XXX: this is basically copied from Call_c.translate
      if (!call.isTargetImplicit()) {
        if (call.target() instanceof Expr) {
          call.printSubExpr((Expr) call.target(), w, tr);
        } else if (call.target() != null) {
          //XXX: ugh. print isn't part of the Node interface. 
          ((Node_c) call).print(call.target(), w, tr);
        }
        w.write(".");
        w.allowBreak(2, 3, "", 0);
      }

      w.begin(0);
      w.write(call.name() + "@");
      ((Node_c) call).print(call.remoteWorker(), w, tr);
      w.write("(");
      if (call.arguments().size() > 0) {
        w.allowBreak(2, 2, "", 0); // miser mode
        w.begin(0);

        for (Iterator<Expr> i = call.arguments().iterator(); i.hasNext();) {
          Expr e = i.next();
          ((Node_c) call).print(e, w, tr);

          if (i.hasNext()) {
            w.write(",");
            w.allowBreak(0, " ");
          }
        }

        w.end();
      }
      w.write(")");
      w.end();

    }
  }
}
