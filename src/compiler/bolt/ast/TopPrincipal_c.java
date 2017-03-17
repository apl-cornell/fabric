package bolt.ast;

import java.util.List;

import polyglot.ast.Ext;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;
import polyglot.visit.CFGBuilder;
import polyglot.visit.PrettyPrinter;

public class TopPrincipal_c extends Principal_c implements TopPrincipal {

  private static final long serialVersionUID = SerialVersionUID.generate();

  public TopPrincipal_c(Position pos) {
    this(pos, null);
  }

  public TopPrincipal_c(Position pos, Ext ext) {
    super(pos, ext);
  }

  @Override
  public Term firstChild() {
    return null;
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    return succs;
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
    // TODO Auto-generated method stub
    int TODO;
    super.prettyPrint(w, pp);
  }

  @Override
  public void dump(CodeWriter w) {
    // TODO Auto-generated method stub
    int TODO;
    super.dump(w);
  }

  @Override
  public String toString() {
    // TODO Auto-generated method stub
    int TODO;
    return super.toString();
  }

}
