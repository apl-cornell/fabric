package fabric;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import codebases.frontend.CBScheduler;
import codebases.frontend.CBTypeExists;
import codebases.frontend.CodebaseImportsInitialized;
import codebases.frontend.CodebaseSource;
import codebases.visit.CBTypeBuilder;

import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;
import fabric.visit.CoercePrincipals;
import fabric.visit.ExplicitSuperclassAdder;
import fabric.visit.FClassGenerator;
import fabric.visit.FabILSkeletonCreator;
import fabric.visit.FabricExceptionChecker;
import fabric.visit.FabricToFabilRewriter;
import fabric.visit.NamespaceChecker;
import fabric.visit.RemoteCallWrapperAdder;
import fabric.visit.RemoteCallWrapperUpdater;
import fabric.visit.StageTxnMethodAdder;
import fabric.worker.Worker;

import jif.JifScheduler;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.CyclicDependencyException;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Job;
import polyglot.frontend.OutputPass;
import polyglot.frontend.Pass;
import polyglot.frontend.Scheduler;
import polyglot.frontend.Source;
import polyglot.frontend.TargetFactory;
import polyglot.frontend.goals.AbstractGoal;
import polyglot.frontend.goals.Barrier;
import polyglot.frontend.goals.EmptyGoal;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.Serialized;
import polyglot.frontend.goals.VisitorGoal;
import polyglot.main.Options;
import polyglot.main.Report;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.visit.Translator;

public class FabricScheduler extends JifScheduler implements CBScheduler {
  protected fabil.ExtensionInfo filext;
  protected fabric.ExtensionInfo fabext;

  protected TargetFactory fabil_target;

  public FabricScheduler(fabric.ExtensionInfo fabext, fabil.ExtensionInfo filext) {
    super(fabext, filext);
    this.fabext = fabext;
    this.filext = filext;
    this.fabil_target =
        new TargetFactory(filext.extFileManager(), filext.getOptions()
            .outputLocation(), "fil", Options.global.output_stdout);

  }

  @Override
  public Job addJob(Source source, Node ast) {
    // We use the same hack here as in JifScheduler, but we need to override to
    // compare with Object.fab instead of Object.jif.
    Job j = super.addJob(source, ast);
    if ("Object.fab".equals(source.name())) {
      this.objectJob = j;
    }
    return j;
  }

  @Override
  public Job addJob(Source source) {
    // We use the same hack here as in JifScheduler, but we need to override to
    // compare with Object.fab instead of Object.jif.
    Job j = super.addJob(source);
    if ("Object.fab".equals(source.name())) {
      this.objectJob = j;
    }
    return j;
  }

  @Override
  public Goal ImportTableInitialized(Job job) {
    TypeSystem ts = extInfo.typeSystem();
    NodeFactory nf = extInfo.nodeFactory();
    Goal g = CodebaseImportsInitialized.create(this, job, ts, nf);
    return g;
  }

  public Goal ExplicitSuperclassesAdded(Job job) {
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g =
        internGoal(new VisitorGoal(job, new ExplicitSuperclassAdder(ts, nf)));
    try {
      addPrerequisiteDependency(g, Parsed(job));
    } catch (CyclicDependencyException e) {
      throw new InternalCompilerError(e);
    }
    return g;
  }

