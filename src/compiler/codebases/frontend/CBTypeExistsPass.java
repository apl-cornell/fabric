package codebases.frontend;

import polyglot.frontend.Pass;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.TypeExists;
import polyglot.frontend.passes.TypeExistsPass;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorInfo;
import polyglot.util.ErrorQueue;
import codebases.types.CodebaseTypeSystem;
import fabric.common.FabricLocation;

public class CBTypeExistsPass extends TypeExistsPass implements Pass {

  private final CodebaseTypeSystem ts;

  public CBTypeExistsPass(Scheduler scheduler, TypeSystem ts, TypeExists goal) {
    super(scheduler, ts, goal);
    this.ts = (CodebaseTypeSystem) ts;
  }

  @Override
  public boolean run() {
    FabricLocation ns = ((CBTypeExists) goal).namespace();
    String name = goal.typeName();
    try {
      // Try to resolve the type; this may throw a
      // MissingDependencyException on the job to load the file
      // containing the type.
      Named n = ts.namespaceResolver(ns).find(name);
      if (n instanceof Type) {
        return true;
      }
    } catch (SemanticException e) {
      ErrorQueue eq = ts.extensionInfo().compiler().errorQueue();
      eq.enqueue(ErrorInfo.SEMANTIC_ERROR, e.getMessage(), e.position());
    }
    return false;
  }

}
