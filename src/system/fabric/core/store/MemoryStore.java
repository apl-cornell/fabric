package fabric.core.store;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import fabric.common.*;
import fabric.common.exceptions.AccessException;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.lang.NodePrincipal;

/**
 * <p>
 * An in-memory implementation of the ObjectStore implementation. This class
 * assumes there will be no failures and hopefully will provide very high
 * performance at the cost of no fault tolerance whatsoever. This class does
 * have a simple facility for loading and saving the store to a file.
 * </p>
 * <p>
 * This class is not thread-safe. Only the <code>TransactionManager</code>
 * should directly interact with this class. The <code>TransactionManager</code>
 * 's thread safety ensures safe usage of this class.
 * </p>
 */
public class MemoryStore extends ObjectStore {

  /**
   * Whether the store has been initialized.
   */
  private boolean isInitialized;

  /**
   * The next free onum.
   */
  private long nextOnum;

  /**
   * Maps 48-bit object numbers to SerializedObjects.
   */
  private LongKeyMap<SerializedObject> objectTable;

  /**
   * The next free glob ID.
   */
  private long nextGlobID;

  private Logger log = Logger.getLogger("fabric.core.store.mem");

  /**
   * Opens the core contained in file "var/coreName" if it exists, or an empty
   * core otherwise.
   * 
   * @param name
   *          name of core to create store for.
   */
  public MemoryStore(String name) {
    super(name);
    this.isInitialized = false;

    try {
      ObjectInputStream oin =
          new ObjectInputStream(Resources.readFile("var", name));

      this.isInitialized = oin.readBoolean();

      this.nextOnum = oin.readLong();

      int size = oin.readInt();
      this.objectTable = new LongKeyHashMap<SerializedObject>(size);
      for (int i = 0; i < size; i++)
        this.objectTable.put(oin.readLong(), new SerializedObject(oin));

      this.nextGlobID = oin.readLong();
    } catch (Exception e) {
      // TODO: distinguish invalid files from nonexistent
      this.nextOnum = ONumConstants.FIRST_UNRESERVED;
      this.objectTable = new LongKeyHashMap<SerializedObject>();
    }

    log.info("Mem store loaded");
  }

  @Override
  public void finishPrepare(long tid, NodePrincipal client) {
  }

  @Override
  public void commit(long tid, NodePrincipal client) throws AccessException {
    PendingTransaction tx = remove(client, tid);

    // merge in the objects
    for (SerializedObject o : tx.modData) {
      objectTable.put(o.getOnum(), o);

      // Remove any cached globs containing the old version of this object.
      removeGlobByOnum(o.getOnum());
    }
  }

  @Override
  public void rollback(long tid, NodePrincipal client) throws AccessException {
    remove(client, tid);
  }

  @Override
  public SerializedObject read(long onum) {
    return objectTable.get(onum);
  }

  @Override
  protected long nextGlobID() {
    return nextGlobID++;
  }

  @Override
  public boolean exists(long onum) {
    return rwLocks.get(onum) != null || objectTable.containsKey(onum);
  }

  @Override
  public long[] newOnums(int num) {
    final long[] result = new long[num < 0 ? 0 : num];

    for (int i = 0; i < num; i++)
      result[i] = nextOnum++;

    return result;
  }

  @Override
  public void close() throws IOException {
    // XXX TODO Save prepared txs to stable storage, implement finishPrepare().

    ObjectOutputStream oout =
        new ObjectOutputStream(Resources.writeFile("var", name));
    oout.writeBoolean(isInitialized);

    oout.writeLong(nextOnum);

    oout.writeInt(this.objectTable.size());
    for (LongKeyMap.Entry<SerializedObject> entry : this.objectTable.entrySet()) {
      oout.writeLong(entry.getKey());
      entry.getValue().write(oout);
    }

    oout.writeLong(this.nextGlobID);

    oout.flush();
    oout.close();
  }

  /**
   * Helper method to check permissions and update the pending object table for
   * a commit or roll-back.
   */
  private PendingTransaction remove(NodePrincipal client, long tid)
      throws AccessException {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    PendingTransaction tx = submap.remove(client);
    if (submap.isEmpty()) pendingByTid.remove(tid);

    if (tx == null) throw new AccessException("Invalid transaction id: " + tid);

    // XXX Check if the client acts for the owner.

    unpin(tx);
    return tx;
  }

  @Override
  protected boolean isInitialized() {
    return this.isInitialized;
  }

  @Override
  protected void setInitialized() {
    this.isInitialized = true;
  }

}

/*
 * vim: ts=2 sw=2 et cindent cino=\:0
 */
