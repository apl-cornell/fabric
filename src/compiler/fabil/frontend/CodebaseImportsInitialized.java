package fabil.frontend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fabil.visit.InitCodebaseImports;

import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.VisitorGoal;
import polyglot.types.TypeSystem;

public class CodebaseImportsInitialized extends VisitorGoal {

  public static Goal create(Scheduler scheduler, Job job, TypeSystem ts, NodeFactory nf) {
    return scheduler.internGoal(new CodebaseImportsInitialized(job, ts, nf));
}

protected CodebaseImportsInitialized(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, new InitCodebaseImports(job, ts, nf));
}

@SuppressWarnings("unchecked")
@Override
public Collection prerequisiteGoals(Scheduler scheduler) {
    List l = new ArrayList();
    l.add(scheduler.TypesInitialized(job));
    l.add(scheduler.TypesInitializedForCommandLine());
    l.addAll(super.prerequisiteGoals(scheduler));
    return l;
}

}
