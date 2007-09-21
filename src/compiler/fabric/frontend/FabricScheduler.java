package fabric.frontend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import polyglot.frontend.JLScheduler;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Barrier;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.Serialized;
import polyglot.frontend.goals.VisitorGoal;
import polyglot.visit.*;
import fabric.ExtensionInfo;
import fabric.visit.*;


public class FabricScheduler extends JLScheduler {
  protected ExtensionInfo       extInfo;

  public FabricScheduler(ExtensionInfo extInfo) {
    super(extInfo);
    this.extInfo = extInfo;
  }

  public Goal LocalClassesRemoved(final Job job) {
    Goal g = internGoal(
        new VisitorGoal(job, new LocalClassRemover(job, extInfo.typeSystem(), extInfo.nodeFactory())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal> ();
            l.add(TypeChecked(job));
            return l;
          }
        });
    return g;
  }
  
  public Goal InnerClassesRewritten(final Job job) {
    Goal g = internGoal(
        new VisitorGoal(job, new InnerClassRewriter(job, extInfo.typeSystem(), extInfo.nodeFactory())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal> ();
            l.add(LocalClassesRemoved(job));
            return l;
          }
        });
    return g;
  }
  
  public Goal InnerClassConstructorsFixed(final Job job) {
    Goal g = internGoal(
        new VisitorGoal(job, new InnerClassConstructorFixer(job, extInfo.typeSystem(), extInfo.nodeFactory())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(InnerClassesRewritten(job));
            return l;
          }
        });
    return g;
  }
  
  public Goal BarrierForInnerClasses() {
    Goal g = internGoal(
        new Barrier(this) {
          @Override
          public Goal goalForJob(Job job) {
            return InnerClassConstructorsFixed(job);
          }
        });
    return g;
  }
  
  public Goal InnerClassesRemoved(final Job job) {
    Goal g = internGoal(
        new VisitorGoal(job, new InnerClassRemover(job, extInfo.typeSystem(), extInfo.nodeFactory())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal> ();
            l.add(BarrierForInnerClasses());
            return l;
          }
        });
    return g;
  }
  
  /**
   * This goal flattens expressions in the AST.
   */
  public Goal ASTFlattened(final Job job) {
    Goal g = internGoal(
        new VisitorGoal(job, new FlattenVisitor(extInfo.typeSystem(), extInfo.nodeFactory())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal> ();
            l.add(InnerClassesRemoved(job));
            return l;
          }
        });
    return g;
  }

  public Goal RewriteProxies(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new ProxyRewriter(extInfo)) {
          @SuppressWarnings("unchecked")
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(ExceptionsChecked(job));
            l.add(ExitPathsChecked(job));
            l.add(InitializationsChecked(job));
            l.add(ConstructorCallsChecked(job));
            l.add(ForwardReferencesChecked(job));
            //l.add(ASTFlattened(job));
            l.add(InnerClassesRemoved(job));
            l.addAll(super.prerequisiteGoals(scheduler));
            return l;
          }
        });

    return g;
  }
  
  public Goal RewriteAtomic(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new AtomicRewriter((ExtensionInfo) job
            .extensionInfo())) {
          @SuppressWarnings("unchecked")
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(RewriteProxies(job));
            return l;
          }
        });

    return g;
  }

  @Override
  public Goal Serialized(Job job) {
    Goal g = internGoal(new Serialized(job) {
      @SuppressWarnings("unchecked")
      @Override
        public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
          List<Goal> l = new ArrayList<Goal>();
          l.addAll(super.prerequisiteGoals(scheduler));
          l.add(RewriteAtomic(job));
          l.add(RewriteProxies(job));
          return l;
        }
      });
    return g;
  }
}
