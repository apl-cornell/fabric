package jpa2fab.ext;

import java.util.Collections;

import polyglot.ast.Expr;
import polyglot.ast.New;
import polyglot.ast.Node;
import polyglot.ext.jl5.types.JL5ClassType;
import polyglot.translate.ExtensionRewriter;
import polyglot.translate.ext.NewToExt_c;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;
import fabil.ast.FabILNodeFactory;

public class NewToFabIL_c extends NewToExt_c {
  JL5ClassType jl5Type;

  @Override
  public NodeVisitor toExtEnter(ExtensionRewriter rw) throws SemanticException {
    New old = (New) node();
    jl5Type = (JL5ClassType) old.objectType().type().toClass();
    return rw;
  }

  @Override
  public Node toExt(ExtensionRewriter rw) throws SemanticException {
    JPA2FabILRewriter j2f = (JPA2FabILRewriter) rw;
    New n = (New) super.toExt(rw);

    if (j2f.isPersistent(jl5Type) || j2f.isCollection(jl5Type)) {
      Expr location;
      FabILNodeFactory nf = (FabILNodeFactory) rw.to_nf();
      if (j2f.inEntity() && !j2f.context().inStaticContext()) {
        // Just let location be the default
        location = null;
      } else {
        location = j2f.createLocationExpr();
      }
      New defCtor =
          nf.New(n.position(), n.qualifier(), n.objectType(), location,
              Collections.<Expr> emptyList(), n.body());
      String name =
          j2f.isCollection(jl5Type) ? j2f.fabricUtilNameFor(jl5Type) : jl5Type
              .fullName();
      if (jl5Type.flags().isInterface()) {
        // creating an anonymous class from an interface.
        // use fabric.lang.Object's method
        name = "fabric.lang.Object";
        //XXX: also need a cast here!
      }
      return rw.qq().parseExpr("%E.%s(%LE)", defCtor,
          name.replace(".", "$") + "$", n.arguments());
    } else {
      return n;
    }
  }
}
