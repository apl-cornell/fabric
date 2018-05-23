package fabric.worker.metrics.treaties;

import fabric.common.FastSerializable;
import fabric.common.util.Pair;
import fabric.metrics.util.Observer;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.StatsMap;

/**
 * An representation of a treaty defined as a logical statement about an object.
 *
 * This is primarily intended to act as a blueprint if we decide to make other
 * kinds of treaty-able objects in the future.
 */
public interface Treaty<This extends Treaty<This>> extends FastSerializable {

  /**
   * Do any work needed to update the state of this treaty, returning the new
   * state.  If the state has not changed, it will return "this".
   * @param asyncExtension
   *        flag indicating if this is running as an async extension.
   * @param weakStats
   *        Weakly consistent stats to use for metrics.
   * @return a pair of the updated treaty and the observers that should be
   *         notified of this update.
   */
  public Pair<This, ImmutableObserverSet> update(boolean asyncExtension,
      StatsMap weakStats);

  /**
   * Check if the treaty is currently true.
   */
  public boolean valid();

  /**
   * @return the expiry, the time at which this treaty is no longer guaranteed.
   */
  public long getExpiry();

  /**
   * @return the id of the treaty within the associated object's treaty set
   */
  public long getId();

  /**
   * @return the observers of this treaty (other treaties or memoized functions)
   */
  public ImmutableObserverSet getObservers();

  /**
   * @param obs an observer now watching this treaty
   * @return the updated treaty
   */
  public This addObserver(Observer obs);

  /**
   * @param obs an observer, whose treaty is now watching this treaty
   * @param id the id of the treaty within the observer obs
   * @return the updated treaty
   */
  public This addObserver(Observer obs, long id);

  /**
   * @param obs an observer no longer watching this treaty
   * @return the updated treaty
   */
  public This removeObserver(Observer obs);

  /**
   * @param obs an observer, whose treaty is no longer watching this treaty
   * @param id the id of the treaty within the observer obs
   * @return the updated treaty
   */
  public This removeObserver(Observer obs, long id);

  /**
   * Has this treaty been activated since creation?
   */
  public boolean isActive();

  /**
   * Does this imply the other treaty?
   */
  public boolean implies(This other);
}
