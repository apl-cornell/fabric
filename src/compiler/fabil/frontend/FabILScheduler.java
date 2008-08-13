package fabil.frontend;

import java.util.*;

import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.frontend.JLScheduler;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.Serialized;
import polyglot.frontend.goals.VisitorGoal;
import polyglot.main.Version;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;
import polyglot.util.Position;
import polyglot.visit.*;
import fabil.ExtensionInfo;
import fabil.Options;
import fabil.visit.*;

public class FabILScheduler extends JLScheduler {
  protected ExtensionInfo extInfo;

  public FabILScheduler(ExtensionInfo extInfo) {
    super(extInfo);
    this.extInfo = extInfo;
  }

  public Goal LoopsNormalized(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new LoopNormalizer(job, job
            .extensionInfo().typeSystem(), job.extensionInfo().nodeFactory())) {
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
    Goal g =
        internGoal(new VisitorGoal(job, new ExpressionFlattener(job, job
            .extensionInfo().typeSystem(), job.extensionInfo().nodeFactory())) {
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

            if (Options.global().optLevel > 0) {
              l.add(TypeCheckedAfterFlatten(job));
            }

            return l;
          }
        });

    return g;
  }

  public Goal InnerClassesRemoved(final Job job) {
    InnerClassRemover icr =
      new InnerClassRemover(job, extInfo.typeSystem(), extInfo.nodeFactory()) {
      @Override
      protected ContextVisitor localClassRemover() {
        return new LocalClassRemover(job, ts, nf) {
          @Override
          protected TypeNode defaultSuperType(Position pos) {
            return null;
          }
        };
      }
    };
    Goal g =
        internGoal(new VisitorGoal(job, icr) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<Goal>();
            l.add(RewriteAtomicMethods(job));
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
    Goal g =
        internGoal(new VisitorGoal(job, new ReadWriteChecker(job, extInfo
            .typeSystem(), extInfo.nodeFactory())) {
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
  
  /**
   * Ensures all objects have their locations assigned.
   */
  public Goal LocationsAssigned(final Job job) {
    Goal g =
      internGoal(new VisitorGoal(job, new LocationAssigner(job, extInfo)) {
        @Override
        @SuppressWarnings("unchecked")
        public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
          List<Goal> l = new ArrayList<Goal>();
          l.add(InnerClassesRemoved(job));
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
        l.add(LocationsAssigned(job));

        if (Options.global().optLevel > 0) {
          l.add(ReadWriteChecked(job));
        }

        l.addAll(super.prerequisiteGoals(scheduler));
        return l;
      }
    });

    return g;
  }

  public Goal InstrumentThreads(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new ThreadRewriter((ExtensionInfo) job
            .extensionInfo())) {
              @Override
              public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
                List<Goal> l = new ArrayList<Goal>();
                l.add(RewriteProxies(job));
                return l;
              }
          
        });

    return g;
  }

  public Goal RewriteAtomic(final Job job) {
    Goal g =
        internGoal(new VisitorGoal(job, new AtomicRewriter((ExtensionInfo) job
            .extensionInfo())) {
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
          l.add(RewriteProxies(job));
          l.add(RewriteAtomic(job));
          l.add(InstrumentThreads(job));
        }
        return l;
      }

      @Override
      protected polyglot.visit.ClassSerializer createSerializer(TypeSystem ts,
          NodeFactory nf, Date lastModified, ErrorQueue eq, Version version) {
        if (extInfo.getOptions().signatureMode)
          return super.createSerializer(ts, nf, lastModified, eq, version);

        return new fabil.visit.ClassSerializer(ts, nf, lastModified, eq,
            version);
      }
    });
    return g;
  }
}
