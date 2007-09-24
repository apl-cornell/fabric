package fabric.core;

import java.security.Principal;
import java.util.Set;


/**
 * Implementations of this do not have to be thread-safe. Only the
 * <code>TransactionManager</code> should directly interact with
 * <code>ObjectStore</code>s. The <code>TransactionManager</code>'s thread
 * safety ensures safe usage of <code>ObjectStore</code>s.
 */
interface ObjectStore {
  /**
   * Fetches an object by object number from the store. Returns null if there is
   * no object with that object number or if the client is not authorised to
   * read the object.
   */
  SerializedObject read(Principal client, long onum);

  /**
   * Determines whether a given principal may insert an object at a specific
   * object number.
   */
  boolean checkInsertPerm(Principal client, long onum);

  /**
   * Determines whether a given principal may write to an object.
   */
  boolean checkWritePerm(Principal client, long onum);

  /**
   * Writes an object to the store. The write fails and the method returns false
   * if there is no object already in the store with the given object number, or
   * if the client is not authorised to write the object.
   */
  boolean write(Principal client, long onum, SerializedObject obj);

  /**
   * Inserts an object into the store. Insertion fails and the method returns
   * false if a lease for the object number exists and is not owned by the
   * client.
   */
  boolean insert(Principal client, long onum, SerializedObject obj);

  /**
   * Returns a set of object numbers that aren't already in use. An object
   * number is in use if there is an object stored at that object number, or if
   * there is an unexpired lease for the object number.
   */
  long[] newOIDs(Principal client, Set<Long> lockedONums, int num);
}
