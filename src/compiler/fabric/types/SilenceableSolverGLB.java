package fabric.types;

import java.util.Iterator;

import polyglot.types.SemanticException;
import polyglot.util.ErrorInfo;
import jif.types.Constraint;
import jif.types.JifTypeSystem;
import jif.types.SolverGLB;
import jif.types.VarMap;

/**
 * <code>SilenceableSolverGLB</code> can be muted, that is, not reporting any error.
 * 
 * @author qixin
 */
public class SilenceableSolverGLB extends SolverGLB {
  private static boolean muted;
  
  public SilenceableSolverGLB(JifTypeSystem ts, polyglot.frontend.Compiler compiler, String solverName) {
      super(ts, compiler, solverName);
  }

  protected SilenceableSolverGLB(SolverGLB js) {
      super(js);
  }

  public static boolean muted() {
    return muted;
  }
  
  public static void mute(boolean b) {
    muted = b;
  }
  
  public boolean isSolved() {
    return status == STATUS_SOLVED;
  }
  
  @Override
  public VarMap solve() throws SemanticException {
    if (muted()) {
      // check for static failures.
      if (staticFailedConstraints != null && !staticFailedConstraints.isEmpty()) {
        if (shouldReport(1)) {
            report(1, "   " + staticFailedConstraints.size() + " statically failed constraint");
        }
        setStatus(STATUS_NO_SOLUTION);
      }
      
      for (Iterator iter = staticFailedConstraints.iterator(); iter.hasNext();) {
        Constraint cons = (Constraint)iter.next();
        System.err.println("Runtime check does not type-check, due to\n" + cons.technicalMsg() + 
                           "\nin the constraint\n" + cons + "\nat " + cons.position());
      }
    }

    return super.solve();
  }
}
