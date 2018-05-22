package fabric.worker.metrics.treaties;

import fabric.common.FastSerializable;
import fabric.worker.metrics.ImmutableObserverSet;

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
   */
  public This update();

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
}
