package bolt.ast;

import static polyglot.ast.Term.ENTRY;
import static polyglot.ast.Term.EXIT;

import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.New;
import polyglot.ast.Term;
import polyglot.ast.Term_c;
import polyglot.util.SerialVersionUID;
import polyglot.visit.CFGBuilder;

public class BoltNewExt extends BoltLocatedElementExt {

  private static final long serialVersionUID = SerialVersionUID.generate();

  public BoltNewExt() {
    this(null);
  }

  public BoltNewExt(Expr location) {
    super(location);
  }

  @Override
  public New node() {
    return (New) super.node();
  }

  @Override
  public Term firstChild() {
    // Location goes between qualifier and object type.
    New n = node();
    if (n.qualifier() != null) {
      return n.qualifier();
    }

    if (location != null) {
      return location;
    }

    return n.objectType();
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    // Location goes between qualifier and object type.
    New n = node();

    Term prev = n.qualifier();

    if (prev == null) {
      prev = location;
    } else if (location != null) {
      v.visitCFG(prev, prev = location, ENTRY);
    }

    if (prev != null) {
      v.visitCFG(prev, n.objectType(), ENTRY);
    }

    if (n.body() != null) {
      v.visitCFG(n.objectType(), Term_c.listChild(n.arguments(), n.body()),
          ENTRY);
      v.visitCFGList(n.arguments(), n.body(), ENTRY);
      v.visitCFG(n.body(), n, EXIT);
    } else {
      if (!n.arguments().isEmpty()) {
        v.visitCFG(n.objectType(), Term_c.listChild(n.arguments(), null),
            ENTRY);
        v.visitCFGList(n.arguments(), n, EXIT);
      } else {
        v.visitCFG(n.objectType(), n, EXIT);

      }
    }

    return succs;
  }

}
