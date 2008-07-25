package fabil.extension;

import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Stmt;
import polyglot.util.Position;
import fabil.ast.Atomic;
import fabil.visit.AtomicRewriter;

public class AtomicExt_c extends FabricExt_c {
  @SuppressWarnings("unchecked")
  @Override
  public Node rewriteAtomic(AtomicRewriter ar) {    
    Atomic atomic  = (Atomic) node();
    NodeFactory nf = ar.nodeFactory();
    
    // Note: this needs to be built by the NF rather than the QQ because
    // otherwise it will be ambiguous and will fail further translation
    Position CG = Position.compilerGenerated();
    Stmt begin  = nf.Eval(CG, nf.Call(CG, ar.transactionManager(),
                                          nf.Id(CG, "startTransaction")));
    Stmt commit = nf.Eval(CG, nf.Call(CG, ar.transactionManager(),
                                          nf.Id(CG, "commitTransaction")));
    Stmt abort  = nf.Eval(CG, nf.Call(CG, ar.transactionManager(),
                                          nf.Id(CG, "abortTransaction")));
   
    Id flag = nf.Id(CG, "$commit" + (freshTid++));
    
    String block = "{ boolean "+flag+" = true;\n" +
                   "  %S\n" +
                   "  try { %LS }\n" +
                   "  catch (final Throwable $_) { "+flag+" = false; throw new fabric.client.AbortException($_); }\n" +
                   "  finally { if ("+flag+") {%S} else {%S} } }\n";
    return ar.qq().parseStmt(block, begin, atomic.statements(), commit, abort);
  }

  private static int freshTid = 0;
}
