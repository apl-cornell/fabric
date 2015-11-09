package fabil.frontend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.InitializationsChecked;
import polyglot.types.TypeSystem;

public class FabILInitializationsChecked extends InitializationsChecked {

  public static Goal create(Scheduler scheduler, Job job, TypeSystem ts,
      NodeFactory nf) {
    return scheduler.internGoal(new FabILInitializationsChecked(job, ts, nf));
  }

  protected FabILInitializationsChecked(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }

  @Override
  public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
    List<Goal> l = new ArrayList<>();
    l.add(((FabILScheduler) scheduler).finalToImmutable(job));
    l.addAll(super.prerequisiteGoals(scheduler));
    return l;
  }
}
