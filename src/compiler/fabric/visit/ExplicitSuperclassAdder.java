package fabric.visit;

import fabric.types.FabricTypeSystem;
import polyglot.ast.ClassDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.types.Flags;
import polyglot.visit.NodeVisitor;

/**
 * A Visitor that replaces all implicit superclasses with explicit extends
 * clauses.
 */
public class ExplicitSuperclassAdder extends NodeVisitor {
  protected FabricTypeSystem ts;
  protected NodeFactory nf;

  public ExplicitSuperclassAdder(FabricTypeSystem ts, NodeFactory nf) {
    this.ts = ts;
    this.nf = nf;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof ClassDecl) {
      ClassDecl cd = (ClassDecl) n;

      // XXX: we want to avoid adding an explicit superclass for f.l.Object and
      // j.l.Object; here we just avoid it for any class named "Object"
      if (!cd.name().equals("Object") && !cd.flags().contains(Flags.INTERFACE)
          && cd.superClass() == null)
        n = cd.superClass(nf.CanonicalTypeNode(cd.position(), ts.FObject()));
    }

    return n;
  }
}
