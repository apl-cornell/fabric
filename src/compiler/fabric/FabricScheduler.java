/**
 * Copyright (C) 2010 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric;

import java.util.*;

import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;
import fabric.visit.ExplicitSuperclassAdder;
import fabric.visit.FabricExceptionChecker;
import fabric.visit.FabricToFabilRewriter;
import fabric.visit.FabricTypeBuilder;
import fabric.visit.PrincipalCastAdder;
import fabric.visit.RemoteCallWrapperAdder;
import fabric.visit.RemoteCallWrapperUpdater;
import fabric.visit.ThisLabelChecker;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.CyclicDependencyException;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.frontend.Source;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.Serialized;
import polyglot.frontend.goals.VisitorGoal;
import polyglot.types.TypeSystem;
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
    FabricOptions opts = (FabricOptions) job.extensionInfo().getOptions();
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    Goal g = internGoal(new VisitorGoal(job, new FabricTypeBuilder(job, ts, nf)));
    try {
      addPrerequisiteDependency(g, Parsed(job));
      addPrerequisiteDependency(g, ExplicitSuperclassesAdded(job));
      if(!opts.signatureMode()) {
        addPrerequisiteDependency(g, RemoteCallWrappersAdded(job));
      } else {
        addPrerequisiteDependency(g, Parsed(job));
      }
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
  
  public Goal DisambiguationAfterWrappers(final Job job) {
    TypeSystem ts = job.extensionInfo().typeSystem();
    NodeFactory nf = job.extensionInfo().nodeFactory();
    Goal g = internGoal(new polyglot.frontend.goals.Disambiguated(job, ts, nf) {
      @Override
      public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
        List<Goal> l = new ArrayList<Goal>();
        l.add(RemoteCallWrappersUpdated(job));
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
      if(!opts.signatureMode()) {
        addPrerequisiteDependency(g, DisambiguationAfterWrappers(job));
      } else {
        addPrerequisiteDependency(g, Disambiguated(job));
      }
    }
    catch (CyclicDependencyException e) {
      throw new InternalCompilerError(e);
    }
    return g;
  }
  
  public Goal ThisLabelChecked(Job job) {
    FabricTypeSystem  ts = fabext.typeSystem();   
    FabricNodeFactory nf = fabext.nodeFactory();    
    Goal g = internGoal(new VisitorGoal(job, new ThisLabelChecker(job, ts, nf)) {
      @Override
      public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
        List<Goal> l = new ArrayList<Goal>();
        l.add(scheduler.TypeChecked(job));
        l.add(scheduler.ExceptionsChecked(job));
        return l;
      }
    });
    return g;
  }
  
  
  
  @Override
  public Goal Serialized(Job job) {
    FabricOptions opts = (FabricOptions) job.extensionInfo().getOptions();
    Goal g = null;
    if (!opts.signatureMode()) {
      g = super.Serialized(job);
      try {
        g.addPrerequisiteGoal(ThisLabelChecked(job), this);
      } catch (CyclicDependencyException e) {
        e.printStackTrace();
      }
    } else {
      // Signature mode.  Don't run some passes.
      g = internGoal(new Serialized(job) {
        @SuppressWarnings("unchecked")
        @Override
        public Collection prerequisiteGoals(Scheduler scheduler) {
          List<Goal> l = new ArrayList<Goal>();
          l.add(ThisLabelChecked(job));
          return l;
        }
      });
    }
    return g;
  }

  public Goal PrincipalCastsAdded(Job job) {
    FabricTypeSystem ts = fabext.typeSystem();
    FabricNodeFactory nf = fabext.nodeFactory();
    
    Goal g = internGoal(new VisitorGoal(job, new PrincipalCastAdder(job, ts, nf)) {
      @SuppressWarnings("unchecked")
      @Override
      public Collection prerequisiteGoals(Scheduler scheduler) {
        List<Goal> l = new ArrayList<Goal>();
        l.add(Serialized(job));
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
        addPrerequisiteDependency(g, this.PrincipalCastsAdded(job));
        
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

  @Override
  public Goal ExceptionsChecked(Job job) {
    TypeSystem ts = extInfo.typeSystem();
    NodeFactory nf = extInfo.nodeFactory();
    Goal g = internGoal(new VisitorGoal(job, new FabricExceptionChecker(job, ts, nf)){
      @SuppressWarnings("unchecked")
      @Override
      public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
        List<Goal> l = new ArrayList<Goal>();
        l.add(scheduler.TypeChecked(job));
        l.add(scheduler.ReachabilityChecked(job));
        FabricScheduler s = (FabricScheduler)scheduler;
        l.add(s.NotNullChecker(job));
        l.add(s.PreciseClassChecker(job));
        l.add(s.IntegerBoundsChecker(job));
        l.addAll(super.prerequisiteGoals(scheduler));
        return l;
      }
    });
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
