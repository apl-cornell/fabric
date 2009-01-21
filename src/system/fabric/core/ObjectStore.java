package fabric.core;

import java.io.IOException;
import java.util.NoSuchElementException;

import fabric.common.SerializedObject;
import fabric.core.store.StoreException;
import fabric.lang.Principal;

/**
 * <p>
 * An Object Store encapsulates the persistent state of the Core. It is
 * responsible for storing and retrieving objects, and also for checking
 * permissions.
 * </p>
 * <p>
 * The Object Store interface is designed to support a two-phase commit
 * protocol. Consequently to insert or modify an object, users must first call
 * the prepare() method, passing in the set of objects to update. These objects
 * will be stored, but will remain unavailable until the commit() method is
 * called with the returned transaction identifier.
 * </p>
 * <p>
 * All ObjectStore implementations should provide a constructor which takes the
 * name of the core and opens the appropriate backend store if it exists, or
 * creates it if it doesn't exist.
 * </p>
 */
public interface ObjectStore {

  /**
   * Store object creations and modifications for later commit. The updates will
   * not become visible until commit() is called with the resulting transaction
   * identifier. In the case of an exception, no objects should be marked as
   * prepared.
   * 
   * @param client
   *          the client to the transaction
   * @param req
   *          the read, write, and create sets for the transaction to prepare
   * @return a transaction identifier that can subsequently be passed to
   *         commit() or abort()
   * @throws StoreException
   *           if the client has insufficient privileges.
   */
  int prepare(Principal client, PrepareRequest req) throws StoreException;

  /**
   * Cause the objects prepared in transaction [tid] to be committed. The
   * changes will hereafter be visible to read.
   * 
   * @param client
   *          the principal requesting the commit
   * @param tid
   *          the identifier (returned by prepare) corresponding to the
   *          transaction
   * @throws StoreException
   *           if the principal differs from the caller of prepare()
   */
  void commit(Principal client, int tid) throws StoreException;

  /**
   * Cause the objects prepared in transaction [tid] to be discarded.
   * 
   * @param client
   *          the principal requesting the rollback
   * @param tid
   *          the identifier (returned by prepare) corresponding to the
   *          transaction
   * @throws StoreException
   *           if the principal differs from the caller of prepare()
   */
  void rollback(Principal client, int tid) throws StoreException;

  /**
   * Return the object stored at a particular onum.
   * 
   * @param client
   *          the client responsible for the request
   * @param onum
   *          the identifier
   * @return the object
   * @throws StoreException
   *           if client is not allowed to read the object (according to the
   *           access control policy associated with the object
   * @throws NoSuchElementException
   *           if there is no object stored at the given onum
   */
  SerializedObject read(Principal client, long onum)
      throws StoreException, NoSuchElementException;

  /**
   * Determine whether an onum has outstanding uncommitted changes or reads.
   * 
   * @param onum
   *          the object number in question
   * @return true if the object has been prepared by transaction that hasn't
   *         been committed or rolled back.
   */
  boolean isPrepared(long onum);

  /**
   * Determine whether an onum has outstanding uncommitted reads.
   * 
   * @param onum
   *          the object number in question
   * @return true if the object has been read by a transaction that hasn't
   *         been committed or rolled back.
   */
  boolean isRead(long onum);

  /**
   * Determine whether an onum has outstanding uncommitted changes.
   * 
   * @param onum
   *          the object number in question
   * @return true if the object has been changed by a transaction that hasn't
   *         been committed or rolled back.
   */
  boolean isWritten(long onum);
  
  /**
   * <p>
   * Return a set of onums that aren't currently occupied. The ObjectStore may
   * return the same onum more than once from this method, althogh doing so would
   * encourage collisions. There is no assumption of unpredictability or
   * randomness about the returned ids.
   * </p>
   * <p>
   * The returned onums should be packed in the lower 48 bits. We assume that
   * the object store is never full, and can always provide new onums
   * </p>
   * 
   * @param num
   *          the number of onums to return
   * @return num fresh onums
   */
  long[] newOnums(int num) throws StoreException;
  
  /**
   * Checks whether an object with the corresponding onum exists, in either
   * prepared or committed form.
   * 
   * @param onum
   *          the onum of to check
   * @return true if an object exists for onum
   */
  boolean exists(long onum);

  /**
   * Returns the name of this core.
   */
  String getName();
  
  /**
   * Gracefully shutdown the object store.
   * @throws IOException 
   */
  void close() throws IOException;

}
