package fabil.extension;

import java.util.Arrays;
import java.util.List;

import polyglot.ast.Call;
import polyglot.ast.Local;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Return;
import polyglot.types.MethodInstance;
import polyglot.util.Position;
import fabil.types.FabILTypeSystem;
import fabil.visit.MemoizedMethodRewriter;

public class ReturnExt_c extends FabILExt_c {

  @Override
  public Node rewriteMemoizedMethods(MemoizedMethodRewriter mmr) {
    Return r = node();
    if (mmr.rewriteReturns()) {
      NodeFactory nf = mmr.nodeFactory();
      FabILTypeSystem ts = mmr.typeSystem();
      Position CG = Position.compilerGenerated();
      
      /* These should be in scope for the entire method body after the
       * NodeVisitor leaves the method declaration.
       */
      Local ct = nf.Local(CG, nf.Id(CG, "$memoCallTup"));
      Local mc = nf.Local(CG, nf.Id(CG, "$memoCache"));
      Call updateMemo = nf.Call(CG, mc, nf.Id(CG, "setCall"), ct, r.expr());
      List<? extends MethodInstance> setCallList = ts.MemoCache().methods("setCall", Arrays.asList(ts.CallTuple(), ts.Object()));
      updateMemo = updateMemo.methodInstance(setCallList.get(0));
      return r.expr(nf.Cast(CG, mmr.methodReturnType(), updateMemo));
    }
    return node();
  }

  @Override
  public Return node() {
    return (Return) super.node();
  }
}
