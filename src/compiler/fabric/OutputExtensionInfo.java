package fabric;

import polyglot.ast.Node;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.frontend.Source;
import jif.ExtensionInfo;

public class OutputExtensionInfo extends jif.OutputExtensionInfo {

  public OutputExtensionInfo(ExtensionInfo jifExtInfo) {
    super(jifExtInfo);
  }

  @Override
  public Scheduler createScheduler() {
    return new OutputScheduler(this);
  }
  
  static class OutputScheduler extends jif.OutputExtensionInfo.OutputScheduler {
    // We use the same hack here as in JifScheduler, but we need to override to
    // compare with Object.fab instead of Object.jif.

    OutputScheduler(OutputExtensionInfo extInfo) {
      super(extInfo);
    }

    @Override
    public Job addJob(Source source, Node ast) {
      Job j = super.addJob(source, ast);
      if ("Object.fab".equals(source.name())) {
        this.objectJob = j;
      }
      return j;
    }

    @Override
    public Job addJob(Source source) {
      Job j = super.addJob(source);
      if ("Object.fab".equals(source.name())) {
        this.objectJob = j;
      }
      return j;
    }
  }
}
