package fabric.core;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.*;

import fabric.client.TransactionCommitFailedException;
import fabric.client.TransactionPrepareFailedException;
import fabric.common.ACLPolicy;
import fabric.common.Pair;
import fabric.core.SerializedObject.RefTypeEnum;

public class TransactionManager {
  public static class Transaction {
    /**
     * The principal for which this transaction is acting.
     */
    protected final Principal client;

    /**
     * The set of object numbers locked by this transaction.
     */
    protected final Set<Long> lockedONums;

    /**
     * The set of objects created by this transaction, indexed by onum.
     */
    protected final Map<Long, SerializedObject> creates;

    /**
     * The set of objects written by this transaction, indexed by onum.
     */
    protected final Map<Long, SerializedObject> writes;

    /**
     * The set of onums of surrogates created by this transaction, indexed by
     * remote reference.
     */
    protected final Map<Pair<String, Long>, Long> surrogates;

    public Transaction(Principal client) {
      this.client = client;
      this.lockedONums = new HashSet<Long>();
      this.creates = new HashMap<Long, SerializedObject>();
      this.writes = new HashMap<Long, SerializedObject>();
      this.surrogates = new HashMap<Pair<String, Long>, Long>();
    }
  }

  /**
   * The object store of the core for which we're managing transactions.
   */
  private final ObjectStore store;

  /**
   * Maps transaction IDs to corresponding <code>Transaction</code> objects.
   */
  private final Map<Integer, Transaction> transactions;

  /**
   * The set of locked object numbers.
   */
  private final Set<Long> lockedONums;

  private final Random random;

  public TransactionManager(ObjectStore store) {
    this.transactions = new HashMap<Integer, Transaction>();
    this.lockedONums = new HashSet<Long>();
    this.store = store;
    this.random = new SecureRandom();
  }

  public synchronized void abortTransaction(Principal client, int transactionID) {
    Transaction transaction = transactions.get(transactionID);
    if (transaction == null || !transaction.client.equals(client)) return;

    // Unlock everything held by this transaction.
    transactions.remove(transactionID);
    lockedONums.removeAll(transaction.lockedONums);
  }

  public synchronized void commitTransaction(Principal client, int transactionID)
      throws TransactionCommitFailedException {
    Transaction transaction = transactions.get(transactionID);
    if (transaction == null || !transaction.client.equals(client))
      throw new TransactionCommitFailedException("Insufficient Authorization");

    // Commit all pending creates and writes.
    boolean result = true;
    for (Map.Entry<Long, SerializedObject> entry : transaction.creates
        .entrySet()) {
      result &= store.insert(client, entry.getKey(), entry.getValue());
    }

    for (Map.Entry<Long, SerializedObject> entry : transaction.writes
        .entrySet()) {
      result &= store.write(client, entry.getKey(), entry.getValue());
    }

    // Unlock everything held by this transaction.
    transactions.remove(transactionID);
    lockedONums.removeAll(transaction.lockedONums);

    // TODO: need a better message
    if (!result)
      throw new TransactionCommitFailedException("something went wrong");
  }

  public int newTransaction(Principal client) {
    while (true) {
      synchronized (this) {
        int result = random.nextInt();
        if (transactions.containsKey(result)) continue;

        // Register the new transaction ID.
        transactions.put(result, new Transaction(client));
        return result;
      }
    }
  }

  public synchronized int prepare(Principal client,
      Collection<SerializedObject> toCreate, Map<Long, Integer> reads,
      Collection<SerializedObject> writes)
      throws TransactionPrepareFailedException {
    int transactionID;
    do {
      transactionID = random.nextInt();
    } while (transactions.containsKey(transactionID));

    Transaction transaction = new Transaction(client);

    try {
      prepare(transaction, toCreate, reads, writes);
      transactions.put(transactionID, transaction);
      return transactionID;
    } catch (TransactionPrepareFailedException e) {
      // Transaction failed. Unlock everything held by this transaction.
      lockedONums.removeAll(transaction.lockedONums);
      e.fillInStackTrace();
      throw e;
    }
  }

