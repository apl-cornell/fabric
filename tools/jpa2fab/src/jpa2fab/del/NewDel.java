package jpa2fab.del;

import java.util.Iterator;

import polyglot.ast.Expr;
import polyglot.ast.JL;
import polyglot.ast.JL_c;
import polyglot.ast.Node_c;
import polyglot.util.CodeWriter;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.Translator;
import fabil.ast.New;

/**
 * 
 */
public class NewDel extends JL_c implements JL {
  @Override
  public void translate(CodeWriter w, Translator tr) {
    New n = node();
    if (n.location() == null) {
      super.translate(w, tr);
    } else {
      //XXX: this is basically copied from New_c.prettyPrint
      printQualifier(w, tr);
      w.write("new ");

      // We need to be careful when pretty printing "new" expressions for
      // member classes.  For the expression "e.new C()" where "e" has
      // static type "T", the TypeNode for "C" is actually the type "T.C".
      // But, if we print "T.C", the post compiler will try to lookup "T"
      // in "T".  Instead, we print just "C".
      if (n.qualifier() != null) {
        w.write(n.objectType().name());
      } else {
        ((Node_c) n).print(n.objectType(), w, tr);
      }
      w.write("@");
      ((Node_c) n).print(n.location(), w, tr);
      printArgs(w, tr);
      printBody(w, tr);
    }
  }

  @Override
  public New node() {
    return (New) super.node();
  }

  // protected methods from polyglot.ast.New_c
  protected void printQualifier(CodeWriter w, PrettyPrinter tr) {
    if (node().qualifier() != null && !node().isQualifierImplicit()) {
      ((Node_c) node()).print(node().qualifier(), w, tr);
      w.write(".");
    }
  }

  protected void printArgs(CodeWriter w, PrettyPrinter tr) {
    w.write("(");
    w.allowBreak(2, 2, "", 0);
    w.begin(0);

    for (Iterator<Expr> i = node().arguments().iterator(); i.hasNext();) {
      Expr e = i.next();

      ((Node_c) node()).print(e, w, tr);

      if (i.hasNext()) {
        w.write(",");
        w.allowBreak(0);
      }
    }

    w.end();
    w.write(")");
  }

  protected void printBody(CodeWriter w, PrettyPrinter tr) {
    if (node().body() != null) {
      w.write(" {");
      ((Node_c) node()).print(node().body(), w, tr);
      w.write("}");
    }
  }

}
