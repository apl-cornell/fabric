package fabric.core.store;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import fabric.common.AccessException;
import fabric.common.ONumConstants;
import fabric.common.Resources;
import fabric.common.SerializedObject;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.Principal;

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
   * Largest transaction id ever used;
   */
  private int maxTid;

  /**
   * Maps 48-bit object numbers to SerializedObjects.
   */
  private LongKeyMap<SerializedObject> objectTable;

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

      int size = oin.readInt();
      this.objectTable = new LongKeyHashMap<SerializedObject>(size);
      for (int i = 0; i < size; i++)
        this.objectTable.put(oin.readLong(), new SerializedObject(oin));
    } catch (Exception e) {
      // Do nothing
      // TODO: distinguish invalid files from nonexistent
      this.objectTable = new LongKeyHashMap<SerializedObject>();
    }

    // Note: this would be much faster if objectTable was sorted...but it's just
    // recovery so IMO probably not worth it
    this.nextOnum = ONumConstants.FIRST_UNRESERVED;
    LongIterator iter = this.objectTable.keySet().iterator();
    while (iter.hasNext()) {
      long l = iter.next();
      if (this.nextOnum < l) nextOnum = l;
    }

    this.maxTid = 0;
    log.info("Mem store loaded");
  }

  @Override
  protected int newTid(Principal client) {
    return ++maxTid;
  }

  @Override
  public void finishPrepare(int tid) {
  }

  @Override
  public void commit(Principal client, int tid) throws AccessException {
    PendingTransaction tx = remove(client, tid);

    // merge in the objects
    for (SerializedObject o : tx.modData) {
      objectTable.put(o.getOnum(), o);
    }
  }

  @Override
  public void rollback(Principal client, int tid) throws AccessException {
    remove(client, tid);
  }

  @Override
  public SerializedObject read(long onum) {
    return objectTable.get(onum);
  }

  @Override
  public boolean exists(long onum) {
    return isPrepared(onum) || objectTable.containsKey(onum);
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
    oout.writeInt(this.objectTable.size());
    for (LongKeyMap.Entry<SerializedObject> entry : this.objectTable.entrySet()) {
      oout.writeLong(entry.getKey());
      entry.getValue().write(oout);
    }
    oout.flush();
    oout.close();
  }

  /**
   * Helper method to check permissions and update the pending object table for
   * a commit or roll-back.
   */
  private PendingTransaction remove(Principal client, int tid)
      throws AccessException {
    PendingTransaction tx = pendingByTid.remove(tid);
    if (tx == null) throw new AccessException();

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
