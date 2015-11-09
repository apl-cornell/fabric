package fabil.frontend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.TypeChecked;
import polyglot.types.TypeSystem;

public class FabILTypeChecked extends TypeChecked {

  public static Goal create(FabILScheduler scheduler, Job job, TypeSystem ts, NodeFactory nf) {
      return scheduler.internGoal(new FabILTypeChecked(job, ts, nf));
  }

  protected FabILTypeChecked(Job job, TypeSystem ts, NodeFactory nf) {
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
