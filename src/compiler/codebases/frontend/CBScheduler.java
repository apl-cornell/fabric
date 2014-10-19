package codebases.frontend;

import java.net.URI;

import polyglot.frontend.goals.Goal;

/**
 * Ideally this interface would extend an interface that all schedulers
 * implement, but polyglot.frontend.Scheduler is a class, not a scheduler.
 *
 * @author owen
 */
public interface CBScheduler {
  Goal TypeExists(URI ns, String name);
}
