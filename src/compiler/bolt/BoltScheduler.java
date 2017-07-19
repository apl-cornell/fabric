package bolt;

import bolt.visit.BoltObjectInitializationChecker;
import polyglot.ext.jl7.JL7Scheduler;
import polyglot.frontend.CyclicDependencyException;
import polyglot.frontend.JLExtensionInfo;
import polyglot.frontend.Job;
import polyglot.frontend.goals.EmptyGoal;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.VisitorGoal;
import polyglot.util.InternalCompilerError;

public class BoltScheduler extends JL7Scheduler {

  protected ExtensionInfo extInfo;

  public BoltScheduler(JLExtensionInfo extInfo) {
    super(extInfo);
    this.extInfo = (ExtensionInfo) extInfo;
  }

  @Override
  public Goal ForwardReferencesChecked(Job job) {
    // Bolt doesn't require forward-reference checking, so disable it.
    return internGoal(new EmptyGoal(job, "ForwardReferencesChecked"));
  }

  @Override
  public Goal InitializationsChecked(Job job) {
    Goal g = new VisitorGoal(job, new BoltObjectInitializationChecker(job,
        extInfo.typeSystem(), extInfo.nodeFactory()));

    try {
      g.addPrerequisiteGoal(ReachabilityChecked(job), this);
    } catch (CyclicDependencyException e) {
      throw new InternalCompilerError(e);
    }

    return internGoal(g);
  }

}
