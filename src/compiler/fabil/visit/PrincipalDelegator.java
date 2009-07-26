package fabil.visit;

import java.util.Collections;
import java.util.List;

import polyglot.ast.*;
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
import fabric.translate.ClassDeclToFabilExt_c;

/**
 * Rewrites Principal constructor calls to add default delegations.
 */
public class PrincipalDelegator extends NodeVisitor {
  protected FabILTypeSystem ts;
  protected FabILNodeFactory nf;
  protected QQ qq;

  protected final ClassType delegatingPrincipal;

  public PrincipalDelegator(ExtensionInfo extInfo) {
    this.ts = extInfo.typeSystem();
    this.nf = extInfo.nodeFactory();
    this.qq = new QQ(extInfo);

    this.delegatingPrincipal = ts.DelegatingPrincipal();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof New) {
      New newCall = (New) n;
      if (newCall.objectType().type().isSubtype(delegatingPrincipal)) {
        // Wrap the constructor call with a call to $addDefaultDelegates.
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

        Cast cast = nf.Cast(pos, newCall.objectType(), call);
        cast = (Cast) cast.type(newCall.objectType().type());
        return cast;
      }
    } else if (old instanceof Call) {
      // Detect calls to Jif initializers. This is an ugly hack.
      Call call = (Call) old;
      Receiver target = call.target();
      if (!(target instanceof New)) return super.leave(old, n, v);

      New newCall = (New) target;
      if (!newCall.objectType().type().isSubtype(delegatingPrincipal))
        return super.leave(old, n, v);

      String initName =
          ClassDeclToFabilExt_c
              .jifConstructorTranslatedName((ClassType) newCall.objectType()
                  .type());
      if (!call.name().equals(initName)) return super.leave(old, n, v);

      // Wrap around the Jif initializer call instead. We do this by mangling
      // the rewritten node to preserve any rewriting that may have occurred in
      // its subtree.
      Call initCall = (Call) n;
      Cast cast = (Cast) initCall.target();
      Call wrapped = (Call) cast.expr();
      List<Expr> wrappedArgs = wrapped.arguments();
      New constructorCall = (New) wrappedArgs.get(0);

      Call newInitCall = initCall.target(constructorCall);
      List<Expr> newArgs = Collections.singletonList((Expr) newInitCall);
      return cast.expr((Expr) wrapped.arguments(newArgs));
    }

    return super.leave(old, n, v);
  }

}
