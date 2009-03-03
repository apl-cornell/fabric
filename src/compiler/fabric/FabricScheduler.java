package fabric;

import java.util.*;

import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;
import fabric.visit.ExplicitSuperclassAdder;
import fabric.visit.FabricToFabilRewriter;
import fabric.visit.FabricTypeBuilder;
import fabric.visit.RemoteCallWrapperAdder;
import fabric.visit.RemoteCallWrapperUpdater;

import polyglot.ast.Node;
import polyglot.frontend.CyclicDependencyException;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.frontend.Source;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.Serialized;
import polyglot.frontend.goals.VisitorGoal;
import polyglot.util.InternalCompilerError;
import jif.JifScheduler;

public class FabricScheduler extends JifScheduler {
  protected  fabil.ExtensionInfo filext;
  protected fabric.ExtensionInfo fabext;
  
  public FabricScheduler(
         fabric.ExtensionInfo fabext,
          fabil.ExtensionInfo filext) {
    super(fabext, filext);
    this.fabext = fabext;
    this.filext = filext;
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
  
  public Goal ExplicitSuperclassesAdded(Job job) {
    FabricTypeSystem  ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g = internGoal(new VisitorGoal(job, new ExplicitSuperclassAdder(ts,nf)));
    try {
      addPrerequisiteDependency(g, Parsed(job));
    } catch(CyclicDependencyException e) {
      throw new InternalCompilerError(e);
    }
    return g;
  }
  
  public Goal RemoteCallWrappersAdded(final Job job) {
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g = internGoal(new VisitorGoal(job, new RemoteCallWrapperAdder(job, ts, nf)) {
      @Override
      public Collection<Goal> prerequisiteGoals(Scheduler s) {
        List<Goal> l = new ArrayList<Goal>();
        l.add(Parsed(job));
        return l;
      }
    });
    return g;
  }

  @Override
  public Goal TypesInitialized(Job job) {
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g = internGoal(new VisitorGoal(job, new FabricTypeBuilder(job, ts, nf)));
    try {
      addPrerequisiteDependency(g, Parsed(job));
      addPrerequisiteDependency(g, ExplicitSuperclassesAdded(job));
      addPrerequisiteDependency(g, RemoteCallWrappersAdded(job));
    }
    catch (CyclicDependencyException e) {
      throw new InternalCompilerError(e);
    }
    return g;
  }

  public Goal RemoteCallWrappersUpdated(final Job job) {
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g = internGoal(new VisitorGoal(job, new RemoteCallWrapperUpdater(job, ts, nf)) {
      @Override
      public Collection<Goal> prerequisiteGoals(Scheduler s) {
        List<Goal> l = new ArrayList<Goal>();
        l.add(Disambiguated(job));
        return l;
      }
    });
    return g;
  }

  @Override
  public Goal TypeChecked(Job job) {
    Goal g = super.TypeChecked(job);
    try {
      addPrerequisiteDependency(g, RemoteCallWrappersUpdated(job));
    }
    catch (CyclicDependencyException e) {
      throw new InternalCompilerError(e);
    }
    return g;
  }
  
  @Override
  public Goal Serialized(Job job) {
    FabricOptions opts = (FabricOptions) job.extensionInfo().getOptions();
    if (!opts.signatureMode()) return super.Serialized(job);
    
    // Signature mode.  Don't run some passes.
    Goal g = internGoal(new Serialized(job) {
      @SuppressWarnings("unchecked")
      @Override
      public Collection prerequisiteGoals(Scheduler scheduler) {
        List<Goal> l = new ArrayList<Goal>();
        l.add(scheduler.TypeChecked(job));
        l.add(scheduler.ExceptionsChecked(job));
        return l;
      }
    });
    return g;
  }

  public Goal FabricToFabilRewritten(Job job) {
    FabricTypeSystem  ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g = internGoal(new VisitorGoal(job, new FabricToFabilRewriter(job, ts, nf, filext)));     

    try {
        addPrerequisiteDependency(g, this.Serialized(job));
        
        // make sure that if Object.fab is being compiled, it is always
        // written to FabIL before any other job.
        if (objectJob != null && job != objectJob)
            addPrerequisiteDependency(g, FabricToFabilRewritten(objectJob));
    }
    catch (CyclicDependencyException e) {
        // Cannot happen
        throw new InternalCompilerError(e);
    }
    return g;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean runToCompletion() {
    /* Note: this call to super.runToCompletion also adds a goal to the jlext
     * scheduler for each job.  I think what's going on is that there shouldn't
     * be any jobs added to that scheduler, so that's a noop. --mdg
     */
    if (super.runToCompletion()) {
      // Create a goal to compile every source file.
      for (Iterator<Job> i = filext.scheduler().jobs().iterator(); i.hasNext(); ) {
          Job job = i.next();
          filext.scheduler().addGoal(filext.getCompileGoal(job));
      }
      return filext.scheduler().runToCompletion();
    }
    return false;
  }
}