  private void prepare(Transaction transaction,
      Collection<SerializedObject> toCreate, Map<Long, Integer> reads,
      Collection<SerializedObject> writes)
      throws TransactionPrepareFailedException {
    // Handle the reads.
    for (Map.Entry<Long, Integer> entry : reads.entrySet()) {
      long onum = entry.getKey();
      int version = entry.getValue();
      SerializedObject obj = store.read(transaction.client, onum);

      // Object must exist and the version numbers must match.
      if (obj == null)
        throw new TransactionPrepareFailedException("Object " + onum
            + " does not exist");
      if (version != obj.getVersion())
        throw new TransactionPrepareFailedException("Object " + onum
            + " is out of date");

      // If the transaction has read it already, nothing to do.
      if (transaction.lockedONums.contains(onum)) continue;

      // Object must not be locked by another transaction.
      if (lockedONums.contains(onum))
        throw new TransactionPrepareFailedException("Object " + onum
            + " has been modified by an uncommitted transaction");

      // This is a new read. Record it.
      transaction.lockedONums.add(onum);
      lockedONums.add(onum);
    }

    // Handle the writes.
    for (SerializedObject obj : writes) {
      long onum = obj.getOnum();

      // Make sure the client has write permissions.
      if (!store.checkWritePerm(transaction.client, onum))
        throw new TransactionPrepareFailedException("Object " + onum
            + " is not writable");

      // Swizzle intercore references.
      makeSurrogates(transaction, obj);

      // If the transaction has already locked object, succeed.
      if (transaction.lockedONums.contains(onum)) {
        transaction.writes.put(onum, obj);
        continue;
      }

      // Fail if the object is already locked by another transaction.
      if (lockedONums.contains(onum))
        throw new TransactionPrepareFailedException("Object " + onum
            + "has been modified by an uncommitted transaction");

      // This is a new write. Record it.
      transaction.lockedONums.add(onum);
      lockedONums.add(onum);
      transaction.writes.put(onum, obj);
    }

    // Handle the object creations.
    for (SerializedObject obj : toCreate) {
      long onum = obj.getOnum();

      // Make sure the client can create the object.
      if (!store.checkInsertPerm(transaction.client, onum))
        throw new TransactionPrepareFailedException("Creation of object "
            + onum + " is unauthorized");

      // Swizzle intercore references.
      makeSurrogates(transaction, obj);

      // If the transaction has already locked object, succeed.
      if (transaction.lockedONums.contains(onum)) {
        transaction.creates.put(onum, obj);
        continue;
      }

      // Fail if the object is already locked by another transaction.
      if (lockedONums.contains(onum))
        throw new TransactionPrepareFailedException("Object " + onum
            + " has been created by a pending transaction");

      // This is a new create. Record it.
      transaction.lockedONums.add(onum);
      lockedONums.add(onum);
      transaction.creates.put(onum, obj);
    }
  }

  /**
   * Creates surrogates for the given object as part of the given transaction.
   */
  private void makeSurrogates(Transaction transaction, SerializedObject obj) {
    Iterator<RefTypeEnum> refTypeIter =
        new ArrayList<RefTypeEnum>(obj.refTypes).iterator();
    Iterator<Long> relatedOnumIter =
        new ArrayList<Long>(obj.intracoreRefs).iterator();
    Iterator<Pair<String, Long>> intercoreRefIter =
        obj.intercoreRefs.iterator();

    obj.refTypes.clear();
    obj.intracoreRefs.clear();
    obj.intercoreRefs = null;
    while (refTypeIter.hasNext()) {
      RefTypeEnum refType = refTypeIter.next();
      switch (refType) {
      case NULL:
      case INLINE:
        obj.refTypes.add(refType);
        break;

      case ONUM:
        obj.intracoreRefs.add(relatedOnumIter.next());
        obj.refTypes.add(refType);
        break;

      case REMOTE:
        obj.refTypes.add(RefTypeEnum.ONUM);
        obj.intracoreRefs.add(createSurrogate(transaction, intercoreRefIter
            .next()));
        break;
      }
    }
  }

  /**
   * A helper method for obtaining the onum for a surrogate for the given remote
   * reference. The cache of surrogates in the given transaction is first
   * checked. If no appropriate surrogate has already been created in the
   * transaction, a new one is made as part of the transaction.
   * 
   * @return the onum for a surrogate for the given remote reference.
   */
  Long createSurrogate(Transaction transaction, Pair<String, Long> remoteRef) {
    // Check our cache of surrogates.
    if (transaction.surrogates.containsKey(remoteRef))
      return transaction.surrogates.get(remoteRef);

    // Allocate a new onum for the surrogate.
    long onum;
    do {
      onum = newOIDs(transaction.client, 1)[0];
    } while (lockedONums.contains(onum));

    // Create the surrogate.
    // XXX Use the right policy here.
    SerializedObject surrogate =
        new SerializedObject(onum, new ACLPolicy(), remoteRef);

    // Register the surrogate.
    transaction.lockedONums.add(onum);
    lockedONums.add(onum);
    transaction.creates.put(onum, surrogate);
    transaction.surrogates.put(remoteRef, onum);

    return onum;
  }

  public synchronized SerializedObject read(Principal client, long onum) {
    return store.read(client, onum);
  }

  public synchronized long[] newOIDs(Principal client, int num) {
    return store.newOIDs(client, lockedONums, num);
  }
}
