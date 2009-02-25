package fabric.lang;

/** A History encapsulates the history of read and write requests for an object.
 *  It is maintained at the Core and is used to generate promise durations for
 *  the object.
 *  
 *  History objects are created by the {@link fabric.lang.Object#createHistory}
 *  method.  They are only heuristic information and may be discarded and
 *  recreated at any time. 
 */
public interface History {
  /** Called whenever a transaction that read  the object is committed */
  void commitRead();
  
  /** Called whenever a transaction that wrote the object is committed */
  void commitWrote();

  /**
   * Determine the duration of a promise to issue.  While a promise is
   * outstanding, the core will not permit updates to the object to commit.
   * This allows read-only transactions to proceed without contacting the core.
   * @return the duration, in milliseconds, of the promise.
   */
  int generatePromise();
}
