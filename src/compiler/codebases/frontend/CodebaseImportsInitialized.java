package codebases.frontend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.VisitorGoal;
import polyglot.types.TypeSystem;
import codebases.types.CodebaseTypeSystem;
import codebases.visit.InitCodebaseImports;

public class CodebaseImportsInitialized extends VisitorGoal {

  public static Goal create(Scheduler scheduler, Job job, TypeSystem ts,
      NodeFactory nf) {
    return scheduler.internGoal(new CodebaseImportsInitialized(job, ts, nf));
  }

  protected CodebaseImportsInitialized(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, new InitCodebaseImports(job, (CodebaseTypeSystem) ts, nf));
  }

  @Override
  public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
    List<Goal> l = new ArrayList<>();
    l.add(scheduler.TypesInitialized(job));
    l.add(scheduler.TypesInitializedForCommandLine());
    l.addAll(super.prerequisiteGoals(scheduler));
    return l;
  }

}
