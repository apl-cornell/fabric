package fabric.visit;

import java.util.Collections;

import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.Flags;
import polyglot.types.MethodInstance;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;
import fabric.types.FabricTypeSystem;

public class CoercePrincipals extends AscriptionVisitor {
  public CoercePrincipals(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }

  /**
   * @throws SemanticException
   */
  @Override
  public Expr ascribe(Expr e, Type toType) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) typeSystem();
    if (ts.isPrincipal(toType)
        && (ts.typeEquals(ts.Worker(), e.type())
            || ts.typeEquals(ts.RemoteWorker(), e.type()) || ts.typeEquals(
                ts.Store(), e.type()))) {
      Call result =
          nf.Call(e.position(), e,
              nf.Id(Position.compilerGenerated(), "getPrincipal"));
      MethodInstance mi =
          ts.methodInstance(e.position(), (ReferenceType) ts.unlabel(e.type()),
              Flags.PUBLIC, ts.Principal(), "getPrincipal",
              Collections.<Type> emptyList(), Collections.<Type> emptyList());
      return result.methodInstance(mi).type(toType);
    }
    return e;
  }
}
