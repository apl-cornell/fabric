/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.store.db;

import static fabric.common.Logging.STORE_DB_LOGGER;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fabric.common.ONumConstants;
import fabric.common.Resources;
import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.lang.security.Principal;
import fabric.store.SubscriptionManager;

/**
 * <p>
 * An in-memory implementation of the ObjectDB. This class assumes there will be
 * no failures and hopefully will provide very high performance at the cost of
 * no fault tolerance whatsoever. This class does have a simple facility for
 * loading and saving the database to a file.
 * </p>
 * <p>
 * This class is not thread-safe. Only the <code>TransactionManager</code>
 * should directly interact with this class. The <code>TransactionManager</code>
 * 's thread safety ensures safe usage of this class.
 * </p>
 */
public class MemoryDB extends ObjectDB {

  /**
   * Whether the database has been initialized.
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

  /**
   * Opens the store contained in file "var/storeName" if it exists, or an empty
   * store otherwise.
   * 
   * @param name
   *          name of store to create database for.
   */
  public MemoryDB(String name) {
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

    STORE_DB_LOGGER.info("MemDB loaded");
  }

  @Override
  public void finishPrepare(long tid, Principal worker) {
  }

  @Override
  public void commit(long tid, Principal workerPrincipal, SubscriptionManager sm)
      throws AccessException {
    PendingTransaction tx = remove(workerPrincipal, tid);

    // merge in the objects
    for (SerializedObject o : tx.modData) {
      objectTable.put(o.getOnum(), o);

      // Remove any cached globs containing the old version of this object.
      notifyCommittedUpdate(sm, o.getOnum());
    }
  }

  @Override
  public void rollback(long tid, Principal worker) throws AccessException {
    remove(worker, tid);
  }

  @Override
  public SerializedObject read(long onum) {
    return objectTable.get(onum);
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
  private PendingTransaction remove(Principal worker, long tid)
      throws AccessException {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    PendingTransaction tx = submap.remove(worker);
    if (submap.isEmpty()) pendingByTid.remove(tid);

    if (tx == null)
      throw new AccessException("Invalid transaction id: " + tid);

    // XXX Check if the worker acts for the owner.

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
