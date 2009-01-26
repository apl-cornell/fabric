package fabric;

import java.util.Iterator;

import polyglot.ast.Node;
import polyglot.frontend.Job;
import polyglot.frontend.Source;
import polyglot.frontend.goals.Goal;
import jif.JifScheduler;

public class FabricScheduler extends JifScheduler {

  public FabricScheduler(ExtensionInfo extInfo, jif.OutputExtensionInfo jlext) {
    super(extInfo, jlext);
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

  public Goal HandoffToFil(Job job) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean runToCompletion() {
    if (super.runToCompletion()) {
      // Create a goal to compile every source file.
      for (Iterator<Job> i = jlext.scheduler().jobs().iterator(); i.hasNext(); ) {
          Job job = i.next();
          jlext.scheduler().addGoal(jlext.getCompileGoal(job));
      }
      return jlext.scheduler().runToCompletion();
    }
    return false;
  }
}
