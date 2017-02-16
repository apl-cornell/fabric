package bolt.ast;

import static polyglot.ast.Term.ENTRY;
import static polyglot.ast.Term.EXIT;

import java.util.List;

import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.Term;
import polyglot.ast.Term_c;
import polyglot.util.SerialVersionUID;
import polyglot.visit.CFGBuilder;

public class BoltCallExt extends BoltLocatedElementExt {

  private static final long serialVersionUID = SerialVersionUID.generate();

  public BoltCallExt() {
    this(null);
  }

  public BoltCallExt(Expr location) {
    super(location);
  }

  @Override
  public Call node() {
    return (Call) super.node();
  }

  @Override
  public Term firstChild() {
    // Location goes between target and arguments.
    Call c = node();

    if (c.target() instanceof Term) {
      return (Term) c.target();
    }
    if (location != null) {
      return location;
    }

    return Term_c.listChild(c.arguments(), (Expr) null);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    // Location goes between target and arguments.
    Call c = node();

    Term prev = null;
    if (c.target() instanceof Term) {
      prev = (Term) c.target();
    }

    if (prev == null) {
      prev = location;
    } else if (location != null) {
      v.visitCFG(prev, prev = location, ENTRY);
    }

    if (prev == null) {
      v.visitCFGList(c.arguments(), c, EXIT);
    } else {
      if (!c.arguments().isEmpty()) {
        v.visitCFG(prev, Term_c.listChild(c.arguments(), (Expr) null), ENTRY);
        v.visitCFGList(c.arguments(), c, EXIT);
      } else {
        v.visitCFG(prev, c, EXIT);
      }
    }

    return succs;
  }

}
