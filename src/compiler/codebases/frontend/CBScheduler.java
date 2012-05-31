package codebases.frontend;

import fabric.common.FabricLocation;

import polyglot.frontend.goals.Goal;

/**
 * Ideally this interface would extend an interface that all schedulers
 * implement, but polyglot.frontend.Scheduler is a class, not a scheduler.
 * 
 * @author owen
 */
public interface CBScheduler {
  Goal TypeExists(FabricLocation ns, String name);
}
