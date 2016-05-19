package fabric.visit;

import java.util.List;

import fabric.extension.FabricCallHelper;
import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricTypeSystem;
import fabric.types.SilenceableSolverGLB;

import jif.ast.JifExt_c;
import jif.ast.JifMethodDecl;
import jif.ast.JifUtil;
import jif.extension.CallHelper;
import jif.types.JifProcedureInstance;
import jif.types.label.Label;
import jif.visit.JifLabelSubst;
import jif.visit.LabelChecker;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Receiver;
import polyglot.ast.Stmt;
import polyglot.frontend.Job;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.Position;

public class FabricLabelChecker extends LabelChecker {
  public FabricLabelChecker(Job job, TypeSystem ts, NodeFactory nf,
      boolean warningsEnabled, boolean solvePerClassBody,
      boolean solvePerMethod, boolean doLabelSubst) {
    super(job, ts, nf, warningsEnabled, solvePerClassBody, solvePerMethod,
        doLabelSubst);
  }

  @Override
  public JifMethodDecl leavingMethod(JifMethodDecl n) {
    if (solvePerMethod) {
      // solving by method. We need to solve the constraints
      JifMethodDecl result = (JifMethodDecl) solveConstraints(n);
      if (!((SilenceableSolverGLB) solver()).isSolved()) {
        return null;
      }
      return result;
    }
    return n;
  }

  //XXX: CallHelper has some really bad design patterns.
  //     Lots of static calls make it very difficult to extend.
  @Override
  public CallHelper createCallHelper(Label receiverLabel, Receiver receiver,
      ReferenceType calleeContainer, JifProcedureInstance pi,
      List<Expr> actualArgs, Position position) {
    return new FabricCallHelper(receiverLabel, receiver, calleeContainer, pi,
        actualArgs, position);
  }

  @Override
  public CallHelper createCallHelper(Label receiverLabel,
      ReferenceType calleeContainer, JifProcedureInstance pi,
      List<Expr> actualArgs, Position position) {
    return createCallHelper(receiverLabel, null, calleeContainer, pi,
        actualArgs, position);
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    // TODO Auto-generated method stub
    return super.clone();
  }

  @Override
  public JifLabelSubst labelSubst() {
    return new FabricLabelSubst(job, ts, nf, solver);
  }

  @Override
  public Node labelCheck(Node n) throws SemanticException {
    n = super.labelCheck(n);
    // If this node didn't make any accesses, update the CL in the pathmap to be
    // the conflict PC by default so that branching statement PC resets continue
    // to work.
    // XXX Sometimes label checking a node returns null?
    if (n != null && JifUtil.jifExt(n) != null) {
      FabricTypeSystem fts = (FabricTypeSystem) ts;
      FabricPathMap X = (FabricPathMap) JifExt_c.getPathMap(n);
      if (X == null) {
        // If there was no pathmap, make one.
        X = (FabricPathMap) fts.pathMap();
      }

      FabricContext A = (FabricContext) context();
      if (X.CL().equals(fts.noAccesses())) {
        n = JifExt_c.updatePathMap(n, X.CL(A.conflictLabel()));
      } else if (n instanceof Stmt) {
        // Make the CL after the statement the new pc.
        A.setConflictLabel(X.CL());
      }
    }
    return n;
  }
}
