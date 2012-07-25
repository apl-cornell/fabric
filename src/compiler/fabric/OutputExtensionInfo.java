package fabric;

import java.util.Collections;
import java.util.List;

import polyglot.ast.Node;
import polyglot.frontend.CyclicDependencyException;
import polyglot.frontend.EmptyPass;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Job;
import polyglot.frontend.Pass;
import polyglot.frontend.Scheduler;
import polyglot.frontend.Source;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.SourceFileGoal;
import polyglot.main.OptFlag.Arg;
import polyglot.main.Options;
import polyglot.main.UsageError;
import polyglot.util.InternalCompilerError;
import fabil.FabILOptions;
import fabil.frontend.FabILScheduler;

/**
 * A small extension of the fabil ExtensionInfo and Scheduler to perform fabil
 * compilation of asts that have come from fabric.
 */
public class OutputExtensionInfo extends fabil.ExtensionInfo {

  protected fabric.ExtensionInfo fabext;

  @Override
  public Scheduler createScheduler() {
    return new OutputScheduler(this);
  }

  //  @Override
  //  protected Options createOptions() {
  //    // we share the options with fabric, which in turn delegates to a
  //    // FabILOptions object for the fabil options handling.
  //    return fabext.getOptions();
  //  }

  static protected class FabILOutputOptions extends FabILOptions {

    public FabILOutputOptions(ExtensionInfo extension) {
      super(extension);
    }

    /**
     * Skip checks regarding source files.
     */
    @Override
    protected void validateArgs() throws UsageError {
    }

  }

  @Override
  protected Options createOptions() {
    FabricOptions parentOpts = fabext.getOptions();
    FabILOutputOptions opt = new FabILOutputOptions(this);
    // filter the parent's options by the ones this extension understands
    try {
      List<Arg<?>> arguments = parentOpts.fabilArgs(opt.flags());
      opt.processArguments(arguments, Collections.<String> emptySet());
    } catch (UsageError e) {
      throw new InternalCompilerError(
          "Got usage error while configuring output extension", e);
    }
    return opt;
  }

  public OutputExtensionInfo(fabric.ExtensionInfo fabext) {
    this.fabext = fabext;
  }

  protected static class OutputScheduler extends FabILScheduler {
    protected Job objectJob;

    OutputScheduler(OutputExtensionInfo extInfo) {
      super(extInfo);
    }

    @Override
    public Job addJob(Source source, Node ast) {
      // We use the same hack here as in JifScheduler, but we need to override
      // to compare with Object.fab instead of Object.jif. Moreover, we need to
      // make sure it's referring to java.lang.Object (as opposed to
      // fabric.lang.Object)
      Job j = super.addJob(source, ast);
      if ("Object.fab".equals(source.name()) && source.path().contains("java")) {
        this.objectJob = j;
      }
      return j;
    }

    @Override
    public Job addJob(Source source) {
      // We use the same hack here as in JifScheduler, but we need to override
      // to compare with Object.fab instead of Object.jif. Moreover, we need to
      // make sure it's referring to java.lang.Object (as opposed to
      // fabric.lang.Object)
      Job j = super.addJob(source);
      if ("Object.fab".equals(source.name()) && source.path().contains("java")) {
        this.objectJob = j;
      }
      return j;
    }

    @Override
    public Goal TypesInitialized(Job job) {
      Goal g = super.TypesInitialized(job);
      try {
        if (objectJob != null && job != objectJob) {
          addPrerequisiteDependency(g, TypesInitialized(objectJob));
        }
      } catch (CyclicDependencyException e) {
        // Cannot happen
        throw new InternalCompilerError(e);
      }
      return g;
    }

    @Override
    public Goal Parsed(Job job) {
      if (job.ast() != null) {
        return internGoal(new SourceFileGoal(job) {
          @Override
          public Pass createPass(polyglot.frontend.ExtensionInfo einf) {
            return new EmptyPass(this);
          }
        });
      }
      return super.Parsed(job);
    }
  }

  //  // Override signature path so that we use the filsigcp
  //  // path during the FabIL compilation phase
  //  @Override
  //  public List<FabricLocation> filsignaturepath() {
  //    return fabext.filsignaturepath();
  //  }

}
