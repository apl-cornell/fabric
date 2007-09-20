/**
 * 
 */
package fabric;

import polyglot.frontend.*;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.SourceFileGoal;
import polyglot.main.Options;

/**
 * This is an extension to JL that is the target language of the Fabric
 * extension. This extension cannot live on its own: it only provides the passes
 * after EXC_CHECK_ALL; that is, those that deal with translation.
 */
public class OutputExtensionInfo extends JLExtensionInfo {
  private ExtensionInfo fabricExtInfo;

  public OutputExtensionInfo(ExtensionInfo fabricExtInfo) {
    this.fabricExtInfo = fabricExtInfo;
  }

  @Override
  public Options getOptions() {
    return fabricExtInfo.getOptions();
  }

  @Override
  public Scheduler createScheduler() {
    return new OutputScheduler(this);
  }
  
  private static class OutputScheduler extends JLScheduler {
    OutputScheduler(OutputExtensionInfo extInfo) {
      super(extInfo);
    }
    
    @Override
    public Goal Parsed(Job job) {
      return internGoal(new SourceFileGoal(job) {
        @Override
        public Pass createPass(polyglot.frontend.ExtensionInfo extInfo) {
          return new EmptyPass(this);
        }
      });
    }
  }
}
