package fabric.types;

import polyglot.types.SemanticException;
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
    }

    return super.solve();
  }
}
