package fabric.extension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jif.extension.JifCallExt;
import jif.translate.ToJavaExt;
import jif.types.Assertion;
import jif.types.CallerConstraint;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.JifMethodInstance;
import jif.types.JifTypeSystem;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.PathMap;
import jif.types.PrincipalConstraint;
import jif.types.label.AccessPath;
import jif.types.label.ArgLabel;
import jif.types.label.ConfPolicy;
import jif.types.label.IntegPolicy;
import jif.types.label.Label;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.Special;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import fabric.ast.FabricCall;
import fabric.types.FabricClassType;
import fabric.types.FabricTypeSystem;

public class CallJifExt_c extends JifCallExt {
  public CallJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }

  protected void labelCheckArgs(LabelChecker lc, Call c,
      List<Label> actualLabels) throws SemanticException {
    JifContext A = lc.context();
    JifTypeSystem ts = lc.typeSystem();

    // X_0 = X_null[n := A[pc]]
    PathMap Xj = ts.pathMap();
    Xj = Xj.N(A.pc());

    for (Expr arg : c.arguments()) {
      // A[pc := X_{j-1}[N]] |- Ej : Xj
      A = (JifContext) A.pushBlock();
      A.setPc(Xj.N(), lc);
      arg = (Expr) lc.context(A).labelCheck(arg);
      A = (JifContext) A.pop();

      Xj = getPathMap(arg);
      actualLabels.add(Xj.NV());
    }

    // // now that the actualArgs list is correct, we can constrain the args
    // for (int i = 0; i < actualArgs.size(); i++) {
    // Expr Ej = (Expr)actualArgs.get(i);
    // constrainArg(lc, i, Ej, (Type)pi.formalTypes().get(i));
    // }
    //
    // return Xjoin;
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    Node n = super.labelCheck(lc);
    FabricCall c = (FabricCall) n;

    DereferenceHelper.checkDereference(c.target(), lc, c.position());

    if (c.remoteWorker() != null) {
      FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
      JifContext A = lc.jifContext();
      A = (JifContext) c.del().enterScope(A);

      JifMethodInstance mi = (JifMethodInstance) c.methodInstance();

      Expr target = (Expr) c.target();
      Label targetLabel = null;

      PathMap Xs = getPathMap(target);
      if (target instanceof Special) {
        targetLabel =
            ((FabricClassType) lc.context().currentClass()).thisLabel();
      } else {
        targetLabel = Xs.NV();
      }

      List<? extends Type> formalTypes = mi.formalTypes();
      List<ArgLabel> formalLabels = new ArrayList<>(formalTypes.size());
      for (Type ft : formalTypes) {
        ArgLabel fl = (ArgLabel) ts.labelOfType(ft);
        formalLabels.add(fl);
      }

      List<Label> actualLabels = new ArrayList<>(c.arguments().size());
      labelCheckArgs(lc, c, actualLabels);

      Label entryLabel = mi.pcBound();
      Label returnLabel = ts.join(mi.returnValueLabel(), mi.returnLabel());
      AccessPath storeap = ts.storeAccessPathFor(target, A);
      entryLabel =
          StoreInstantiator.instantiate(entryLabel, A, target, target.type()
              .toReference(), targetLabel, formalLabels, formalTypes,
              actualLabels, c.arguments(), Collections.<Label> emptyList(),
              storeap);
      returnLabel =
          StoreInstantiator.instantiate(returnLabel, A, target, target.type()
              .toReference(), targetLabel, formalLabels, formalTypes,
              actualLabels, c.arguments(), Collections.<Label> emptyList(),
              storeap);

      Principal remotePrincipal = c.remoteWorkerPrincipal();

      // Fold in the policies of all the parameters.
      // Note that we are calling the original method, rather than the _remote
      // version
      ConfPolicy entryConfPolicy = ts.confProjection(entryLabel);
      IntegPolicy entryIntegPolicy = ts.integProjection(entryLabel);

      for (Type ft : (List<Type>) mi.formalTypes()) {
        ArgLabel fl_ = (ArgLabel) ts.labelOfType(ft);
        Label fl = fl_.upperBound();
        fl =
            StoreInstantiator.instantiate(fl, A, target, target.type()
                .toReference(), targetLabel, formalLabels, formalTypes,
                actualLabels, c.arguments(), Collections.EMPTY_LIST, storeap);

        ConfPolicy cp = ts.confProjection(fl);
        IntegPolicy ip = ts.integProjection(fl);
        entryConfPolicy = ts.join(entryConfPolicy, cp);
        entryIntegPolicy = ts.meet(entryIntegPolicy, ip);
      }

      // These checks happen at runtime
      // lc.constrain(new NamedLabel("{C(rv), *<-worker$}",
      // ts.pairLabel(Position.compilerGenerated(),
      // ts.confProjection(returnLabel),
      // ts.writerPolicy(Position.compilerGenerated(),
      // ts.topPrincipal(Position.compilerGenerated()),
      // localPrincipal))),
      // LabelConstraint.LEQ,
      // new NamedLabel("{*->worker$, I(m)}",
      // ts.pairLabel(Position.compilerGenerated(),
      // ts.readerPolicy(Position.compilerGenerated(),
      // ts.topPrincipal(Position.compilerGenerated()),
      // localPrincipal),
      // entryIntegPolicy)),
      // A.labelEnv(),
      // c.position(),
      // new ConstraintMessage() {
      // @Override
      // public String msg() {
      // return
      // "Insecure remote method call: Either the return value is not readable"
      // +
      // " by the calling worker or the calling worker does not have" +
      // " enough integrity to host the method arguments and make the call.";
      // }
      //
      // @Override
      // public String detailMsg() {
      // return
      // "Insecure remote method call: Either the return value is not readable"
      // +
      // " by the calling worker or the calling worker does not have" +
      // " enough integrity to host the method arguments and make the call.";
      // }
      //
      // @Override
      // public String technicalMsg() {
      // return
      // "C(rv) <= {*->worker$} and {*<-worker$} <= I(m) for obj.m@c(...)";
      // }
      // });

      lc.constrain(
          new NamedLabel("{C(m), *<-c}", ts.pairLabel(Position
              .compilerGenerated(), entryConfPolicy, ts.writerPolicy(
              Position.compilerGenerated(),
              ts.topPrincipal(Position.compilerGenerated()), remotePrincipal))),
          LabelConstraint.LEQ,
          new NamedLabel("[*->c, I(rv)]", ts.pairLabel(Position
              .compilerGenerated(), ts.readerPolicy(
              Position.compilerGenerated(),
              ts.topPrincipal(Position.compilerGenerated()), remotePrincipal),
              ts.integProjection(returnLabel))), A.labelEnv(), c.position(),
          new ConstraintMessage() {
            @Override
            public String msg() {
              return "Insecure remote method call: Either the callee worker is not allowed to read"
                  + " the method arguments or it does not have enough integrity to "
                  + " return correctly.";
            }

            @Override
            public String detailMsg() {
              return "Insecure remote method call: Either the callee worker is not allowed to read"
                  + " the method arguments or it does not have enough integrity to "
                  + " return correctly.";
            }

            @Override
            public String technicalMsg() {
              return "C(m) <= {*->c} and {*<-c} <= I(rv) for obj.m@c(...)";
            }
          });

      for (Assertion as : mi.constraints()) {
        if (as instanceof CallerConstraint) {
          CallerConstraint cc = (CallerConstraint) as;
          for (Principal p : cc.principals()) {
            lc.constrain(remotePrincipal, PrincipalConstraint.ACTSFOR, p,
                A.labelEnv(), c.position(), new ConstraintMessage() {
                  @Override
                  public String msg() {
                    return "The principal of the remote worker must act for every principal in the caller constraint.";
                  }

                  @Override
                  public String detailMsg() {
                    return "The principal of the remote worker must act for every principal in the caller constraint.";
                  }

                  @Override
                  public String technicalMsg() {
                    return "c acts for p in obj.m@c(...) with the constraint caller(p)";
                  }
                });
          }
        }
      }
    }

    return c;
  }
}
