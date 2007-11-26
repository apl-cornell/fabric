package fabric.frontend;

import java.util.*;

import polyglot.ast.NodeFactory;
import polyglot.frontend.JLScheduler;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Barrier;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.Serialized;
import polyglot.frontend.goals.VisitorGoal;
import polyglot.main.Version;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;
import polyglot.visit.InnerClassConstructorFixer;
import polyglot.visit.InnerClassRemover;
import polyglot.visit.InnerClassRewriter;
import polyglot.visit.LocalClassRemover;
import fabric.ExtensionInfo;
import fabric.visit.*;

public class FabricScheduler extends JLScheduler {
  protected ExtensionInfo extInfo;

  public FabricScheduler(ExtensionInfo extInfo) {
    super(extInfo);
    this.extInfo = extInfo;
  }

  public Goal LocalClassesRemoved(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new LocalClassRemover(job, extInfo
            .typeSystem(), extInfo.nodeFactory())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(TypeChecked(job));
            return l;
          }
        });
    return g;
  }

  public Goal InnerClassesRewritten(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new InnerClassRewriter(job, extInfo
            .typeSystem(), extInfo.nodeFactory())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(LocalClassesRemoved(job));
            return l;
          }
        });
    return g;
  }

  public Goal InnerClassConstructorsFixed(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new InnerClassConstructorFixer(job,
            extInfo.typeSystem(), extInfo.nodeFactory())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(InnerClassesRewritten(job));
            return l;
          }
        });
    return g;
  }

  public Goal InnerClassConstructorsFixedBarrier() {
    Goal g = internGoal(new Barrier(this) {
      @Override
      public Goal goalForJob(Job job) {
        return InnerClassConstructorsFixed(job);
      }
    });
    return g;
  }

  public Goal InnerClassesRemoved(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new InnerClassRemover(job, extInfo
            .typeSystem(), extInfo.nodeFactory())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(InnerClassConstructorsFixedBarrier());
            return l;
          }
        });
    return g;
  }

  public Goal AssignmentsNormalized(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new AssignNormalizer(extInfo
            .nodeFactory())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
            List<Goal> l = new ArrayList<Goal>();
            return l;
          }
        });
    return g;
  }

  public Goal FixArrayInitializerTypes(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new ArrayInitializerTypeFixer(job,
            extInfo.typeSystem(), extInfo.nodeFactory())) {
          /*
           * (non-Javadoc)
           * 
           * @see polyglot.frontend.goals.AbstractGoal#prerequisiteGoals(polyglot.frontend.Scheduler)
           */
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(TypeChecked(job));
            return l;
          }
        });
    return g;
  }

  public Goal WrapInlineables(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new InlineableWrapper(job, extInfo
            .typeSystem(), extInfo.nodeFactory())) {
          @SuppressWarnings("unchecked")
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(ExceptionsChecked(job));
            l.add(ExitPathsChecked(job));
            l.add(InitializationsChecked(job));
            l.add(ConstructorCallsChecked(job));
            l.add(ForwardReferencesChecked(job));
            l.add(InnerClassesRemoved(job));
            l.add(AssignmentsNormalized(job));
            l.add(FixArrayInitializerTypes(job));
            l.addAll(super.prerequisiteGoals(scheduler));
            return l;
          }
        });
    return g;
  }

  public Goal RewriteProxies(final Job job) {
    Goal g = internGoal(new VisitorGoal(job, new ProxyRewriter(extInfo)) {
      @SuppressWarnings("unchecked")
      @Override
      public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
        List<Goal> l = new ArrayList<Goal>();
        l.add(WrapInlineables(job));
        l.add(FixArrayInitializerTypes(job));
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
        if (!extInfo.getOptions().signatureMode) {
          l.add(RewriteAtomic(job));
          l.add(RewriteProxies(job));
        }
        return l;
      }

      @Override
      protected polyglot.visit.ClassSerializer createSerializer(TypeSystem ts,
          NodeFactory nf, Date lastModified, ErrorQueue eq, Version version) {
        if (extInfo.getOptions().signatureMode)
          return super.createSerializer(ts, nf, lastModified, eq, version);

        return new fabric.visit.ClassSerializer(ts, nf, lastModified, eq,
            version);
      }
    });
    return g;
  }
}
