package fabil.visit;

import java.util.Collections;

import polyglot.ast.Call;
import polyglot.ast.Cast;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.MethodInstance;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import fabil.ExtensionInfo;
import fabil.ast.FabILNodeFactory;
import fabil.ast.New;
import fabil.types.FabILTypeSystem;

/**
 * Rewrites Principal constructor calls to add default delegations.
 */
public class PrincipalDelegator extends NodeVisitor {
  protected FabILTypeSystem ts;
  protected FabILNodeFactory nf;
  protected QQ qq;

  public PrincipalDelegator(ExtensionInfo extInfo) {
    this.ts = extInfo.typeSystem();
    this.nf = extInfo.nodeFactory();
    this.qq = new QQ(extInfo);
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof New) {
      New newCall = (New) n;
      ClassType delegatingPrincipal = ts.DelegatingPrincipal();
      if (newCall.objectType().type().isSubtype(delegatingPrincipal)) {
        Position pos = Position.compilerGenerated();
        Receiver target = nf.CanonicalTypeNode(pos, ts.DelegatingPrincipal());

        Call call =
            nf.Call(pos, target, nf.Id(pos, "$addDefaultDelegates"), newCall);
        call = (Call) call.type(delegatingPrincipal);

        MethodInstance mi =
            ts.methodInstance(pos, delegatingPrincipal, Flags.PUBLIC
                .set(Flags.STATIC), delegatingPrincipal,
                "$addDefaultDelegates", Collections
                    .singletonList(delegatingPrincipal), Collections
                    .emptyList());
        call = call.methodInstance(mi);

        Cast cast =
            nf.Cast(pos, newCall.objectType(), call);
        cast = (Cast) cast.type(newCall.objectType().type());
        return cast;
      }
    }

    return super.leave(old, n, v);
  }

}
