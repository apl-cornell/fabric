package fabric;

import polyglot.ast.Node;
import polyglot.frontend.Job;
import polyglot.frontend.Source;
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

}
