package codebases.frontend;

import polyglot.frontend.goals.Goal;
import fabric.common.FabricLocation;

/**
 * Ideally this interface would extend an interface that all schedulers
 * implement, but polyglot.frontend.Scheduler is a class, not a scheduler.
 * 
 * @author owen
 */
public interface CBScheduler {
  Goal TypeExists(FabricLocation ns, String name);
}
