package fabric.frontend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
import polyglot.visit.ExpressionFlattener;
import polyglot.visit.InnerClassConstructorFixer;
import polyglot.visit.InnerClassRemover;
import polyglot.visit.InnerClassRewriter;
import polyglot.visit.LocalClassRemover;
import polyglot.visit.LoopNormalizer;
import fabric.ExtensionInfo;
import fabric.Options;
import fabric.visit.ArrayInitializerTypeFixer;
import fabric.visit.AtomicMethodRewriter;
import fabric.visit.AtomicRewriter;
import fabric.visit.InlineableWrapper;
import fabric.visit.ProxyRewriter;
import fabric.visit.ReadWriteChecker;

public class FabricScheduler extends JLScheduler {
  protected ExtensionInfo extInfo;

  public FabricScheduler(ExtensionInfo extInfo) {
    super(extInfo);
    this.extInfo = extInfo;
  }

  public Goal LoopsNormalized(final Job job) {
    Goal g = internGoal(new VisitorGoal(job, new LoopNormalizer(job, 
            job.extensionInfo().typeSystem(), 
            job.extensionInfo().nodeFactory())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(TypeChecked(job));
            return l;
          }
        });

    return g;
  }

  public Goal ExpressionsFlattened(final Job job) {
    Goal g = internGoal(new VisitorGoal(job, new ExpressionFlattener(job, 
            job.extensionInfo().typeSystem(), 
            job.extensionInfo().nodeFactory())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(LoopsNormalized(job));
            return l;
          }
        });

    return g;
  }

  public Goal TypeCheckedAfterFlatten(final Job job) {
    TypeSystem ts = job.extensionInfo().typeSystem();
    NodeFactory nf = job.extensionInfo().nodeFactory();

    Goal g = internGoal(new polyglot.frontend.goals.TypeChecked(job, ts, nf) {
      @SuppressWarnings("unchecked")
      @Override
      public Collection prerequisiteGoals(Scheduler scheduler) {
        return Collections.singletonList(ExpressionsFlattened(job));
      }
    });

    return g;
  }

  public Goal RewriteAtomicMethods(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new AtomicMethodRewriter(
            (ExtensionInfo) job.extensionInfo())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(TypeChecked(job));
            
            if (Options.global().optimize) {
              l.add(TypeCheckedAfterFlatten(job));
            }
            
            return l;
          }
        });

    return g;
  }

  public Goal LocalClassesRemoved(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new LocalClassRemover(job, extInfo
            .typeSystem(), extInfo.nodeFactory())) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(RewriteAtomicMethods(job));
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
            l.add(InnerClassesRemoved(job));
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
            l.add(FixArrayInitializerTypes(job));
            l.addAll(super.prerequisiteGoals(scheduler));
            return l;
          }
        });
    return g;
  }

  public Goal ReadWriteChecked(final Job job) {
    Goal g = internGoal(new VisitorGoal(job, new ReadWriteChecker(job,
        extInfo.typeSystem(), extInfo.nodeFactory())) {
      @SuppressWarnings("unchecked")
      @Override
      public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
        List<Goal> l = new ArrayList<Goal>();
        l.add(WrapInlineables(job));
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
        
        if (Options.global().optimize) {
          l.add(ReadWriteChecked(job));
        }
        
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
