package bolt;

import polyglot.ext.jl7.JL7Scheduler;
import polyglot.frontend.JLExtensionInfo;
import polyglot.frontend.Job;
import polyglot.frontend.goals.EmptyGoal;
import polyglot.frontend.goals.Goal;

public class BoltScheduler extends JL7Scheduler {

  public BoltScheduler(JLExtensionInfo extInfo) {
    super(extInfo);
  }

  @Override
  public Goal ForwardReferencesChecked(Job job) {
    // Bolt doesn't require forward-reference checking, so disable it.
    return internGoal(new EmptyGoal(job, "ForwardReferencesChecked"));
  }

}