  public Goal StageTxnMethodsAdded(final Job job) {
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g =
        internGoal(new VisitorGoal(job, new StageTxnMethodAdder(job, ts, nf)) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<>();
            l.add(ExplicitSuperclassesAdded(job));
            return l;
          }
        });
    return g;
  }

  public Goal RemoteCallWrappersAdded(final Job job) {
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g =
        internGoal(new VisitorGoal(job, new RemoteCallWrapperAdder(job, ts, nf)) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<>();
            l.add(ExplicitSuperclassesAdded(job));
            return l;
          }
        });
    return g;
  }

  @Override
  public Goal TypesInitialized(Job job) {
    FabricOptions opts = (FabricOptions) job.extensionInfo().getOptions();
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g = internGoal(new VisitorGoal(job, new CBTypeBuilder(job, ts, nf)));
    try {
      addPrerequisiteDependency(g, Parsed(job));
      addPrerequisiteDependency(g, ExplicitSuperclassesAdded(job));
      if (!opts.signatureMode()) {
        addPrerequisiteDependency(g, RemoteCallWrappersAdded(job));
        addPrerequisiteDependency(g, StageTxnMethodsAdded(job));
      } else {
        addPrerequisiteDependency(g, Parsed(job));
      }
    } catch (CyclicDependencyException e) {
      throw new InternalCompilerError(e);
    }
    return g;
  }

  public Goal RemoteCallWrappersUpdated(final Job job) {
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g =
        internGoal(new VisitorGoal(job, new RemoteCallWrapperUpdater(job, ts,
            nf)) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler s) {
            List<Goal> l = new ArrayList<>();
            l.add(Disambiguated(job));
            return l;
          }
        });
    return g;
  }

  @Override
  public Goal TypeChecked(Job job) {
    FabricOptions opts = (FabricOptions) job.extensionInfo().getOptions();
    Goal g = super.TypeChecked(job);
    try {
      if (!opts.signatureMode()) {
        addPrerequisiteDependency(g, RemoteCallWrappersUpdated(job));
      } else {
        addPrerequisiteDependency(g, Disambiguated(job));
      }
    } catch (CyclicDependencyException e) {
      throw new InternalCompilerError(e);
    }
    return g;
  }

  @Override
  public Goal Serialized(Job job) {
    FabricOptions opts = (FabricOptions) job.extensionInfo().getOptions();
    Goal g = null;
    if (!opts.signatureMode()) {
      g = super.Serialized(job);
      try {
        // only run if we are publishing classes or
        // compiling downloaded classes
        if (Worker.isInitialized())
          addPrerequisiteDependency(g, this.ConsistentNamespace());

        g.addPrerequisiteGoal(TypeChecked(job), this);
        g.addPrerequisiteGoal(ExceptionsChecked(job), this);
      } catch (CyclicDependencyException e) {
        throw new InternalCompilerError(e);
      }
    } else {
      // Signature mode. Don't run some passes.
      g = internGoal(new Serialized(job) {
        @Override
        public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
          List<Goal> l = new ArrayList<>();
          l.add(TypeChecked(job));
          l.add(ExceptionsChecked(job));
          l.add(NativeConstructorsAdded(job));
          return l;
        }
      });
    }
    return g;
  }

  public Goal PrincipalsCoerced(Job job) {
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();

    Goal g =
        internGoal(new VisitorGoal(job, new CoercePrincipals(job, ts, nf)) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
            List<Goal> l = new ArrayList<>();
            l.add(Serialized(job));
            return l;
          }
        });

    return g;
  }

  private Goal preFClassGeneration = new Barrier("preFClassGeneration", this) {
    @Override
    public Goal goalForJob(Job job) {
      Goal g = internGoal(new EmptyGoal(job, "preFClassGeneration"));

      try {
        addPrerequisiteDependency(g, TypeChecked(job));
        addPrerequisiteDependency(g, ExceptionsChecked(job));
      } catch (CyclicDependencyException e) {
        // Cannot happen.
        throw new InternalCompilerError(e);
      }

      return g;
    }
  };

  public Goal PreFClassGenerationBarrier() {
    return preFClassGeneration;
  }

  public Goal FClassGenerated(Job job) {
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g;
    if (((CodebaseSource) job.source()).shouldPublish()) {
      g = internGoal(new VisitorGoal(job, new FClassGenerator(job, ts, nf)));
      try {
        addPrerequisiteDependency(g, this.PreFClassGenerationBarrier());
      } catch (CyclicDependencyException e) {
        // Cannot happen
        throw new InternalCompilerError(e);
      }
    } else g = internGoal(new EmptyGoal(job, "FClassGenerated"));

    return g;
  }

  private Goal allFClassesGenerated =
      new Barrier("allFClassesGenerated", this) {
        @Override
        public Goal goalForJob(Job j) {
          return FClassGenerated(j);
        }
      };

  public Goal AllFClassesGenerated() {
    return allFClassesGenerated;
  }

  public Goal NamespaceChecked(Job job) {
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g =
        internGoal(new VisitorGoal(job, new NamespaceChecker(job, ts, nf)));
    try {
      addPrerequisiteDependency(g, this.AllFClassesGenerated());
    } catch (CyclicDependencyException e) {
      // Cannot happen
      throw new InternalCompilerError(e);
    }
    return g;
  }

  private Goal consistentNamespace = new Barrier("consistentNamespace", this) {
    @Override
    public Goal goalForJob(Job j) {
      return NamespaceChecked(j);
    }
  };

  public Goal ConsistentNamespace() {
    return consistentNamespace;
  }

  public Goal FabricToFabilRewritten(Job job) {
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g =
        internGoal(new VisitorGoal(job, new FabricToFabilRewriter(job, ts, nf,
            filext)));
    try {
      addPrerequisiteDependency(g, this.Serialized(job));
      addPrerequisiteDependency(g, this.PrincipalsCoerced(job));

      // make sure that if Object.fab is being compiled, it is always
      // written to FabIL before any other job.
      if (objectJob != null && job != objectJob)
        addPrerequisiteDependency(g, FabricToFabilRewritten(objectJob));
    } catch (CyclicDependencyException e) {
      // Cannot happen
      throw new InternalCompilerError(e);
    }

    return g;
  }

  @Override
  public Goal ExceptionsChecked(Job job) {
    TypeSystem ts = extInfo.typeSystem();
    NodeFactory nf = extInfo.nodeFactory();
    Goal g =
        internGoal(new VisitorGoal(job, new FabricExceptionChecker(job, ts, nf)) {
          @Override
          public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
            List<Goal> l = new ArrayList<>();
            l.add(scheduler.TypeChecked(job));
            l.add(scheduler.ReachabilityChecked(job));
            FabricScheduler s = (FabricScheduler) scheduler;
            l.add(s.NotNullChecker(job));
            l.add(s.PreciseClassChecker(job));
            l.add(s.IntegerBoundsChecker(job));
            l.addAll(super.prerequisiteGoals(scheduler));
            return l;
          }
        });
    return g;
  }

  public Goal CreateFabILSkeleton(Job job) {
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g =
        internGoal(new VisitorGoal(job, new FabILSkeletonCreator(job, ts, nf)));

    try {
      addPrerequisiteDependency(g, this.FabricToFabilRewritten(job));
    } catch (CyclicDependencyException e) {
      throw new InternalCompilerError(e);
    }
    return g;
  }

  public Goal FabILSkeletonGenerated(final Job job) {
    Goal g = internGoal(new AbstractGoal(job) {
      @Override
      public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
        List<Goal> l = new ArrayList<>();
        l.add(CreateFabILSkeleton(job));
        l.addAll(super.prerequisiteGoals(scheduler));
        return l;
      }

      @Override
      public Pass createPass(ExtensionInfo extInfo) {
        TypeSystem ts = extInfo.typeSystem();
        NodeFactory nf = extInfo.nodeFactory();

        return new OutputPass(this, new Translator(job(), ts, nf, fabil_target));
      }
    });
    return g;
  }

  @Override
  public boolean runToCompletion() {
    long startTime = System.currentTimeMillis();
    /*
     * Note: this call to super.runToCompletion also adds a goal to the jlext
     * scheduler for each job. I think what's going on is that there shouldn't
     * be any jobs added to that scheduler, so that's a noop. --mdg
     */
    boolean fab_complete = super.runToCompletion();
    long endTime = System.currentTimeMillis();
    if (Report.should_report(Topics.profile, 1)) {
      Report.report(1, "Fabric passes complete: " + (endTime - startTime)
          + "ms");
    }
    if (fab_complete) {
      // Create a goal to compile every source file.
      for (Job job : filext.scheduler().jobs()) {
        filext.scheduler().addGoal(filext.getCompileGoal(job));
      }
      return filext.scheduler().runToCompletion();
    }
    return false;
  }

  @Override
  public Goal TypeExists(URI ns, String name) {
    return CBTypeExists.create(this, ns, name);
  }

}
