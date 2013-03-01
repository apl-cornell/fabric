package jpa2fab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jpa2fab.JPA2FabExtensionInfo.JPA2FabOptions;
import jpa2fab.ext.JPA2FabILRewriter;
import polyglot.ast.NodeFactory;
import polyglot.ext.jl5.JL5Scheduler;
import polyglot.frontend.CyclicDependencyException;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.JLExtensionInfo;
import polyglot.frontend.Job;
import polyglot.frontend.OutputPass;
import polyglot.frontend.Pass;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.CodeGenerated;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.VisitorGoal;
import polyglot.main.Report;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;

/**
 * 
 */
public class JPA2FabScheduler extends JL5Scheduler {

  /**
   * @param extInfo
   */
  public JPA2FabScheduler(JLExtensionInfo extInfo) {
    super(extInfo);
  }

  @Override
  public boolean runToCompletion() {
    JPA2FabExtensionInfo extInfo = (JPA2FabExtensionInfo) this.extInfo;
    JPA2FabOptions opt = (JPA2FabOptions) extInfo.getOptions();
    if (!opt.outputFabIL()) {
      return super.runToCompletion();
    } else {
      Goal theEnd = internGoal(new TheEndGoal(this));

      boolean okay = true;

      while (okay && !reached(theEnd)) {
        okay = attemptGoal(theEnd);
      }

      if (Report.should_report(Report.frontend, 1))
        Report.report(1, "Finished all passes for " + this.getClass().getName()
            + " -- " + (okay ? "okay" : "failed"));

      return okay;
    }

  }

  @Override
  public Goal RemoveJava5isms(Job job) {
    polyglot.frontend.ExtensionInfo toExtInfo = extInfo.outputExtensionInfo();
    Goal g =
        internGoal(new VisitorGoal(job, new JPA2FabILRewriter(job, extInfo,
            toExtInfo, "store")));
    try {
      g.addPrerequisiteGoal(CastsInserted(job), this);
      g.addPrerequisiteGoal(TypeErasureProcDecls(job), this);
      g.addPrerequisiteGoal(RemoveVarArgs(job), this);
      g.addPrerequisiteGoal(AutoBoxing(job), this);
      g.addPrerequisiteGoal(RemoveEnums(job), this);
      g.addPrerequisiteGoal(RemoveVarArgsFlags(job), this);
      g.addPrerequisiteGoal(RemoveExtendedFors(job), this);
      g.addPrerequisiteGoal(RemoveStaticImports(job), this);
      g.addPrerequisiteGoal(RemoveAnnotations(job), this);
    } catch (CyclicDependencyException e) {
      throw new InternalCompilerError(e);
    }
    return this.internGoal(g);
  }

  /**
   * @param job
   * @return
   */
  public Goal FabILGenerated(Job job) {
    Goal g = internGoal(new CodeGenerated(job) {
      @Override
      public Pass createPass(ExtensionInfo extInfo) {
        JPA2FabExtensionInfo jpa2fabExt = (JPA2FabExtensionInfo) extInfo;
        ExtensionInfo fabILExt = jpa2fabExt.outputExtensionInfo();
        TypeSystem ts = fabILExt.typeSystem();
        NodeFactory nf = fabILExt.nodeFactory();
        return new OutputPass(this, new FabILTranslator(job(), ts, nf,
            jpa2fabExt.targetFactory()/*, true*/));
      }

      @Override
      public Collection<Goal> prerequisiteGoals(Scheduler scheduler) {
        List<Goal> l = new ArrayList<Goal>();
        // Overwrite to avoid type serialization -- there aren't any yet!
        l.add(((JL5Scheduler) scheduler).RemoveJava5isms(job));
        return l;
      }
    });
    return g;
  }

}
