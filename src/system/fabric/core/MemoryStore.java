package fabric.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.*;

/**
 * This class is not thread-safe. Only the <code>TransactionManager</code>
 * should directly interact with this class. The <code>TransactionManager</code>'s
 * thread safety ensures safe usage of this class.
 */
class MemoryStore implements ObjectStore {
  /**
   * Maps 48-bit object numbers to SerializedObjects. Value is null if object
   * lease has been created.
   */
  private Map<Long, SerializedObject> objectTable;
  private Map<Long, Principal> leaseTable;
  private Timer leaseCleaner;
  private Random rand;

  private static final long LEASE_LENGTH = 300000L; // 5 minutes

  private MemoryStore() {
    this.leaseCleaner = new Timer();
    this.leaseTable = new HashMap<Long, Principal>();
    this.rand = new Random();
  }

  @SuppressWarnings("unchecked")
  public MemoryStore(InputStream in) throws IOException, ClassNotFoundException {
    this();
    ObjectInputStream oin = new ObjectInputStream(in);
    this.objectTable = (Map<Long,SerializedObject>) oin.readObject();    
  }
  
  public void dump(OutputStream out) throws IOException {
    ObjectOutputStream oout = new ObjectOutputStream(out);
    oout.writeObject(this.objectTable);
  }
   
  /*
   * (non-Javadoc)
   * 
   * @see fabric.core.ObjectStore#read(java.security.Principal, long)
   */
  public SerializedObject read(Principal client, long onum) {
    SerializedObject obj = objectTable.get(onum);

    if (obj != null && !obj.getPolicy().canRead(client)) return null;
    return obj;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.core.ObjectStore#checkInsertPerm(java.security.Principal, long)
   */
  public boolean checkInsertPerm(Principal client, long onum) {
    if (objectTable.containsKey(onum)) return false;

    Principal owner = leaseTable.get(onum);
    return owner == null || owner.equals(client);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.core.ObjectStore#checkWritePerm(java.security.Principal, long)
   */
  public boolean checkWritePerm(Principal client, long onum) {
    SerializedObject curObj = objectTable.get(onum);
    return curObj != null && curObj.getPolicy().canWrite(client);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.core.ObjectStore#write(java.security.Principal, long,
   *      fabric.core.SerializedObject)
   */
  public boolean write(Principal client, long onum, SerializedObject obj) {

    if (!checkWritePerm(client, onum)) return false;

    // Update the version number.
    SerializedObject curObj = objectTable.get(onum);
    obj.setVersion(curObj.getVersion() + 1);

    objectTable.put(onum, obj);
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.core.ObjectStore#insert(java.security.Principal, long,
   *      fabric.core.SerializedObject)
   */
  public boolean insert(Principal client, long onum, SerializedObject obj) {

    if (!checkInsertPerm(client, onum)) return false;

    // Initialise the version number and insert
    obj.setVersion(0);
    objectTable.put(onum, obj);
    return true;
  }

  /**
   * Default number of leases to hand out in reponse to a lease request.
   */
  private static final int LEASE_REQ_DEFAULT = 10;

  /**
   * Returns a set of object numbers that aren't already in use. An object
   * number is in use if there is an object stored at that object number, or if
   * there is an unexpired lease for the object number.
   */
  public long[] newOIDs(Principal client, Set<Long> lockedONums, int num) {
    if (num < 1) num = LEASE_REQ_DEFAULT;
    final long[] result = new long[num];

    // generate the object numbers
    for (int count = 0; count < num; count++) {
      long onum;
      do {
        onum = rand.nextLong() & 0x0000ffffffffffffL;
      } while (objectTable.containsKey(onum) || leaseTable.containsKey(onum)
          || lockedONums.contains(onum));
      result[count] = onum;
    }

    // store leases
    for (long onum : result)
      leaseTable.put(onum, client);

    // schedule removal
    TimerTask remover = new TimerTask() {
      @Override
      public void run() {
        synchronized (MemoryStore.this) {
          for (long onum : result)
            leaseTable.remove(onum);
        }
      }
    };
    leaseCleaner.schedule(remover, LEASE_LENGTH);

    return result;
  }
}
